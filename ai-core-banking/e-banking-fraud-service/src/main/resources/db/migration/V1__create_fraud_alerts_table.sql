CREATE TABLE fraud_alerts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    level VARCHAR(50) NOT NULL,
    score INT NOT NULL,
    reason VARCHAR(500) NOT NULL,
    transaction_id BIGINT NOT NULL,
    resolved TINYINT(1) NOT NULL DEFAULT 0,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE INDEX idx_fraud_alerts_level ON fraud_alerts(level);
CREATE INDEX idx_fraud_alerts_transaction ON fraud_alerts(transaction_id);
CREATE INDEX idx_fraud_alerts_resolved ON fraud_alerts(resolved);
CREATE INDEX idx_fraud_alerts_created ON fraud_alerts(created_at);
