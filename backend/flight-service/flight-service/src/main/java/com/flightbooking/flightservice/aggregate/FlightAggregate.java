package com.flightbooking.flightservice.aggregate;

import com.flightbooking.flightservice.entity.FlightRoute;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

/**
 * DDD Aggregate Root behavior class.
 * Encapsulates invariants (business rules) for flight pricing.
 * Invariant 1: Dynamic pricing based on day of week
 * Invariant 2: Dynamic pricing based on seat availability
 */
public class FlightAggregate {

    private static final BigDecimal WEEKEND_MULTIPLIER = new BigDecimal("1.20");   // 20% more on weekends
    private static final BigDecimal WEEKDAY_MULTIPLIER = new BigDecimal("1.00");   // base price on weekdays
    private static final BigDecimal LOW_SEATS_MULTIPLIER = new BigDecimal("1.30"); // 30% more when < 10 seats
    private static final BigDecimal MID_SEATS_MULTIPLIER = new BigDecimal("1.10"); // 10% more when < 30 seats

    /**
     * Calculates dynamic price based on:
     * - Day of week (weekend = more expensive)
     * - Seats availability (fewer seats = more expensive)
     */
    public static BigDecimal calculateDynamicPrice(FlightRoute route, LocalDate travelDate) {
        BigDecimal price = route.getBasePrice();

        // Invariant 1: Weekend pricing
        price = applyDayOfWeekPricing(price, travelDate);

        // Invariant 2: Seat availability pricing
        price = applySeatAvailabilityPricing(price, route.getAvailableSeats());

        return price.setScale(2, RoundingMode.HALF_UP);
    }

    private static BigDecimal applyDayOfWeekPricing(BigDecimal price, LocalDate date) {
        return switch (date.getDayOfWeek()) {
            case SATURDAY, SUNDAY -> price.multiply(WEEKEND_MULTIPLIER);
            default -> price.multiply(WEEKDAY_MULTIPLIER);
        };
    }

    private static BigDecimal applySeatAvailabilityPricing(BigDecimal price, int availableSeats) {
        if (availableSeats < 10) {
            return price.multiply(LOW_SEATS_MULTIPLIER);
        } else if (availableSeats < 30) {
            return price.multiply(MID_SEATS_MULTIPLIER);
        }
        return price;
    }
}