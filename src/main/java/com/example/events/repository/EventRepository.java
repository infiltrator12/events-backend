package com.example.events.repository;

import com.example.events.model.Event;
import com.example.events.model.EventStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EventRepository extends JpaRepository<Event, UUID> {
    Optional<Event> findBySlug(String slug);
    List<Event> findByOrganizerId(UUID organizerId);
    List<Event> findByStatus(EventStatus status);
}