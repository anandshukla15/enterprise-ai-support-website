ALTER TABLE customers
    ADD COLUMN user_id BIGINT NULL;

ALTER TABLE customers
    ADD CONSTRAINT uk_customers_user_id
        UNIQUE (user_id);

ALTER TABLE customers
    ADD CONSTRAINT fk_customer_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE SET NULL;