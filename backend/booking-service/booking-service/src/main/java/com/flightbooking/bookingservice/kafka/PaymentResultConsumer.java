package com.flightbooking.bookingservice.kafka;

import com.flightbooking.bookingservice.dto.PaymentResultDTO;
import com.flightbooking.bookingservice.service.BookingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentResultConsumer {

    private final BookingService bookingService;

    @KafkaListener(topics = "payment-results", groupId = "booking-group")
    public void consumePaymentResult(PaymentResultDTO result) {
        log.info("Received payment result for bookingId: {} status: {}",
                result.getBookingId(), result.getStatus());
        bookingService.updateBookingStatus(result.getBookingId(), result.getStatus());
    }
}