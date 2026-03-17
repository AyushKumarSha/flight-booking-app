package com.flightbooking.paymentservice.kafka;

import com.flightbooking.paymentservice.dto.PaymentResultDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentResultProducer {

    private static final String TOPIC = "payment-results";
    private final KafkaTemplate<String, PaymentResultDTO> kafkaTemplate;

    public void sendPaymentResult(PaymentResultDTO result) {
        log.info("Sending payment result for bookingId: {} status: {}",
                result.getBookingId(), result.getStatus());
        kafkaTemplate.send(TOPIC, String.valueOf(result.getBookingId()), result);
    }
}