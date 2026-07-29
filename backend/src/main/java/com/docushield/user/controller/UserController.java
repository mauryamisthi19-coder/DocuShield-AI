package com.docushield.user.controller;

import com.docushield.user.dto.UserRegistrationRequest;
import com.docushield.user.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public String registerUser(@RequestBody UserRegistrationRequest request) {
        return userService.registerUser(request);
    }
}