package com.flightbooking.flightservice.controller;

import com.flightbooking.flightservice.dto.*;
import com.flightbooking.flightservice.service.FlightService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Flight Service", description = "APIs for managing flights and routes with DDD dynamic pricing")
public class FlightController {

    private final FlightService flightService;

    @Operation(summary = "Add a new flight",
               description = "Creates a new flight (aggregate root)")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Flight added successfully"),
        @ApiResponse(responseCode = "409", description = "Flight already exists")
    })
    @PostMapping
    public ResponseEntity<FlightRequestDTO> addFlight(@Valid @RequestBody FlightRequestDTO request) {
        return ResponseEntity.ok(flightService.addFlight(request));
    }

    @Operation(summary = "Add a route to a flight",
               description = "Adds a route with pricing to an existing flight")
    @ApiResponse(responseCode = "200", description = "Route added successfully")
    @PostMapping("/routes")
    public ResponseEntity<FlightRouteRequestDTO> addRoute(@Valid @RequestBody FlightRouteRequestDTO request) {
        return ResponseEntity.ok(flightService.addRoute(request));
    }

    @Operation(summary = "Search available flights",
               description = "Searches flights by source, destination and date with dynamic pricing applied")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Flights found"),
        @ApiResponse(responseCode = "200", description = "Empty list if no flights available")
    })
    @GetMapping("/search")
    public ResponseEntity<List<FlightSearchResponseDTO>> searchFlights(
            @RequestParam String source,
            @RequestParam String destination,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate travelDate) {
        return ResponseEntity.ok(flightService.searchFlights(source, destination, travelDate));
    }

    @Operation(summary = "Get route by ID")
    @ApiResponse(responseCode = "200", description = "Route found")
    @GetMapping("/routes/{routeId}")
    public ResponseEntity<FlightSearchResponseDTO> getRouteById(@PathVariable Long routeId) {
        return ResponseEntity.ok(flightService.getRouteById(routeId));
    }

    @Operation(summary = "Decrement available seats",
               description = "Reduces seat count by 1 after successful booking")
    @ApiResponse(responseCode = "200", description = "Seat decremented successfully")
    @PutMapping("/routes/{routeId}/decrement-seat")
    public ResponseEntity<Void> decrementSeat(@PathVariable Long routeId) {
        flightService.decrementSeat(routeId);
        return ResponseEntity.ok().build();
    }
}