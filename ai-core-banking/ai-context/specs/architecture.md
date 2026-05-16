# Architecture — AI Core Banking

## Vue d'Ensemble

Le système suit une architecture **microservices** avec un monorepo Maven. Chaque service est autonome, possède sa propre base de données, et communique via REST synchrone.

## Diagramme d'Architecture

```text
                    ┌──────────────┐
                    │    Nginx     │
                    │  (Gateway)   │
                    └──────┬───────┘
                           │
        ┌──────────────────┼──────────────────┐
        │                  │                  │
   ┌────▼────┐       ┌────▼────┐       ┌────▼────┐
   │Customer │       │Account  │       │  Card   │
   │Service  │       │Service  │       │Service  │
   │ :8081   │       │ :8082   │       │ :8083   │
   └────┬────┘       └────┬────┘       └────┬────┘
        │                  │                  │
        │            ┌────▼────┐              │
        │            │Transact.│              │
        │            │Service  │              │
        │            │ :8084   │              │
        │            └────┬────┘              │
        │                  │                  │
        │            ┌────▼────┐              │
        │            │ Fraud   │              │
        │            │Service  │              │
        │            │ :8085   │              │
        │            └────┬────┘              │
        │                  │                  │
        │            ┌────▼────┐              │
        └───────────►│Notific. │◄─────────────┘
                     │Service  │
                     │ :8086   │
                     └────┬────┘
                          │
                     ┌────▼────┐
                     │ Mailhog │
                     │ :1025   │
                     └─────────┘
```

## Principes Architecturaux

### 1. Database per Service
Chaque microservice possède sa propre base de données MySQL. Aucun partage de schéma entre services.

| Service | Base de données |
|---------|----------------|
| customer-service | `ebanking_customers` |
| account-service | `ebanking_accounts` |
| card-service | `ebanking_cards` |
| transaction-service | `ebanking_transactions` |
| fraud-service | `ebanking_fraud` |
| notification-service | `ebanking_notifications` |

### 2. Communication Synchrone (REST)
Les services communiquent via des appels REST. Chaque service expose une API REST documentée via OpenAPI/Swagger.

### 3. API Gateway (Nginx)
Nginx sert de point d'entrée unique et route les requêtes vers les microservices appropriés via les préfixes `/api/*`.

### 4. Sécurité Centralisée (JWT)
L'authentification utilise des tokens JWT. Chaque service valide le token de manière indépendante.

### 5. Migration de Données (Flyway)
Chaque service gère ses propres migrations SQL via Flyway, versionnées dans `src/main/resources/db/migration/`.

## Couches Applicatives (par service)

```text
┌─────────────────────┐
│     Controller      │  ← REST endpoints
├─────────────────────┤
│      Service        │  ← Business logic
├─────────────────────┤
│     Repository      │  ← Data access (JPA)
├─────────────────────┤
│      Entity         │  ← Domain model
├─────────────────────┤
│    DTO / Mapper     │  ← Data transfer
├─────────────────────┤
│   Exception Handler │  ← Error management
├─────────────────────┤
│   Configuration     │  ← Security, CORS, etc.
└─────────────────────┘
```

## Packages Convention

```text
com.ebanking.{service}
├── config/
├── controller/
├── dto/
├── entity/
├── exception/
├── mapper/
├── repository/
└── service/
    └── impl/
```
