package com.example.events.dto;

import com.example.events.model.Event;
import java.time.OffsetDateTime;
import java.util.UUID;

public record EventResponse(
        UUID id,
        UUID organizerId,
        String title,
        String slug,
        String status,
        OffsetDateTime startsAt,
        OffsetDateTime endsAt
) {
    public static EventResponse from(Event e) {
        return new EventResponse(
                e.getId(), e.getOrganizerId(), e.getTitle(), e.getSlug(),
                e.getStatus().name(), e.getStartsAt(), e.getEndsAt()
        );
    }
}