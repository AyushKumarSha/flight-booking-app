package com.flightbooking.userservicewrite.service;

import com.flightbooking.userservicewrite.dto.*;

public interface UserWriteService {
    AuthResponseDTO register(RegisterRequestDTO request);
    AuthResponseDTO login(LoginRequestDTO request);
}