package com.aidesk.ticket.repository;

import com.aidesk.ticket.entity.Ticket;
import com.aidesk.ticket.enums.TicketStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TicketRepository extends JpaRepository<Ticket,Long> {

    Optional<Ticket> findByIdAndCompanyId(Long id, Long companyId);

    List<Ticket> findByCompanyId(Long companyId);

    List<Ticket> findByCompanyIdAndStatus(
            Long companyId,
            TicketStatus status
    );

    List<Ticket> findByAssignedAgentIdAndCompanyId(
            Long agentId,
            Long companyId
    );
}
