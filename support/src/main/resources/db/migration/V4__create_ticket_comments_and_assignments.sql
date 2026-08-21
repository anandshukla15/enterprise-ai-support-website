CREATE TABLE ticket_comments (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,

    ticket_id BIGINT NOT NULL,

    author_user_id BIGINT NOT NULL,

    content TEXT NOT NULL,

    internal_note BOOLEAN NOT NULL DEFAULT FALSE,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_ticket_comment_ticket
        FOREIGN KEY (ticket_id)
        REFERENCES tickets(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_ticket_comment_author
        FOREIGN KEY (author_user_id)
        REFERENCES users(id)
        ON DELETE RESTRICT
);

CREATE TABLE ticket_assignments (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,

    ticket_id BIGINT NOT NULL,

    assigned_agent_id BIGINT NOT NULL,

    assigned_by_user_id BIGINT NOT NULL,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_ticket_assignment_ticket
        FOREIGN KEY (ticket_id)
        REFERENCES tickets(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_ticket_assignment_agent
        FOREIGN KEY (assigned_agent_id)
        REFERENCES users(id)
        ON DELETE RESTRICT,

    CONSTRAINT fk_ticket_assignment_assigned_by
        FOREIGN KEY (assigned_by_user_id)
        REFERENCES users(id)
        ON DELETE RESTRICT
);

CREATE INDEX idx_ticket_comments_ticket_id
    ON ticket_comments(ticket_id);

CREATE INDEX idx_ticket_assignments_ticket_id
    ON ticket_assignments(ticket_id);