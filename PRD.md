# PRD.md — Core Banking AI-Native Workshop (Version Monorepo Enterprise)

# 1. Vision Globale du Projet

Ce projet est une plateforme de Core Banking moderne, conçue comme support pédagogique pour un workshop intensif de 3 jours sur l’ingénierie logicielle assistée par intelligence artificielle (AI Engineering).

Le système simule une banque digitale complète permettant la gestion des clients, comptes, cartes bancaires, transactions, fraude, reporting et notifications.

L’objectif est de démontrer comment construire un système enterprise réel en utilisant des LLM locaux (Ollama) et des outils d’IA comme assistants de développement.

L’IA agit uniquement comme un assistant de l’ingénieur logiciel.

---

# 2. Philosophie IA

L’intelligence artificielle est un copilote de développement.

## Rôles de l’IA

- assister les développeurs
- générer du code
- générer de la documentation
- proposer des architectures
- expliquer les choix techniques
- accélérer le développement
- aider au debugging

## Interdictions de l’IA

- décider seule des règles métier
- modifier l’architecture sans validation humaine
- inventer des comportements bancaires
- produire du code incohérent

Le développeur humain reste le décideur final.

---

# 3. Stack Technique

## Backend

- Java 21
- Spring Boot 3
- Maven multi-modules
- application.properties (STRICTEMENT, pas de YAML)
- Spring Security JWT
- REST APIs
- JPA / Hibernate
- Flyway (migration SQL)
- MySQL

## Frontend Backoffice

- Angular
- Angular Material
- Dashboard bancaire enterprise

## Mobile

- Flutter
- UX bancaire moderne

## Infrastructure

- Docker
- Docker Compose
- Nginx
- Mailhog

## IA

- Ollama
- Antigravity
- Continue.dev
- DeepSeek
- Qwen

---

# 4. Architecture Monorepo (IMPORTANT)

Le projet est un monorepo enterprise.

⚠️ INTERDICTION : aucun dossier backend global.

Tous les modules doivent être à la racine.

---

## Structure du projet

```text
ai-core-banking/
│
├── e-banking-parent/                      (POM parent Maven)
│
├── e-banking-customer-service/           (microservice)
├── e-banking-account-service/            (microservice)
├── e-banking-card-service/               (microservice)
├── e-banking-transaction-service/        (microservice)
├── e-banking-fraud-service/              (microservice)
├── e-banking-notification-service/       (microservice)
│
├── e-banking-backoffice-frontend/        (Angular)
├── e-banking-mobile/                     (Flutter)
│
├── infrastructure/
│   ├── docker/
│   ├── nginx/
│   ├── mysql/
│   └── mailhog/
│
├── ai-context/
├── docs/
├── docker-compose.yml
└── README.md
```

---

# 5. Architecture Backend

Chaque microservice doit être autonome.

Chaque service contient :

- controller
- service
- repository
- entity
- dto
- mapper
- exception handling
- configuration
- application.properties
- flyway migrations

---

# 6. Base de Données

## MySQL + Flyway

Chaque service peut avoir ses propres migrations Flyway.

Structure :

```text
src/main/resources/db/migration
```

Les scripts doivent être versionnés.

---

# 7. Fonctionnalités Métier

## 7.1 Gestion des Clients

- création client
- modification client
- désactivation client
- consultation client
- recherche client
- KYC

---

## 7.2 Comptes Bancaires

- création compte
- fermeture compte
- consultation solde
- historique transactions
- gel de compte

---

## 7.3 Cartes Bancaires

- création carte
- activation
- opposition
- blocage

---

## 7.4 Transactions

- virement interne
- historique
- validation
- rejet

---

## 7.5 Fraude

- détection anomalies
- scoring fraude
- alertes
- analyse batch

---

## 7.6 Notifications

- email création client
- email transaction
- email fraude

---

## 7.7 Reporting

- clients
- comptes
- transactions
- fraude

---

# 8. Mode Idle (Core Banking)

Le système doit supporter un mode Idle global.

## Mode Idle activé :

### Interdit

- création client
- création compte
- transactions
- modification données

### Autorisé

- lecture données
- consultation solde
- reporting

Le mode Idle doit être centralisé et configurable.

---

# 9. Entités Principales

## Customer
- id
- name
- email
- phone
- status

## Account
- id
- number
- balance
- currency
- status

## Card
- id
- pan
- expiry
- status

## Transaction
- id
- amount
- type
- status

## FraudAlert
- id
- level
- score

---

# 10. Backoffice Angular

- gestion clients
- gestion comptes
- gestion cartes
- gestion fraude
- dashboard
- mode idle control

---

# 11. Mobile Flutter

- login
- comptes
- solde
- transactions
- cartes
- notifications

---

# 12. Tickets & Evolution Fonctionnelle (IMPORTANT)

Le système doit intégrer un système de gestion de tickets sous forme de Markdown.

## Types de tickets

- FEATURE (nouvelle fonctionnalité)
- BUG (correction)
- TASK (maintenance)
- ARCHIVE (historique)

## Exemple de ticket

```markdown
# TICKET-001
Type: FEATURE
Titre: Ajouter consultation solde en temps réel
Description: Permettre aux clients de consulter leur solde mis à jour
Priorité: HIGH
Statut: TODO
```

Les tickets servent à piloter les évolutions du système avec l’IA.

---

# 13. AI Context Engineering (AI-CONTEXT)

Le dossier `ai-context` est le cœur du système d’ingénierie logicielle assistée par IA.

Il ne s’agit pas de documentation classique, mais d’un **système de pilotage de l’IA** permettant de contrôler la génération, la cohérence et l’évolution du logiciel.

## Objectif

Le `ai-context` sert à transformer le projet en un système **AI-native**, où l’IA agit comme un assistant structurant et guidé par des règles strictes.

---

## Structure du dossier ai-context

```text
ai-context/
│
├── specs/
│   ├── vision.md
│   ├── architecture.md
│   ├── domain_model.md
│   ├── spec.md
│   ├── security.md
│   ├── fraud-engine.md
│   ├── workflows.md
│   ├── reporting.md
│   ├── error_handling.md
│   └── idle-mode.md
│
├── rules/
│   ├── backend-rules.md
│   ├── angular-rules.md
│   ├── flutter-rules.md
│   ├── database-rules.md
│   ├── git-rules.md
│   └── ai-rules.md
│
├── prompts/
│   ├── architecture.prompt.md
│   ├── backend.prompt.md
│   ├── angular.prompt.md
│   ├── flutter.prompt.md
│   ├── fraud.prompt.md
│   └── ticketing.prompt.md
│
├── workspace/
│   ├── development.md
│   ├── conventions.md
│   ├── rules.md
│   └── code_of_conduct.md
│
├── tickets/
│   ├── active/
│   ├── backlog/
│   ├── in_progress/
│   └── archived/
│
└── meta/
    ├── ai-guidelines.md
    ├── generation-order.md
    └── system-behavior.md
```

---

## Rôle des composants

### 1. specs/
Contient la **source de vérité métier et technique** :

- Vision produit
- Architecture
- Modèle de domaine
- Règles métier
- Sécurité
- Mode Idle
- Fraude

👉 Ces fichiers définissent *ce que le système doit être*.

---

### 2. rules/
Contient les règles de développement :

- conventions backend Spring Boot
- conventions Angular
- conventions Flutter
- règles base de données
- règles Git
- règles d’utilisation de l’IA

👉 Ces fichiers définissent *comment coder*.

---

### 3. prompts/
Contient les prompts réutilisables pour l’IA :

- génération backend
- génération frontend
- génération mobile
- génération fraude
- génération architecture
- génération tickets

👉 Ces fichiers définissent *comment parler à l’IA*.

---

### 4. workspace/
Contient les règles globales de travail :

- workflow de développement
- conventions de nommage
- code of conduct
- règles de collaboration humain + IA

👉 Ces fichiers définissent *comment travailler en équipe humain + IA*.

---

### 5. tickets/
Système de gestion des évolutions du projet en Markdown :

- FEATURE : nouvelle fonctionnalité
- BUG : correction
- TASK : tâche technique
- ARCHIVE : historique

👉 Permet de simuler un système type Jira mais IA-native.

---

### 6. meta/
Contient les règles globales de génération IA :

- ordre obligatoire de génération des fichiers
- comportement de l’IA
- interdictions strictes
- règles de cohérence
- règles d’architecture

👉 C’est le **cerveau de contrôle de l’IA**.

---

## Principe fondamental

Le `ai-context` est chargé dans l’ordre suivant :

1. meta/
2. specs/
3. rules/
4. workspace/
5. prompts/
6. tickets/

L’IA doit toujours respecter cet ordre de priorité.

---

## Règle critique

L’IA ne doit jamais générer de code sans avoir consulté :

- vision.md
- architecture.md
- domain_model.md
- rules/

---

# 14. Règles Globales Règles Globales

- code propre
- architecture scalable
- séparation des responsabilités
- respect DDD
- usage obligatoire application.properties
- pas de YAML
- microservices indépendants
- cohérence globale

---

# 15. Objectif Final

Construire une plateforme bancaire réaliste enterprise avec :

- microservices Java
- Angular backoffice
- Flutter mobile
- MySQL
- Docker
- IA assistée

Le tout piloté par un workflow AI-native structuré.

