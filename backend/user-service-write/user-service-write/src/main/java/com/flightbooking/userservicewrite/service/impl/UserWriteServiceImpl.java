package com.flightbooking.userservicewrite.service.impl;

import com.flightbooking.userservicewrite.dto.*;
import com.flightbooking.userservicewrite.entity.User;
import com.flightbooking.userservicewrite.exception.UserAlreadyExistsException;
import com.flightbooking.userservicewrite.exception.InvalidCredentialsException;
import com.flightbooking.userservicewrite.repository.UserRepository;
import com.flightbooking.userservicewrite.service.UserWriteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserWriteServiceImpl implements UserWriteService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthResponseDTO register(RegisterRequestDTO request) {
        log.info("Registering user with email: {}", request.getEmail());

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("User already exists with email: " + request.getEmail());
        }

        User user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role("ROLE_USER")
                .build();

        User saved = userRepository.save(user);
        log.info("User registered successfully with id: {}", saved.getId());

        return new AuthResponseDTO("Registration successful", saved.getEmail(), saved.getRole(), saved.getId());
    }

    @Override
    public AuthResponseDTO login(LoginRequestDTO request) {
        log.info("Login attempt for email: {}", request.getEmail());

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        log.info("Login successful for email: {}", request.getEmail());
        return new AuthResponseDTO("Login successful", user.getEmail(), user.getRole(), user.getId());
    }
}