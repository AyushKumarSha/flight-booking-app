package com.flightbooking.userservicewrite.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponseDTO {
    private String message;
    private String email;
    private String role;
    private Long userId;
}