package com.flightbooking.flightservice.service;

import com.flightbooking.flightservice.dto.*;
import java.time.LocalDate;
import java.util.List;

public interface FlightService {
    FlightRequestDTO addFlight(FlightRequestDTO request);
    FlightRouteRequestDTO addRoute(FlightRouteRequestDTO request);
    List<FlightSearchResponseDTO> searchFlights(String source, String destination, LocalDate travelDate);
    FlightSearchResponseDTO getRouteById(Long routeId);
    void decrementSeat(Long routeId);
}