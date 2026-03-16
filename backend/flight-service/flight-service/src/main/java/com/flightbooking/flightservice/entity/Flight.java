package com.flightbooking.flightservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "flights")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String flightNumber;

    @NotBlank
    @Column(nullable = false)
    private String aircraftName;

    @NotBlank
    @Column(nullable = false)
    private String airline;

    @Column(nullable = false)
    private Integer totalSeats;

    // One flight has many routes
    @OneToMany(mappedBy = "flight", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Builder.Default
    private List<FlightRoute> routes = new ArrayList<>();
}