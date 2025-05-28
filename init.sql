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
