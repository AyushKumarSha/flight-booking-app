package com.flightbooking.bookingservice;

import com.flightbooking.bookingservice.config.CardValidationUtil;
import com.flightbooking.bookingservice.exception.InvalidPaymentException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BookingServiceTest {

    @Test
    void contextLoads() {}

    @Test
    void testValidCardNumber() {
        assertDoesNotThrow(() ->
            CardValidationUtil.validateCard("1234567812345678", "12/27"),
            "Valid card should not throw exception"
        );
    }

    @Test
    void testInvalidCardNumberLength() {
        assertThrows(InvalidPaymentException.class, () ->
            CardValidationUtil.validateCard("12345", "12/27"),
            "Short card number should throw exception"
        );
    }

    @Test
    void testExpiredCard() {
        assertThrows(InvalidPaymentException.class, () ->
            CardValidationUtil.validateCard("1234567812345678", "01/20"),
            "Expired card should throw exception"
        );
    }

    @Test
    void testInvalidExpiryFormat() {
        assertThrows(InvalidPaymentException.class, () ->
            CardValidationUtil.validateCard("1234567812345678", "2027/12"),
            "Invalid expiry format should throw exception"
        );
    }
}