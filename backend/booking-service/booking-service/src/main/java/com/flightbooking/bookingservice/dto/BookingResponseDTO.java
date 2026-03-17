package com.flightbooking.bookingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingResponseDTO {
    private Long bookingId;
    private Long userId;
    private String flightNumber;
    private String source;
    private String destination;
    private LocalDate travelDate;
    private BigDecimal amount;
    private String paymentMode;
    private String status;
    private LocalDateTime createdAt;
}