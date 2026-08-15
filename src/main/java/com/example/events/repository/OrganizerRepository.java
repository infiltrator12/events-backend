package com.example.events.repository;

import com.example.events.model.Organizer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrganizerRepository extends JpaRepository<Organizer, UUID> {
    Optional<Organizer> findBySlug(String slug);
    List<Organizer> findByOwnerUserId(UUID ownerUserId);
}