package com.example.events.controller;

import com.example.events.dto.CreateOrganizerRequest;
import com.example.events.dto.OrganizerResponse;
import com.example.events.model.Organizer;
import com.example.events.security.AppUserDetails;
import com.example.events.service.OrganizerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/organizers")
public class OrganizerController {

    private final OrganizerService organizerService;

    public OrganizerController(OrganizerService organizerService) {
        this.organizerService = organizerService;
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public OrganizerResponse create(
            @Valid @RequestBody CreateOrganizerRequest request,
            @AuthenticationPrincipal AppUserDetails principal
    ) {
        Organizer organizer = organizerService.create(request, principal.getUser().getId());
        return OrganizerResponse.from(organizer);
    }

    @GetMapping("/{id}")
    public OrganizerResponse getById(@PathVariable UUID id) {
        return OrganizerResponse.from(organizerService.getById(id));
    }
}