package com.example.events.controller;

import com.example.events.dto.LoginRequest;
import com.example.events.dto.LoginResponse;
import com.example.events.dto.UserResponse;
import com.example.events.security.AppUserDetails;
import com.example.events.security.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        // AuthenticationManager delegates to DaoAuthenticationProvider,
        // which uses AppUserDetailsService + PasswordEncoder under the
        // hood. It throws BadCredentialsException (mapped to 401 in
        // GlobalExceptionHandler) for a wrong password OR an unknown
        // email — deliberately the same error for both, so a failed
        // login attempt can't be used to enumerate which emails exist.
        var authToken = new UsernamePasswordAuthenticationToken(
                request.email().trim().toLowerCase(),
                request.password()
        );
        var authentication = authenticationManager.authenticate(authToken);

        AppUserDetails userDetails = (AppUserDetails) authentication.getPrincipal();
        String token = jwtService.generateToken(userDetails);

        return LoginResponse.of(token, UserResponse.from(userDetails.getUser()));
    }
}