-- ============================================
-- AI Core Banking — MySQL Initialization
-- ============================================
-- Creates separate databases for each microservice
-- following the database-per-service pattern.
-- ============================================

-- Customer Service Database
CREATE DATABASE IF NOT EXISTS ebanking_customers
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

-- Account Service Database
CREATE DATABASE IF NOT EXISTS ebanking_accounts
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

-- Card Service Database
CREATE DATABASE IF NOT EXISTS ebanking_cards
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

-- Transaction Service Database
CREATE DATABASE IF NOT EXISTS ebanking_transactions
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

-- Fraud Service Database
CREATE DATABASE IF NOT EXISTS ebanking_fraud
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

-- Notification Service Database
CREATE DATABASE IF NOT EXISTS ebanking_notifications
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

-- Grant permissions to the ebanking user
GRANT ALL PRIVILEGES ON ebanking_customers.* TO 'ebanking'@'%';
GRANT ALL PRIVILEGES ON ebanking_accounts.* TO 'ebanking'@'%';
GRANT ALL PRIVILEGES ON ebanking_cards.* TO 'ebanking'@'%';
GRANT ALL PRIVILEGES ON ebanking_transactions.* TO 'ebanking'@'%';
GRANT ALL PRIVILEGES ON ebanking_fraud.* TO 'ebanking'@'%';
GRANT ALL PRIVILEGES ON ebanking_notifications.* TO 'ebanking'@'%';

FLUSH PRIVILEGES;
