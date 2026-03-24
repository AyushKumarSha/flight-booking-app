package com.flightbooking.paymentservice.controller;

import com.flightbooking.paymentservice.dto.PaymentResponseDTO;
import com.flightbooking.paymentservice.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Payment Service", description = "Payment processing via Kafka SAGA async communication")
public class PaymentController {

    private final PaymentService paymentService;

    @Operation(summary = "Get payment by booking ID",
               description = "Retrieves payment details after SAGA completion")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Payment record found"),
        @ApiResponse(responseCode = "404", description = "Payment not found for this booking")
    })
    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<PaymentResponseDTO> getPaymentByBookingId(@PathVariable Long bookingId) {
        return ResponseEntity.ok(paymentService.getPaymentByBookingId(bookingId));
    }
}