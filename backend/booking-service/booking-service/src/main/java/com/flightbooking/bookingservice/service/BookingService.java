package com.flightbooking.bookingservice.service;

import com.flightbooking.bookingservice.dto.*;
import java.time.LocalDate;
import java.util.List;

public interface BookingService {
    List<FlightSearchResponseDTO> searchFlights(String source, String destination, LocalDate travelDate);
    BookingResponseDTO initiateBooking(BookingRequestDTO request);
    BookingResponseDTO getBookingById(Long bookingId);
    List<BookingResponseDTO> getBookingsByUser(Long userId);
    void updateBookingStatus(Long bookingId, String status);
    BookingResponseDTO cancelBooking(Long bookingId);
}