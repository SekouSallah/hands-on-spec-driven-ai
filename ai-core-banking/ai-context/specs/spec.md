# Spécifications Fonctionnelles — AI Core Banking

## 1. Gestion des Clients (Customer Service)

### 1.1 Création Client
- **Endpoint** : `POST /api/customers`
- **Règles** :
  - Email unique obligatoire
  - Status initial = `ACTIVE`
  - KYC initial = `PENDING`
  - Notification email envoyée à la création

### 1.2 Modification Client
- **Endpoint** : `PUT /api/customers/{id}`
- **Règles** :
  - Seuls firstName, lastName, phone modifiables
  - Email non modifiable après création
  - Client doit exister et être ACTIVE

### 1.3 Désactivation Client
- **Endpoint** : `PATCH /api/customers/{id}/deactivate`
- **Règles** :
  - Status passe à INACTIVE
  - Les comptes associés ne sont PAS fermés automatiquement
  - Un client INACTIVE ne peut pas effectuer de transactions

### 1.4 Consultation Client
- **Endpoint** : `GET /api/customers/{id}`
- **Règles** :
  - Retourne les informations du client
  - Autorisé même en mode Idle

### 1.5 Recherche Clients
- **Endpoint** : `GET /api/customers?search={term}&page={n}&size={s}`
- **Règles** :
  - Recherche sur firstName, lastName, email
  - Pagination obligatoire
  - Autorisé même en mode Idle

## 2. Comptes Bancaires (Account Service)

### 2.1 Création Compte
- **Endpoint** : `POST /api/accounts`
- **Règles** :
  - Le customerId doit référencer un client ACTIVE
  - Numéro de compte auto-généré (format : EB-XXXX-XXXX-XXXX)
  - Balance initiale = 0
  - Currency par défaut = "EUR"

### 2.2 Fermeture Compte
- **Endpoint** : `PATCH /api/accounts/{id}/close`
- **Règles** :
  - Balance doit être à 0 pour fermer
  - Status passe à CLOSED
  - Les cartes associées sont automatiquement bloquées

### 2.3 Gel de Compte
- **Endpoint** : `PATCH /api/accounts/{id}/freeze`
- **Règles** :
  - Status passe à FROZEN
  - Aucune transaction autorisée sur un compte gelé

### 2.4 Consultation Solde
- **Endpoint** : `GET /api/accounts/{id}/balance`
- **Règles** :
  - Retourne le solde en temps réel
  - Autorisé même en mode Idle

## 3. Transactions (Transaction Service)

### 3.1 Virement Interne
- **Endpoint** : `POST /api/transactions/transfer`
- **Règles** :
  - Comptes source et cible doivent être ACTIVE
  - Montant > 0
  - Solde suffisant sur le compte source
  - Référence auto-générée (format : TXN-YYYYMMDD-XXXXX)
  - Status initial = PENDING, passe à COMPLETED si succès
  - Notification envoyée après traitement

### 3.2 Validation Transaction
- **Endpoint** : `PATCH /api/transactions/{id}/validate`
- **Règles** :
  - Seules les transactions PENDING peuvent être validées
  - Le solde est vérifié à nouveau au moment de la validation

### 3.3 Rejet Transaction
- **Endpoint** : `PATCH /api/transactions/{id}/reject`
- **Règles** :
  - Seules les transactions PENDING peuvent être rejetées
  - Un motif de rejet est obligatoire

## 4. Cartes Bancaires (Card Service)

### 4.1 Création Carte
- **Endpoint** : `POST /api/cards`
- **Règles** :
  - Le compte associé doit être ACTIVE
  - PAN auto-généré (16 chiffres)
  - CVV auto-généré (3 chiffres)
  - Expiry = date courante + 3 ans
  - Status initial = ACTIVE

### 4.2 Opposition Carte
- **Endpoint** : `PATCH /api/cards/{id}/oppose`
- **Règles** :
  - Status passe à OPPOSITION
  - Irréversible

### 4.3 Blocage Carte
- **Endpoint** : `PATCH /api/cards/{id}/block`
- **Règles** :
  - Status passe à BLOCKED
  - Réversible (peut être réactivée)

## 5. Fraude (Fraud Service)

### 5.1 Scoring Fraude
- **Règles** :
  - Chaque transaction est analysée à la création
  - Score de 0 (sûr) à 100 (frauduleux)
  - Critères : montant, fréquence, heure, compte

### 5.2 Alertes
- **Règles** :
  - Score ≥ 30 : alerte LOW
  - Score ≥ 50 : alerte MEDIUM
  - Score ≥ 70 : alerte HIGH
  - Score ≥ 90 : alerte CRITICAL
  - Les alertes CRITICAL déclenchent une notification email

### 5.3 Analyse Batch
- **Règles** :
  - Job planifié (CRON) analysant les transactions des dernières 24h
  - Détection de patterns anormaux
