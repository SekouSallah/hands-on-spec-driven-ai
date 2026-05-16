# Règles Base de Données — AI Core Banking

## Moteur

- MySQL 8.0
- Charset : `utf8mb4`
- Collation : `utf8mb4_unicode_ci`

## Conventions de Nommage

### Tables
- snake_case, pluriel (`customers`, `bank_accounts`, `fraud_alerts`)
- Préfixe par service si ambiguïté

### Colonnes
- snake_case (`first_name`, `account_number`, `created_at`)
- Clé primaire : `id` (BIGINT, AUTO_INCREMENT)
- Clé étrangère : `{table_singulier}_id` (ex: `customer_id`, `account_id`)

### Index
- Nommage : `idx_{table}_{colonnes}` (ex: `idx_customers_email`)
- Index unique : `uk_{table}_{colonnes}` (ex: `uk_customers_email`)

## Types de Données

| Concept | Type MySQL | Type Java |
|---------|-----------|-----------|
| Identifiant | BIGINT | Long |
| Texte court | VARCHAR(n) | String |
| Texte long | TEXT | String |
| Montant | DECIMAL(19,4) | BigDecimal |
| Booléen | TINYINT(1) | Boolean |
| Date | DATE | LocalDate |
| Date+Heure | DATETIME | LocalDateTime |
| Enum | VARCHAR(50) | Enum (String) |

## Migrations Flyway

### Règles
1. Une migration par changement de schéma
2. Nommage : `V{n}__{description}.sql` (double underscore)
3. JAMAIS modifier une migration existante après commit
4. Toujours tester la migration sur une base vide

### Exemple

```sql
-- V1__create_customers_table.sql

CREATE TABLE customers (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL,
    phone VARCHAR(20),
    status VARCHAR(50) NOT NULL DEFAULT 'ACTIVE',
    kyc_status VARCHAR(50) NOT NULL DEFAULT 'PENDING',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT uk_customers_email UNIQUE (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE INDEX idx_customers_status ON customers(status);
CREATE INDEX idx_customers_last_name ON customers(last_name);
```

## Interdictions

- ❌ Pas de suppression physique (soft delete ou désactivation)
- ❌ Pas de `SELECT *` dans les requêtes custom
- ❌ Pas de données de test dans les migrations (utiliser des scripts séparés)
- ❌ Pas de procédures stockées (logique dans le service Java)
- ❌ Pas de triggers (logique dans le service Java)
