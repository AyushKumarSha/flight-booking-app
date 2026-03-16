package com.flightbooking.flightservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlightSearchResponseDTO {
    private Long routeId;          // unique id stored in booking MS
    private String flightNumber;
    private String aircraftName;
    private String airline;
    private String source;
    private String destination;
    private LocalTime departureTime;
    private LocalTime arrivalTime;
    private Integer availableSeats;
    private BigDecimal basePrice;
    private BigDecimal dynamicPrice; // price after applying invariants
}