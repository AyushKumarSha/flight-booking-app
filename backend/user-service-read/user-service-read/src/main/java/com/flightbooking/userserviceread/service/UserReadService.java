package com.flightbooking.userserviceread.service;

import com.flightbooking.userserviceread.dto.UserResponseDTO;
import java.util.List;

public interface UserReadService {
    UserResponseDTO getUserById(Long id);
    UserResponseDTO getUserByEmail(String email);
    List<UserResponseDTO> getAllUsers();
}