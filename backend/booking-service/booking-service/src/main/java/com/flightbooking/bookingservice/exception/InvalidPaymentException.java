package com.flightbooking.bookingservice.exception;
public class InvalidPaymentException extends RuntimeException {
    public InvalidPaymentException(String message) { super(message); }
}