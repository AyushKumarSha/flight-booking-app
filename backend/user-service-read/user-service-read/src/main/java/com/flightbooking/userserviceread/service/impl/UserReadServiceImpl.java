package com.flightbooking.userserviceread.service.impl;

import com.flightbooking.userserviceread.dto.UserResponseDTO;
import com.flightbooking.userserviceread.entity.User;
import com.flightbooking.userserviceread.exception.UserNotFoundException;
import com.flightbooking.userserviceread.repository.UserReadRepository;
import com.flightbooking.userserviceread.service.UserReadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserReadServiceImpl implements UserReadService {

    private final UserReadRepository userReadRepository;

    @Override
    public UserResponseDTO getUserById(Long id) {
        log.info("Fetching user by id: {}", id);
        User user = userReadRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
        return mapToDTO(user);
    }

    @Override
    public UserResponseDTO getUserByEmail(String email) {
        log.info("Fetching user by email: {}", email);
        User user = userReadRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));
        return mapToDTO(user);
    }

    @Override
    public List<UserResponseDTO> getAllUsers() {
        log.info("Fetching all users");
        return userReadRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private UserResponseDTO mapToDTO(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.getRole(),
                user.getCreatedAt()
        );
    }
}