package com.example.events.service;

import com.example.events.dto.CreateEventRequest;
import com.example.events.exceptions.ForbiddenException;
import com.example.events.exceptions.NotFoundException;
import com.example.events.model.Event;
import com.example.events.model.EventStatus;
import com.example.events.model.Organizer;
import com.example.events.repository.EventRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final OrganizerService organizerService;

    public EventService(EventRepository eventRepository, OrganizerService organizerService) {
        this.eventRepository = eventRepository;
        this.organizerService = organizerService;
    }

    @Transactional
    public Event create(CreateEventRequest request, UUID requestingUserId) {
        if (!request.endsAt().isAfter(request.startsAt())) {
            throw new IllegalArgumentException("endsAt must be after startsAt");
        }

        Organizer organizer = organizerService.getById(request.organizerId());
        assertOwnership(organizer, requestingUserId);

        Event event = new Event();
        event.setOrganizerId(organizer.getId());
        event.setVenueId(request.venueId());
        event.setTitle(request.title().trim());
        event.setSlug(request.slug().trim().toLowerCase());
        event.setDescription(request.description());
        event.setSeated(request.isSeated());
        event.setStartsAt(request.startsAt());
        event.setEndsAt(request.endsAt());
        if (request.timezone() != null && !request.timezone().isBlank()) {
            event.setTimezone(request.timezone());
        }
        event.setStatus(EventStatus.DRAFT);

        return eventRepository.save(event);
    }

    @Transactional
    public Event publish(UUID eventId, UUID requestingUserId) {
        Event event = getById(eventId);
        Organizer organizer = organizerService.getById(event.getOrganizerId());
        assertOwnership(organizer, requestingUserId);

        event.setStatus(EventStatus.PUBLISHED);
        return eventRepository.save(event);
    }

    public List<Event> listPublished() {
        return eventRepository.findByStatus(EventStatus.PUBLISHED);
    }

    public List<Event> listForOrganizer(UUID organizerId, UUID requestingUserId) {
        Organizer organizer = organizerService.getById(organizerId);
        assertOwnership(organizer, requestingUserId);
        return eventRepository.findByOrganizerId(organizerId);
    }

    public Event getById(UUID id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Event not found: " + id));
    }

    private void assertOwnership(Organizer organizer, UUID requestingUserId) {
        if (!organizer.getOwnerUserId().equals(requestingUserId)) {
            throw new ForbiddenException("You do not have permission to manage this organizer's events");
        }
    }
}