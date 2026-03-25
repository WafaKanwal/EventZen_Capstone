package com.eventzen.userservice.service;

import com.eventzen.userservice.exception.AlreadyPresentException;
import com.eventzen.userservice.exception.ResourceNotFoundException;
import com.eventzen.userservice.model.User;
import com.eventzen.userservice.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.eventzen.userservice.util.JwtUtil;
import com.eventzen.userservice.validation.PasswordValidator;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(User user) {
        PasswordValidator.validate(user.getPassword());

        return userRepository.findByEmail(user.getEmail())
                .map(existingUser -> {
                    existingUser.setPassword(null);
                    return existingUser;
                })
                .orElseGet(() -> {
                    user.setPassword(passwordEncoder.encode(user.getPassword()));
                    if (user.getRole() == null || user.getRole().isEmpty()) {
                        user.setRole("CUSTOMER");
                    }
                    return userRepository.save(user);
                });
    }

    public String login(String email, String password) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return JwtUtil.generateToken(user.getId(), user.getEmail(), user.getRole(), user.getName());
    }
}