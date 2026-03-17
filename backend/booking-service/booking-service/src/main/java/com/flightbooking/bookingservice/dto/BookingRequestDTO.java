package com.flightbooking.bookingservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;

@Data
public class BookingRequestDTO {

    @NotNull
    private Long userId;

    @NotNull
    private Long routeId;

    @NotNull
    private LocalDate travelDate;

    @NotNull
    private String paymentMode;

    // Card details (used if paymentMode = CARD)
    private String cardNumber;
    private String cardExpiry;
    private String cardCvv;
}