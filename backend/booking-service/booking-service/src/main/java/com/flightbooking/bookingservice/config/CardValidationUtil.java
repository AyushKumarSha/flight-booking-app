package com.flightbooking.bookingservice.config;

import com.flightbooking.bookingservice.exception.InvalidPaymentException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

public class CardValidationUtil {

    public static void validateCard(String cardNumber, String expiry) {
        if (cardNumber == null || cardNumber.replaceAll("\\s", "").length() != 16) {
            throw new InvalidPaymentException("Card number must be 16 digits");
        }
        try {
            YearMonth expiryDate = YearMonth.parse(expiry, DateTimeFormatter.ofPattern("MM/yy"));
            if (expiryDate.isBefore(YearMonth.now())) {
                throw new InvalidPaymentException("Card has expired");
            }
        } catch (Exception e) {
            throw new InvalidPaymentException("Invalid expiry date format. Use MM/YY");
        }
    }
}