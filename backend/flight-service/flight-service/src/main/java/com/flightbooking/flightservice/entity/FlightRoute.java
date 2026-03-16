package com.flightbooking.flightservice.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "flight_routes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FlightRoute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flight_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Flight flight;

    @Column(nullable = false)
    private String source;

    @Column(nullable = false)
    private String destination;

    @Column(nullable = false)
    private LocalDate availableFrom;

    @Column(nullable = false)
    private LocalDate availableTo;

    @Column(nullable = false)
    private LocalTime departureTime;

    @Column(nullable = false)
    private LocalTime arrivalTime;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal basePrice;

    @Column(nullable = false)
    private Integer availableSeats;

    @PrePersist
    @PreUpdate
    public void validate() {
        if (availableSeats < 0) {
            throw new IllegalStateException("Available seats cannot be negative");
        }
        if (basePrice.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalStateException("Base price must be greater than zero");
        }
    }
}