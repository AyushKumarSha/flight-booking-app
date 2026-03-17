package com.flightbooking.paymentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResultDTO {
    private Long bookingId;
    private String status;
    private String transactionId;
}