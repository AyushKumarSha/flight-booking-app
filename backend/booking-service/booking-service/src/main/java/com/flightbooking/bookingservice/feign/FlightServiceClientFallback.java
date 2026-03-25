package com.flightbooking.bookingservice.feign;

import com.flightbooking.bookingservice.dto.FlightSearchResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Component
@Slf4j
public class FlightServiceClientFallback implements FlightServiceClient {

    @Override
    public List<FlightSearchResponseDTO> searchFlights(
            String source, String destination, LocalDate travelDate) {
        log.error("Circuit Breaker OPEN — Flight search unavailable. " +
                  "Falling back for route: {} -> {}", source, destination);
        return Collections.emptyList();
    }

    @Override
    public FlightSearchResponseDTO getRouteById(Long routeId) {
        log.error("Circuit Breaker OPEN — Could not fetch route: {}", routeId);
        return null;
    }

    @Override
    public void decrementSeat(Long routeId) {
        log.error("Circuit Breaker OPEN — Could not decrement seat for route: {}", routeId);
    }
}