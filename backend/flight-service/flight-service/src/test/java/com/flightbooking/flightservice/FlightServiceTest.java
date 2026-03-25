package com.flightbooking.flightservice;

import com.flightbooking.flightservice.aggregate.FlightAggregate;
import com.flightbooking.flightservice.entity.FlightRoute;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import java.math.BigDecimal;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FlightServiceTest {

    @Test
    void contextLoads() {}

    @Test
    void testDynamicPricingWeekend() {
        FlightRoute route = new FlightRoute();
        route.setBasePrice(new BigDecimal("4500.00"));
        route.setAvailableSeats(100);

        // Find a Saturday
        LocalDate saturday = LocalDate.of(2026, 3, 21);
        BigDecimal price = FlightAggregate.calculateDynamicPrice(route, saturday);

        assertTrue(price.compareTo(new BigDecimal("4500.00")) > 0,
                "Weekend price should be higher than base price");
    }

    @Test
    void testDynamicPricingLowSeats() {
        FlightRoute route = new FlightRoute();
        route.setBasePrice(new BigDecimal("4500.00"));
        route.setAvailableSeats(5); // Low seats

        LocalDate weekday = LocalDate.of(2026, 3, 24); // Tuesday
        BigDecimal price = FlightAggregate.calculateDynamicPrice(route, weekday);

        assertTrue(price.compareTo(new BigDecimal("4500.00")) > 0,
                "Low seats price should be higher than base price");
    }

    @Test
    void testDynamicPricingNormalConditions() {
        FlightRoute route = new FlightRoute();
        route.setBasePrice(new BigDecimal("4500.00"));
        route.setAvailableSeats(100); // Normal seats

        LocalDate weekday = LocalDate.of(2026, 3, 24); // Tuesday
        BigDecimal price = FlightAggregate.calculateDynamicPrice(route, weekday);

        assertEquals(new BigDecimal("4500.00"), price,
                "Normal conditions price should equal base price");
    }
}