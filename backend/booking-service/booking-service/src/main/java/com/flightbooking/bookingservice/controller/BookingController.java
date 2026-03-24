package com.flightbooking.bookingservice.controller;

import com.flightbooking.bookingservice.dto.*;
import com.flightbooking.bookingservice.service.BookingService;
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
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Booking Service", description = "Master service - search flights, initiate bookings and SAGA orchestration")
public class BookingController {

    private final BookingService bookingService;

    @Operation(summary = "Search flights",
               description = "Calls Flight Service via OpenFeign to search available flights")
    @ApiResponse(responseCode = "200", description = "List of available flights")
    @GetMapping("/search")
    public ResponseEntity<List<FlightSearchResponseDTO>> searchFlights(
            @RequestParam String source,
            @RequestParam String destination,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate travelDate) {
        return ResponseEntity.ok(bookingService.searchFlights(source, destination, travelDate));
    }

    @Operation(summary = "Initiate a booking",
               description = "Creates a booking with INITIATED status and sends Kafka event to payment service (SAGA begin)")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Booking initiated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid payment details")
    })
    @PostMapping
    public ResponseEntity<BookingResponseDTO> initiateBooking(
            @Valid @RequestBody BookingRequestDTO request) {
        return ResponseEntity.ok(bookingService.initiateBooking(request));
    }

    @Operation(summary = "Get booking by ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Booking found"),
        @ApiResponse(responseCode = "404", description = "Booking not found")
    })
    @GetMapping("/{bookingId}")
    public ResponseEntity<BookingResponseDTO> getBookingById(@PathVariable Long bookingId) {
        return ResponseEntity.ok(bookingService.getBookingById(bookingId));
    }

    @Operation(summary = "Get all bookings by user")
    @ApiResponse(responseCode = "200", description = "List of user bookings")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BookingResponseDTO>> getBookingsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(bookingService.getBookingsByUser(userId));
    }

    @Operation(summary = "Cancel a booking",
               description = "Cancels an INITIATED booking (SAGA compensation transaction)")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Booking cancelled"),
        @ApiResponse(responseCode = "400", description = "Cannot cancel a successful booking")
    })
    @PutMapping("/{bookingId}/cancel")
    public ResponseEntity<BookingResponseDTO> cancelBooking(@PathVariable Long bookingId) {
        return ResponseEntity.ok(bookingService.cancelBooking(bookingId));
    }
}