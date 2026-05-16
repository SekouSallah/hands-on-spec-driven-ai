# 🏦 AI Core Banking — Workshop Platform

> **Plateforme Core Banking AI-Native** — Support pédagogique pour un workshop intensif de 3 jours sur l'ingénierie logicielle assistée par intelligence artificielle.

---

## 🎯 Vision

Ce projet simule une banque digitale complète permettant la gestion des clients, comptes, cartes bancaires, transactions, fraude, reporting et notifications — le tout piloté par un workflow AI-native structuré.

## 🏗️ Architecture

```text
ai-core-banking/
│
├── e-banking-parent/                      (POM parent Maven)
│
├── e-banking-customer-service/           (Gestion clients & KYC)
├── e-banking-account-service/            (Comptes bancaires)
├── e-banking-card-service/               (Cartes bancaires)
├── e-banking-transaction-service/        (Transactions & virements)
├── e-banking-fraud-service/              (Détection fraude)
├── e-banking-notification-service/       (Notifications email)
│
├── e-banking-backoffice-frontend/        (Angular — Backoffice)
├── e-banking-mobile/                     (Flutter — App mobile)
│
├── infrastructure/                       (Docker, Nginx, MySQL, Mailhog)
├── ai-context/                           (Pilotage IA)
├── docs/                                 (Documentation)
├── docker-compose.yml
└── README.md
```

## 🛠️ Stack Technique

| Composant | Technologie |
|-----------|-------------|
| **Backend** | Java 21, Spring Boot 3, Maven, JPA/Hibernate, Flyway |
| **Base de données** | MySQL |
| **Sécurité** | Spring Security + JWT |
| **Frontend** | Angular + Angular Material |
| **Mobile** | Flutter |
| **Infrastructure** | Docker, Docker Compose, Nginx, Mailhog |
| **IA** | Ollama, Antigravity, Continue.dev, DeepSeek, Qwen |

## 🚀 Démarrage Rapide

### Prérequis

- Java 21+
- Maven 3.9+
- Node.js 20+
- Flutter 3.x
- Docker & Docker Compose

### Lancement avec Docker Compose

```bash
# Démarrer l'infrastructure (MySQL, Mailhog, Nginx)
docker-compose up -d

# Builder tous les microservices
cd e-banking-parent
mvn clean install

# Lancer un microservice
cd ../e-banking-customer-service
mvn spring-boot:run
```

### Variables d'environnement

Chaque microservice utilise `application.properties` (⚠️ **pas de YAML**).

## 📋 Modules

| Service | Port | Description |
|---------|------|-------------|
| customer-service | 8081 | Gestion clients, KYC |
| account-service | 8082 | Comptes bancaires |
| card-service | 8083 | Cartes bancaires |
| transaction-service | 8084 | Transactions financières |
| fraud-service | 8085 | Détection de fraude |
| notification-service | 8086 | Emails & notifications |
| backoffice-frontend | 4200 | Dashboard Angular |
| mobile | — | App Flutter |

## 🤖 AI Context

Le dossier `ai-context/` est le cœur du système d'ingénierie logicielle assistée par IA. Consultez [ai-context/](./ai-context/) pour les specs, règles, prompts et tickets.

## 📜 Licence

Projet pédagogique — Workshop AI Engineering.
