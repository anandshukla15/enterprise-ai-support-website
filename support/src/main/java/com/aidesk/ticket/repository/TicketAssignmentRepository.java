package com.aidesk.ticket.repository;

import com.aidesk.ticket.entity.TicketAssignment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketAssignmentRepository
        extends JpaRepository<TicketAssignment, Long> {
}
