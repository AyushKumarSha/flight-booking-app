package com.flightbooking.flightservice.controller;

import com.flightbooking.flightservice.dto.*;
import com.flightbooking.flightservice.service.FlightService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/flights")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class FlightController {

    private final FlightService flightService;

    @PostMapping
    public ResponseEntity<FlightRequestDTO> addFlight(@Valid @RequestBody FlightRequestDTO request) {
        return ResponseEntity.ok(flightService.addFlight(request));
    }

    @PostMapping("/routes")
    public ResponseEntity<FlightRouteRequestDTO> addRoute(@Valid @RequestBody FlightRouteRequestDTO request) {
        return ResponseEntity.ok(flightService.addRoute(request));
    }

    @GetMapping("/search")
    public ResponseEntity<List<FlightSearchResponseDTO>> searchFlights(
            @RequestParam String source,
            @RequestParam String destination,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate travelDate) {
        return ResponseEntity.ok(flightService.searchFlights(source, destination, travelDate));
    }

    @GetMapping("/routes/{routeId}")
    public ResponseEntity<FlightSearchResponseDTO> getRouteById(@PathVariable Long routeId) {
        return ResponseEntity.ok(flightService.getRouteById(routeId));
    }
}