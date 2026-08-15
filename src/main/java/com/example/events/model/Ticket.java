package com.example.events.model;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "tickets", schema = "ticketing")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "order_item_id", nullable = false)
    private UUID orderItemId;

    @Column(name = "ticket_type_id", nullable = false)
    private UUID ticketTypeId;

    @Column(name = "event_id", nullable = false)
    private UUID eventId;

    @Column(name = "owner_user_id", nullable = false)
    private UUID ownerUserId;

    @Column(name = "seat_id")
    private UUID seatId;

    @Column(nullable = false)
    private String status = "issued";

    @Column(name = "qr_token", nullable = false)
    private UUID qrToken = UUID.randomUUID();

    @Column(name = "issued_at", nullable = false)
    private OffsetDateTime issuedAt = OffsetDateTime.now();

    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt = OffsetDateTime.now();

    public Ticket() {}

    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UUID getQrToken() { return qrToken; }
    public void setQrToken(UUID qrToken) { this.qrToken = qrToken; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
