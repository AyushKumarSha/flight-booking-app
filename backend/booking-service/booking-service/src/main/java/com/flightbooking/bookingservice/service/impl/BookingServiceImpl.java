package com.flightbooking.bookingservice.service.impl;

import com.flightbooking.bookingservice.config.CardValidationUtil;
import com.flightbooking.bookingservice.dto.*;
import com.flightbooking.bookingservice.entity.Booking;
import com.flightbooking.bookingservice.exception.*;
import com.flightbooking.bookingservice.feign.FlightServiceClient;
import com.flightbooking.bookingservice.kafka.PaymentEventProducer;
import com.flightbooking.bookingservice.repository.BookingRepository;
import com.flightbooking.bookingservice.service.BookingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final FlightServiceClient flightServiceClient;
    private final PaymentEventProducer paymentEventProducer;

    @Override
    public List<FlightSearchResponseDTO> searchFlights(String source, String destination, LocalDate travelDate) {
        log.info("Searching flights via Feign: {} -> {} on {}", source, destination, travelDate);
        return flightServiceClient.searchFlights(source, destination, travelDate);
    }

    @Override
    @Transactional
    public BookingResponseDTO initiateBooking(BookingRequestDTO request) {
        log.info("Initiating booking for userId: {} routeId: {}", request.getUserId(), request.getRouteId());

        // Card validation (SAGA invariant check before initiating)
        if ("CARD".equalsIgnoreCase(request.getPaymentMode())) {
            CardValidationUtil.validateCard(request.getCardNumber(), request.getCardExpiry());
        }

        // Get flight details via Feign
        FlightSearchResponseDTO route = flightServiceClient.getRouteById(request.getRouteId());

        // Create booking with INITIATED status (SAGA begins)
        Booking booking = Booking.builder()
                .userId(request.getUserId())
                .routeId(request.getRouteId())
                .flightNumber(route.getFlightNumber())
                .source(route.getSource())
                .destination(route.getDestination())
                .travelDate(request.getTravelDate())
                .amount(route.getDynamicPrice())
                .paymentMode(request.getPaymentMode())
                .build();

        Booking saved = bookingRepository.save(booking);
        log.info("Booking created with id: {} status: INITIATED", saved.getId());

        // Send payment event to Kafka (async SAGA step)
        PaymentEventDTO paymentEvent = new PaymentEventDTO(
                saved.getId(),
                request.getUserId(),
                saved.getAmount(),
                request.getPaymentMode(),
                request.getCardNumber(),
                request.getCardExpiry()
        );
        paymentEventProducer.sendPaymentEvent(paymentEvent);

        return mapToResponse(saved);
    }

    @Override
    public BookingResponseDTO getBookingById(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException("Booking not found: " + bookingId));
        return mapToResponse(booking);
    }

    @Override
    public List<BookingResponseDTO> getBookingsByUser(Long userId) {
        return bookingRepository.findByUserId(userId)
                .stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void updateBookingStatus(Long bookingId, String status) {
        log.info("Updating booking {} to status: {}", bookingId, status);
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException("Booking not found: " + bookingId));
        booking.setStatus(status);
        bookingRepository.save(booking);

        // Reduce seat count if payment successful
        if ("SUCCESSFUL".equals(status)) {
            log.info("Decrementing seat for routeId: {}", booking.getRouteId());
            flightServiceClient.decrementSeat(booking.getRouteId());
        }
    }

    @Override
    @Transactional
    public BookingResponseDTO cancelBooking(Long bookingId) {
        log.info("Cancelling booking: {}", bookingId);
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException("Booking not found: " + bookingId));

        // SAGA compensation - only INITIATED bookings can be cancelled
        if ("SUCCESSFUL".equals(booking.getStatus())) {
            throw new InvalidPaymentException("Cannot cancel a successful booking");
        }
        booking.setStatus("CANCELLED");
        return mapToResponse(bookingRepository.save(booking));
    }

    private BookingResponseDTO mapToResponse(Booking booking) {
        return new BookingResponseDTO(
                booking.getId(),
                booking.getUserId(),
                booking.getFlightNumber(),
                booking.getSource(),
                booking.getDestination(),
                booking.getTravelDate(),
                booking.getAmount(),
                booking.getPaymentMode(),
                booking.getStatus(),
                booking.getCreatedAt()
        );
    }
}