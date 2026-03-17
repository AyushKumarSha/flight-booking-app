package com.flightbooking.paymentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentEventDTO {
    private Long bookingId;
    private Long userId;
    private BigDecimal amount;
    private String paymentMode;
    private String cardNumber;
    private String cardExpiry;
}