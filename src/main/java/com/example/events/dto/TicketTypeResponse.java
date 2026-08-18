package com.example.events.dto;

import com.example.events.model.TicketType;
import java.math.BigDecimal;
import java.util.UUID;

public record TicketTypeResponse(
        UUID id,
        UUID eventId,
        String name,
        String description,
        BigDecimal price,
        String currency,
        int quantityTotal,
        int quantitySold,
        int available
) {
    public static TicketTypeResponse from(TicketType t) {
        int available = t.getQuantityTotal() - t.getQuantitySold() - t.getQuantityHeld();
        return new TicketTypeResponse(
                t.getId(), t.getEventId(), t.getName(), t.getDescription(),
                t.getPrice(), t.getCurrency(), t.getQuantityTotal(), t.getQuantitySold(), available
        );
    }
}
