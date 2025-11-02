package com.fitness.userservice.services;

import com.fitness.userservice.dto.RegisterRequest;
import com.fitness.userservice.dto.UserResponse;
import org.springframework.stereotype.Service;

import java.util.Optional;

public interface UserService {
    UserResponse register(RegisterRequest request);

    Optional<UserResponse> getUserProfile(String userId);
}
