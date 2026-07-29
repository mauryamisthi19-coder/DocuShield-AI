package com.docushield.user.service;

import com.docushield.user.dto.UserRegistrationRequest;
import com.docushield.user.entity.Role;
import com.docushield.user.entity.User;
import com.docushield.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String registerUser(UserRegistrationRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            return "Email already registered";
        }

        User user = new User();

        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());

        // For now we'll store the password directly.
        // In the next step we'll replace this with BCrypt encryption.
        user.setPassword(request.getPassword());

        user.setRole(Role.OFFICER);

        userRepository.save(user);

        return "User registered successfully";
    }
}