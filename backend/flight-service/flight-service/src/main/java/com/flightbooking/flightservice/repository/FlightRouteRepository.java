package com.flightbooking.flightservice.repository;

import com.flightbooking.flightservice.entity.FlightRoute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;

public interface FlightRouteRepository extends JpaRepository<FlightRoute, Long> {

    @Query("SELECT r FROM FlightRoute r WHERE " +
           "LOWER(r.source) = LOWER(:source) AND " +
           "LOWER(r.destination) = LOWER(:destination) AND " +
           ":travelDate BETWEEN r.availableFrom AND r.availableTo AND " +
           "r.availableSeats > 0")
    List<FlightRoute> searchFlights(
            @Param("source") String source,
            @Param("destination") String destination,
            @Param("travelDate") LocalDate travelDate);
}