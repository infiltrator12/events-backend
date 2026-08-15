package com.example.events.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.UUID;

public record CreateEventRequest(
        @NotNull UUID organizerId,
        UUID venueId,
        @NotBlank String title,
        @NotBlank String slug,
        String description,
        boolean isSeated,
        @NotNull @Future OffsetDateTime startsAt,
        @NotNull OffsetDateTime endsAt,
        String timezone
) {
}