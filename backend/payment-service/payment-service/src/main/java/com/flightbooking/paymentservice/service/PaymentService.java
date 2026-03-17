package com.flightbooking.paymentservice.service;

import com.flightbooking.paymentservice.dto.*;

public interface PaymentService {
    PaymentResultDTO processPayment(PaymentEventDTO event);
    PaymentResponseDTO getPaymentByBookingId(Long bookingId);
}