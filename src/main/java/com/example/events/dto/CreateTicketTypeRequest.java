package com.example.events.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.UUID;

public record CreateTicketTypeRequest(
        @NotNull UUID eventId,
        @NotBlank String name,
        String description,
        @NotNull @DecimalMin("0.0") BigDecimal price,
        @Size(min = 3, max = 3) String currency,
        @Min(1) int quantityTotal
) {
}
