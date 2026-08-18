package com.example.events.service;

import com.example.events.dto.CreateTicketTypeRequest;
import com.example.events.exceptions.ForbiddenException;
import com.example.events.exceptions.NotFoundException;
import com.example.events.model.Event;
import com.example.events.model.Organizer;
import com.example.events.model.TicketType;
import com.example.events.repository.TicketTypeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class TicketTypeService {

    private final TicketTypeRepository ticketTypeRepository;
    private final EventService eventService;
    private final OrganizerService organizerService;

    public TicketTypeService(TicketTypeRepository ticketTypeRepository,
                             EventService eventService,
                             OrganizerService organizerService) {
        this.ticketTypeRepository = ticketTypeRepository;
        this.eventService = eventService;
        this.organizerService = organizerService;
    }

    @Transactional
    public TicketType create(CreateTicketTypeRequest request, UUID requestingUserId) {
        Event event = eventService.getById(request.eventId());
        Organizer organizer = organizerService.getById(event.getOrganizerId());

        if (!organizer.getOwnerUserId().equals(requestingUserId)) {
            throw new ForbiddenException("You do not have permission to manage this event's ticket types");
        }

        TicketType ticketType = new TicketType();
        ticketType.setEventId(event.getId());
        ticketType.setName(request.name().trim());
        ticketType.setDescription(request.description());
        ticketType.setPrice(request.price());
        ticketType.setCurrency(request.currency() != null ? request.currency().toUpperCase() : "KES");
        ticketType.setQuantityTotal(request.quantityTotal());

        return ticketTypeRepository.save(ticketType);
    }

    public List<TicketType> listByEvent(UUID eventId) {
        return ticketTypeRepository.findByEventId(eventId);
    }

    public TicketType getById(UUID id) {
        return ticketTypeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Ticket type not found: " + id));
    }
}