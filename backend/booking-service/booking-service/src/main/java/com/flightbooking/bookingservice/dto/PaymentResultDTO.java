package com.flightbooking.bookingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResultDTO {
    private Long bookingId;
    private String status; // SUCCESSFUL or FAILED
    private String transactionId;
}