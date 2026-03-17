package com.flightbooking.paymentservice.service.impl;

import com.flightbooking.paymentservice.dto.*;
import com.flightbooking.paymentservice.entity.Payment;
import com.flightbooking.paymentservice.exception.PaymentNotFoundException;
import com.flightbooking.paymentservice.repository.PaymentRepository;
import com.flightbooking.paymentservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    @Override
    public PaymentResultDTO processPayment(PaymentEventDTO event) {
        log.info("Processing payment for bookingId: {}", event.getBookingId());

        String status;
        String transactionId = null;
        String failureReason = null;

        try {
            // Simulate payment processing with validation
            validatePayment(event);

            // Simulate 90% success rate for demo purposes
            boolean paymentSuccess = Math.random() > 0.1;

            if (paymentSuccess) {
                status = "SUCCESSFUL";
                transactionId = "TXN-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
                log.info("Payment successful for bookingId: {} txnId: {}", event.getBookingId(), transactionId);
            } else {
                status = "FAILED";
                failureReason = "Payment declined by bank";
                log.warn("Payment failed for bookingId: {}", event.getBookingId());
            }

        } catch (Exception e) {
            status = "FAILED";
            failureReason = e.getMessage();
            log.error("Payment error for bookingId: {} - {}", event.getBookingId(), e.getMessage());
        }

        // Save payment record
        Payment payment = Payment.builder()
                .bookingId(event.getBookingId())
                .userId(event.getUserId())
                .amount(event.getAmount())
                .paymentMode(event.getPaymentMode())
                .status(status)
                .transactionId(transactionId)
                .failureReason(failureReason)
                .build();

        paymentRepository.save(payment);

        return new PaymentResultDTO(event.getBookingId(), status, transactionId);
    }

    @Override
    public PaymentResponseDTO getPaymentByBookingId(Long bookingId) {
        Payment payment = paymentRepository.findByBookingId(bookingId)
                .orElseThrow(() -> new PaymentNotFoundException("Payment not found for bookingId: " + bookingId));
        return mapToResponse(payment);
    }

    private void validatePayment(PaymentEventDTO event) {
        if ("CARD".equalsIgnoreCase(event.getPaymentMode())) {
            // Validate card number
            if (event.getCardNumber() == null ||
                event.getCardNumber().replaceAll("\\s", "").length() != 16) {
                throw new IllegalArgumentException("Invalid card number");
            }
            // Validate expiry
            try {
                YearMonth expiry = YearMonth.parse(
                    event.getCardExpiry(),
                    DateTimeFormatter.ofPattern("MM/yy")
                );
                if (expiry.isBefore(YearMonth.now())) {
                    throw new IllegalArgumentException("Card has expired");
                }
            } catch (IllegalArgumentException e) {
                throw e;
            } catch (Exception e) {
                throw new IllegalArgumentException("Invalid expiry date format");
            }
        }
    }

    private PaymentResponseDTO mapToResponse(Payment payment) {
        return new PaymentResponseDTO(
                payment.getId(),
                payment.getBookingId(),
                payment.getUserId(),
                payment.getAmount(),
                payment.getPaymentMode(),
                payment.getStatus(),
                payment.getTransactionId(),
                payment.getFailureReason(),
                payment.getCreatedAt()
        );
    }
}