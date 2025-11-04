package com.fitness.userservice.services.impl;

import com.fitness.userservice.dto.RegisterRequest;
import com.fitness.userservice.dto.UserResponse;
import com.fitness.userservice.mapper.UserMapper;
import com.fitness.userservice.models.User;
import com.fitness.userservice.repository.UserRepository;
import com.fitness.userservice.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw  new RuntimeException("Email already exit");
        }
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());

        userRepository.save(user);

        return UserMapper.toDto(user);

    }

    @Override
    public Optional<UserResponse> getUserProfile(String userId) {
        User response = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found!"));

        return Optional.ofNullable(UserMapper.toDto(response));
    }

    @Override
    public Boolean exitsByUserId(String userId) {
        return userRepository.existsById(userId);
    }

}
