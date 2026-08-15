package com.example.events.dto;

import com.example.events.model.Organizer;
import java.math.BigDecimal;
import java.util.UUID;

public record OrganizerResponse(
        UUID id,
        UUID ownerUserId,
        String legalName,
        String displayName,
        String slug,
        String supportEmail,
        BigDecimal commissionRate
) {
    public static OrganizerResponse from(Organizer o) {
        return new OrganizerResponse(
                o.getId(), o.getOwnerUserId(), o.getLegalName(), o.getDisplayName(),
                o.getSlug(), o.getSupportEmail(), o.getCommissionRate()
        );
    }
}