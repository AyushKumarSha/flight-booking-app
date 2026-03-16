package com.flightbooking.userserviceread.repository;

import com.flightbooking.userserviceread.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserReadRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}