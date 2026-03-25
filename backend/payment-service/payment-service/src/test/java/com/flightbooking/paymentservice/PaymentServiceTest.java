package com.flightbooking.paymentservice;

import com.flightbooking.paymentservice.dto.PaymentEventDTO;
import com.flightbooking.paymentservice.dto.PaymentResultDTO;
import com.flightbooking.paymentservice.repository.PaymentRepository;
import com.flightbooking.paymentservice.service.impl.PaymentServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import com.flightbooking.paymentservice.entity.Payment;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    @Test
    void testProcessPaymentUPI() {
        PaymentEventDTO event = new PaymentEventDTO();
        event.setBookingId(1L);
        event.setUserId(1L);
        event.setAmount(new BigDecimal("4500.00"));
        event.setPaymentMode("UPI");

        Payment savedPayment = new Payment();
        savedPayment.setBookingId(1L);
        savedPayment.setStatus("SUCCESSFUL");

        when(paymentRepository.save(any())).thenReturn(savedPayment);

        PaymentResultDTO result = paymentService.processPayment(event);

        assertNotNull(result);
        assertEquals(1L, result.getBookingId());
    }

    @Test
    void testProcessPaymentInvalidCard() {
        PaymentEventDTO event = new PaymentEventDTO();
        event.setBookingId(2L);
        event.setUserId(1L);
        event.setAmount(new BigDecimal("4500.00"));
        event.setPaymentMode("CARD");
        event.setCardNumber("123"); // Invalid
        event.setCardExpiry("12/27");

        Payment savedPayment = new Payment();
        savedPayment.setBookingId(2L);
        savedPayment.setStatus("FAILED");

        when(paymentRepository.save(any())).thenReturn(savedPayment);

        PaymentResultDTO result = paymentService.processPayment(event);

        assertNotNull(result);
        assertEquals("FAILED", result.getStatus());
    }
}