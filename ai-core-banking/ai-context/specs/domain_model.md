# Modèle de Domaine — AI Core Banking

## Entités Principales

### Customer (Client)

| Champ | Type | Contraintes | Description |
|-------|------|-------------|-------------|
| id | Long | PK, auto-generated | Identifiant unique |
| firstName | String | NOT NULL, max 100 | Prénom |
| lastName | String | NOT NULL, max 100 | Nom |
| email | String | NOT NULL, UNIQUE | Adresse email |
| phone | String | max 20 | Numéro de téléphone |
| status | Enum | NOT NULL | ACTIVE, INACTIVE, SUSPENDED |
| kycStatus | Enum | NOT NULL | PENDING, VERIFIED, REJECTED |
| createdAt | LocalDateTime | NOT NULL | Date de création |
| updatedAt | LocalDateTime | | Date de dernière modification |

### Account (Compte Bancaire)

| Champ | Type | Contraintes | Description |
|-------|------|-------------|-------------|
| id | Long | PK, auto-generated | Identifiant unique |
| accountNumber | String | NOT NULL, UNIQUE | Numéro de compte (IBAN-like) |
| balance | BigDecimal | NOT NULL, default 0 | Solde du compte |
| currency | String | NOT NULL, default "EUR" | Devise |
| status | Enum | NOT NULL | ACTIVE, CLOSED, FROZEN |
| customerId | Long | NOT NULL, FK | Référence vers Customer |
| createdAt | LocalDateTime | NOT NULL | Date de création |
| updatedAt | LocalDateTime | | Date de dernière modification |

### Card (Carte Bancaire)

| Champ | Type | Contraintes | Description |
|-------|------|-------------|-------------|
| id | Long | PK, auto-generated | Identifiant unique |
| pan | String | NOT NULL, UNIQUE | Numéro de carte (masqué) |
| expiryDate | LocalDate | NOT NULL | Date d'expiration |
| cvv | String | NOT NULL | Code de sécurité (hashé) |
| status | Enum | NOT NULL | ACTIVE, BLOCKED, EXPIRED, OPPOSITION |
| cardType | Enum | NOT NULL | DEBIT, CREDIT |
| accountId | Long | NOT NULL, FK | Référence vers Account |
| createdAt | LocalDateTime | NOT NULL | Date de création |

### Transaction

| Champ | Type | Contraintes | Description |
|-------|------|-------------|-------------|
| id | Long | PK, auto-generated | Identifiant unique |
| reference | String | NOT NULL, UNIQUE | Référence transaction |
| amount | BigDecimal | NOT NULL | Montant |
| type | Enum | NOT NULL | TRANSFER, DEPOSIT, WITHDRAWAL |
| status | Enum | NOT NULL | PENDING, COMPLETED, REJECTED, FAILED |
| sourceAccountId | Long | | Compte source |
| targetAccountId | Long | | Compte cible |
| description | String | max 255 | Libellé |
| createdAt | LocalDateTime | NOT NULL | Date de création |

### FraudAlert (Alerte Fraude)

| Champ | Type | Contraintes | Description |
|-------|------|-------------|-------------|
| id | Long | PK, auto-generated | Identifiant unique |
| level | Enum | NOT NULL | LOW, MEDIUM, HIGH, CRITICAL |
| score | Integer | NOT NULL, 0-100 | Score de risque |
| reason | String | NOT NULL | Raison de l'alerte |
| transactionId | Long | NOT NULL, FK | Référence vers Transaction |
| resolved | Boolean | NOT NULL, default false | Statut résolution |
| createdAt | LocalDateTime | NOT NULL | Date de création |

## Relations

```text
Customer (1) ──── (N) Account
Account  (1) ──── (N) Card
Account  (1) ──── (N) Transaction (source ou target)
Transaction (1) ── (0..1) FraudAlert
```

## Enums

### CustomerStatus
- `ACTIVE` : Client actif
- `INACTIVE` : Client désactivé
- `SUSPENDED` : Client suspendu

### KycStatus
- `PENDING` : En attente de vérification
- `VERIFIED` : Vérifié
- `REJECTED` : Rejeté

### AccountStatus
- `ACTIVE` : Compte actif
- `CLOSED` : Compte fermé
- `FROZEN` : Compte gelé

### CardStatus
- `ACTIVE` : Carte active
- `BLOCKED` : Carte bloquée
- `EXPIRED` : Carte expirée
- `OPPOSITION` : Carte en opposition

### TransactionType
- `TRANSFER` : Virement
- `DEPOSIT` : Dépôt
- `WITHDRAWAL` : Retrait

### TransactionStatus
- `PENDING` : En attente
- `COMPLETED` : Complétée
- `REJECTED` : Rejetée
- `FAILED` : Échouée

### FraudLevel
- `LOW` : Risque faible
- `MEDIUM` : Risque moyen
- `HIGH` : Risque élevé
- `CRITICAL` : Risque critique
