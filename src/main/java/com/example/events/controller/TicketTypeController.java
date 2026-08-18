package com.example.events.controller;

import com.example.events.dto.CreateTicketTypeRequest;
import com.example.events.dto.TicketTypeResponse;
import com.example.events.model.TicketType;
import com.example.events.security.AppUserDetails;
import com.example.events.service.TicketTypeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/ticket-types")
public class TicketTypeController {

    private final TicketTypeService ticketTypeService;

    public TicketTypeController(TicketTypeService ticketTypeService) {
        this.ticketTypeService = ticketTypeService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TicketTypeResponse create(
            @Valid @RequestBody CreateTicketTypeRequest request,
            @AuthenticationPrincipal AppUserDetails principal
    ) {
        TicketType tt = ticketTypeService.create(request, principal.getUser().getId());
        return TicketTypeResponse.from(tt);
    }

    @GetMapping("/event/{eventId}")
    public List<TicketTypeResponse> listByEvent(@PathVariable UUID eventId) {
        return ticketTypeService.listByEvent(eventId).stream()
                .map(TicketTypeResponse::from)
                .toList();
    }

    @GetMapping("/{id}")
    public TicketTypeResponse getById(@PathVariable UUID id) {
        return TicketTypeResponse.from(ticketTypeService.getById(id));
    }
}