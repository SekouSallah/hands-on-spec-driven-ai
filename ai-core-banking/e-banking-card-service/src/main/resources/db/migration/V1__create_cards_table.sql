CREATE TABLE cards (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    pan VARCHAR(16) NOT NULL,
    expiry_date DATE NOT NULL,
    cvv VARCHAR(60) NOT NULL,
    status VARCHAR(50) NOT NULL DEFAULT 'ACTIVE',
    card_type VARCHAR(50) NOT NULL,
    account_id BIGINT NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uk_cards_pan UNIQUE (pan)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE INDEX idx_cards_account_id ON cards(account_id);
CREATE INDEX idx_cards_status ON cards(status);
