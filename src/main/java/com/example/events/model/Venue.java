package com.example.events.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "venues", schema = "ticketing")
public class Venue {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "organizer_id")
    private UUID organizerId;

    @Column(nullable = false)
    private String name;

    @Column(name = "address_line")
    private String addressLine;

    @Column(nullable = false)
    private String city;

    @Column(name = "country_code", nullable = false, length = 2)
    private String countryCode;

    private BigDecimal latitude;
    private BigDecimal longitude;

    @Column(nullable = false)
    private String timezone = "UTC";

    private Integer capacity;

    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt = OffsetDateTime.now();

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt = OffsetDateTime.now();

    public Venue() {}

    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
}
