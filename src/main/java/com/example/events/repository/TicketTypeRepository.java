package com.example.events.repository;

import com.example.events.model.TicketType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface TicketTypeRepository extends JpaRepository<TicketType, UUID> {

    List<TicketType> findByEventId(UUID eventId);

    /**
     * Atomically reserves `qty` tickets. The WHERE clause repeats the
     * capacity check inside the same UPDATE, so Postgres row-locks and
     * checks in one step — two concurrent checkouts can never both
     * succeed past capacity. Returns rows updated: 1 = reserved, 0 = sold out.
     */
    @Modifying
    @Query(value = """
        UPDATE ticketing.ticket_types
           SET quantity_held = quantity_held + :qty
         WHERE id = :ticketTypeId
           AND quantity_sold + quantity_held + :qty <= quantity_total
        """, nativeQuery = true)
    int reserveInventory(@Param("ticketTypeId") UUID ticketTypeId, @Param("qty") int qty);

    @Modifying
    @Query(value = """
        UPDATE ticketing.ticket_types
           SET quantity_held = quantity_held - :qty,
               quantity_sold = quantity_sold + :qty
         WHERE id = :ticketTypeId
        """, nativeQuery = true)
    void confirmSale(@Param("ticketTypeId") UUID ticketTypeId, @Param("qty") int qty);

    @Modifying
    @Query(value = """
        UPDATE ticketing.ticket_types
           SET quantity_held = GREATEST(quantity_held - :qty, 0)
         WHERE id = :ticketTypeId
        """, nativeQuery = true)
    void releaseHold(@Param("ticketTypeId") UUID ticketTypeId, @Param("qty") int qty);
}