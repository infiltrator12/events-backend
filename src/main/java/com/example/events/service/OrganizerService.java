package com.example.events.service;

import com.example.events.dto.CreateOrganizerRequest;
import com.example.events.exceptions.NotFoundException;
import com.example.events.model.Organizer;
import com.example.events.repository.OrganizerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Service
public class OrganizerService {

    private final OrganizerRepository organizerRepository;

    public OrganizerService(OrganizerRepository organizerRepository) {
        this.organizerRepository = organizerRepository;
    }

    @Transactional
    public Organizer create(CreateOrganizerRequest request, UUID ownerUserId) {
        Organizer organizer = new Organizer();
        organizer.setOwnerUserId(ownerUserId);
        organizer.setLegalName(request.legalName().trim());
        organizer.setDisplayName(request.displayName().trim());
        organizer.setSlug(request.slug().trim().toLowerCase());
        organizer.setSupportEmail(request.supportEmail().trim().toLowerCase());
        return organizerRepository.save(organizer);
    }

    public Organizer getById(UUID id) {
        return organizerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Organizer not found: " + id));
    }
}