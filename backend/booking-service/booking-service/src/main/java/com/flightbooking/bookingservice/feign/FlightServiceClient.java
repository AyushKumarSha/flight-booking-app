package com.flightbooking.bookingservice.feign;

import com.flightbooking.bookingservice.dto.FlightSearchResponseDTO;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.time.LocalDate;
import java.util.List;

@FeignClient(name = "flight-service", url = "${feign.client.flight-service.url}")
public interface FlightServiceClient {

    @GetMapping("/api/flights/search")
    List<FlightSearchResponseDTO> searchFlights(
            @RequestParam String source,
            @RequestParam String destination,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate travelDate);

    @GetMapping("/api/flights/routes/{routeId}")
    FlightSearchResponseDTO getRouteById(@PathVariable Long routeId);
    
    @PutMapping("/api/flights/routes/{routeId}/decrement-seat")
    void decrementSeat(@PathVariable Long routeId);
}