package com.flightbooking.paymentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponseDTO {
    private Long id;
    private Long bookingId;
    private Long userId;
    private BigDecimal amount;
    private String paymentMode;
    private String status;
    private String transactionId;
    private String failureReason;
    private LocalDateTime createdAt;
}