package com.aidesk.ticket.repository;

import com.aidesk.ticket.entity.TicketAssignment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketAssignmentRepository
        extends JpaRepository<TicketAssignment, Long> {

    List<TicketAssignment> findByTicketIdOrderByCreatedAtDesc(Long ticketId);
}
