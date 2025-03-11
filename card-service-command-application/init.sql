-- Create tables if they don't exist (should be created by Hibernate, but just in case)
CREATE TABLE IF NOT EXISTS card_limits (
    card_id UUID PRIMARY KEY,
    total_limit DECIMAL(19, 2) NOT NULL,
    available_limit DECIMAL(19, 2) NOT NULL,
    last_updated TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS card_transactions (
    id UUID PRIMARY KEY,
    cnpj VARCHAR(14) NOT NULL,
    amount DECIMAL(19, 2) NOT NULL,
    customer_id UUID NOT NULL,
    description VARCHAR(255) NOT NULL,
    transaction_date TIMESTAMP NOT NULL,
    card_id UUID NOT NULL,
    status VARCHAR(20) NOT NULL,
    dispute_reason VARCHAR(255)
);

-- Insert sample card limit data for testing
INSERT INTO card_limits (card_id, total_limit, available_limit, last_updated)
VALUES
  ('f47ac10b-58cc-4372-a567-0e02b2c3d479', 10000.00, 10000.00, CURRENT_TIMESTAMP),
  ('550e8400-e29b-41d4-a716-446655440000', 5000.00, 5000.00, CURRENT_TIMESTAMP),
  ('6ba7b810-9dad-11d1-80b4-00c04fd430c8', 25000.00, 25000.00, CURRENT_TIMESTAMP);

-- Log the inserted data
SELECT 'Initialized card limits for testing' as log;
SELECT * FROM card_limits;