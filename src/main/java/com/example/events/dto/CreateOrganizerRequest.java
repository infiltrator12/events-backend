package com.example.events.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateOrganizerRequest(
        @NotBlank String legalName,
        @NotBlank String displayName,
        @NotBlank String slug,
        @NotBlank @Email String supportEmail
) {
}