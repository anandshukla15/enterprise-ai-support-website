CREATE TABLE customers (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,

    company_id BIGINT NOT NULL,

    name VARCHAR(150) NOT NULL,

    email VARCHAR(255) NOT NULL,

    phone VARCHAR(30),

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_customer_company
        FOREIGN KEY (company_id)
        REFERENCES companies(id)
        ON DELETE CASCADE,

    CONSTRAINT uk_customer_company_email
        UNIQUE (company_id, email)
);

CREATE TABLE tickets (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,

    company_id BIGINT NOT NULL,

    customer_id BIGINT NOT NULL,

    assigned_agent_id BIGINT NULL,

    title VARCHAR(255) NOT NULL,

    description TEXT NOT NULL,

    priority VARCHAR(20) NOT NULL DEFAULT 'MEDIUM',

    status VARCHAR(30) NOT NULL DEFAULT 'OPEN',

    category VARCHAR(100),

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_ticket_company
        FOREIGN KEY (company_id)
        REFERENCES companies(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_ticket_customer
        FOREIGN KEY (customer_id)
        REFERENCES customers(id)
        ON DELETE RESTRICT,

    CONSTRAINT fk_ticket_assigned_agent
        FOREIGN KEY (assigned_agent_id)
        REFERENCES users(id)
        ON DELETE SET NULL
);

CREATE INDEX idx_customers_company_id
    ON customers(company_id);

CREATE INDEX idx_tickets_company_status
    ON tickets(company_id, status);

CREATE INDEX idx_tickets_customer_id
    ON tickets(customer_id);

CREATE INDEX idx_tickets_assigned_agent_id
    ON tickets(assigned_agent_id);