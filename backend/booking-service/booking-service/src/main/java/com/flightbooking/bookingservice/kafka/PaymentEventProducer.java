package com.flightbooking.bookingservice.kafka;

import com.flightbooking.bookingservice.dto.PaymentEventDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentEventProducer {

    private static final String TOPIC = "payment-events";
    private final KafkaTemplate<String, PaymentEventDTO> kafkaTemplate;

    public void sendPaymentEvent(PaymentEventDTO event) {
        log.info("Sending payment event for bookingId: {}", event.getBookingId());
        kafkaTemplate.send(TOPIC, String.valueOf(event.getBookingId()), event);
    }
}