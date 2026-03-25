package com.flightbooking.flightservice.aggregate;

import com.flightbooking.flightservice.entity.FlightRoute;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;


public class FlightAggregate {

    private static final BigDecimal WEEKEND_MULTIPLIER = new BigDecimal("1.20");   // 20% more on weekends
    private static final BigDecimal WEEKDAY_MULTIPLIER = new BigDecimal("1.00");   // base price on weekdays
    private static final BigDecimal LOW_SEATS_MULTIPLIER = new BigDecimal("1.30"); // 30% more when < 10 seats
    private static final BigDecimal MID_SEATS_MULTIPLIER = new BigDecimal("1.10"); // 10% more when < 30 seats

    
    public static BigDecimal calculateDynamicPrice(FlightRoute route, LocalDate travelDate) {
        BigDecimal price = route.getBasePrice();

        price = applyDayOfWeekPricing(price, travelDate);

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