package com.example.events.controller;

import com.example.events.dto.RegisterUserRequest;
import com.example.events.dto.UserResponse;
import com.example.events.model.User;
import com.example.events.repository.UserRepository;
import com.example.events.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse register(@Valid @RequestBody RegisterUserRequest request) {
        User user = userService.register(request);
        return UserResponse.from(user);
    }
}
