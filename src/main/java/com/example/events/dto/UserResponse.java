package com.example.events.dto;

import com.example.events.model.User;
import java.time.OffsetDateTime;
import java.util.UUID;


public record UserResponse(
        UUID id,
        String email,
        String firstName,
        String lastName,
        String status,
        OffsetDateTime createdAt
) {
    public static UserResponse from(User user) {
        return new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getStatus().name(),
                user.getCreatedAt()
        );
    }
}
