package com.flightbooking.paymentservice.kafka;

import com.flightbooking.paymentservice.dto.PaymentEventDTO;
import com.flightbooking.paymentservice.dto.PaymentResultDTO;
import com.flightbooking.paymentservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentEventConsumer {

    private final PaymentService paymentService;
    private final PaymentResultProducer paymentResultProducer;

    @KafkaListener(topics = "payment-events", groupId = "payment-group")
    public void consumePaymentEvent(PaymentEventDTO event) {
        log.info("Received payment event for bookingId: {}", event.getBookingId());

        // Process and get result
        PaymentResultDTO result = paymentService.processPayment(event);

        // Send result back to booking-service via Kafka (SAGA completion)
        paymentResultProducer.sendPaymentResult(result);
    }
}