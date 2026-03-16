package com.flightbooking.flightservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FlightRequestDTO {

    @NotBlank
    private String flightNumber;

    @NotBlank
    private String aircraftName;

    @NotBlank
    private String airline;

    @NotNull
    private Integer totalSeats;
}