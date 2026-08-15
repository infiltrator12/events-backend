package com.example.events.controller;

import com.example.events.dto.CreateEventRequest;
import com.example.events.dto.EventResponse;
import com.example.events.model.Event;
import com.example.events.security.AppUserDetails;
import com.example.events.service.EventService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventResponse create(
            @Valid @RequestBody CreateEventRequest request,
            @AuthenticationPrincipal AppUserDetails principal
    ) {
        Event event = eventService.create(request, principal.getUser().getId());
        return EventResponse.from(event);
    }

    @PostMapping("/{id}/publish")
    public EventResponse publish(
            @PathVariable UUID id,
            @AuthenticationPrincipal AppUserDetails principal
    ) {
        Event event = eventService.publish(id, principal.getUser().getId());
        return EventResponse.from(event);
    }

    @GetMapping
    public List<EventResponse> listPublished() {
        return eventService.listPublished().stream().map(EventResponse::from).toList();
    }

    @GetMapping("/{id}")
    public EventResponse getById(@PathVariable UUID id) {
        return EventResponse.from(eventService.getById(id));
    }

    @GetMapping("/organizer/{organizerId}")
    public List<EventResponse> listForOrganizer(
            @PathVariable UUID organizerId,
            @AuthenticationPrincipal AppUserDetails principal
    ) {
        return eventService.listForOrganizer(organizerId, principal.getUser().getId()).stream()
                .map(EventResponse::from).toList();
    }
}