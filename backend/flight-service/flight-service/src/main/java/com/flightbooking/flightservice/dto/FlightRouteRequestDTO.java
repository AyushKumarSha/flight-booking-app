package com.flightbooking.flightservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class FlightRouteRequestDTO {

    @NotNull
    private Long flightId;

    @NotBlank
    private String source;

    @NotBlank
    private String destination;

    @NotNull
    private LocalDate availableFrom;

    @NotNull
    private LocalDate availableTo;

    @NotNull
    private LocalTime departureTime;

    @NotNull
    private LocalTime arrivalTime;

    @NotNull
    private BigDecimal basePrice;

    @NotNull
    private Integer availableSeats;
}