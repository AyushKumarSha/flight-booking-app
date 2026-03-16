package com.flightbooking.flightservice.service.impl;

import com.flightbooking.flightservice.aggregate.FlightAggregate;
import com.flightbooking.flightservice.dto.*;
import com.flightbooking.flightservice.entity.*;
import com.flightbooking.flightservice.exception.*;
import com.flightbooking.flightservice.repository.*;
import com.flightbooking.flightservice.service.FlightService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class FlightServiceImpl implements FlightService {

    private final FlightRepository flightRepository;
    private final FlightRouteRepository flightRouteRepository;

    @Override
    public FlightRequestDTO addFlight(FlightRequestDTO request) {
        log.info("Adding flight: {}", request.getFlightNumber());
        if (flightRepository.existsByFlightNumber(request.getFlightNumber())) {
            throw new FlightAlreadyExistsException("Flight already exists: " + request.getFlightNumber());
        }
        Flight flight = Flight.builder()
                .flightNumber(request.getFlightNumber())
                .aircraftName(request.getAircraftName())
                .airline(request.getAirline())
                .totalSeats(request.getTotalSeats())
                .build();
        flightRepository.save(flight);
        return request;
    }

    @Override
    public FlightRouteRequestDTO addRoute(FlightRouteRequestDTO request) {
        log.info("Adding route for flightId: {}", request.getFlightId());
        Flight flight = flightRepository.findById(request.getFlightId())
                .orElseThrow(() -> new FlightNotFoundException("Flight not found: " + request.getFlightId()));
        FlightRoute route = FlightRoute.builder()
                .flight(flight)
                .source(request.getSource())
                .destination(request.getDestination())
                .availableFrom(request.getAvailableFrom())
                .availableTo(request.getAvailableTo())
                .departureTime(request.getDepartureTime())
                .arrivalTime(request.getArrivalTime())
                .basePrice(request.getBasePrice())
                .availableSeats(request.getAvailableSeats())
                .build();
        flightRouteRepository.save(route);
        return request;
    }

    @Override
    public List<FlightSearchResponseDTO> searchFlights(String source, String destination, LocalDate travelDate) {
        log.info("Searching flights: {} -> {} on {}", source, destination, travelDate);
        List<FlightRoute> routes = flightRouteRepository.searchFlights(source, destination, travelDate);
        return routes.stream()
                .map(route -> mapToSearchResponse(route, travelDate))
                .collect(Collectors.toList());
    }

    @Override
    public FlightSearchResponseDTO getRouteById(Long routeId) {
        FlightRoute route = flightRouteRepository.findById(routeId)
                .orElseThrow(() -> new FlightNotFoundException("Route not found: " + routeId));
        return mapToSearchResponse(route, LocalDate.now());
    }

    private FlightSearchResponseDTO mapToSearchResponse(FlightRoute route, LocalDate travelDate) {
        return new FlightSearchResponseDTO(
                route.getId(),
                route.getFlight().getFlightNumber(),
                route.getFlight().getAircraftName(),
                route.getFlight().getAirline(),
                route.getSource(),
                route.getDestination(),
                route.getDepartureTime(),
                route.getArrivalTime(),
                route.getAvailableSeats(),
                route.getBasePrice(),
                FlightAggregate.calculateDynamicPrice(route, travelDate)
        );
    }
}