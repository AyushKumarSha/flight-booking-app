package com.flightbooking.bookingservice.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalTime;

@Data
public class FlightSearchResponseDTO {
    private Long routeId;
    private String flightNumber;
    private String aircraftName;
    private String airline;
    private String source;
    private String destination;
    private LocalTime departureTime;
    private LocalTime arrivalTime;
    private Integer availableSeats;
    private BigDecimal basePrice;
    private BigDecimal dynamicPrice;
}