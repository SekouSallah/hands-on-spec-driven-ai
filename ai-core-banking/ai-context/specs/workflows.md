# Workflows — AI Core Banking

## 1. Workflow Création Client

```text
[Opérateur] → POST /api/customers
    │
    ├── Validation données
    ├── Création en base (status=ACTIVE, kyc=PENDING)
    ├── Notification email (via notification-service)
    └── Réponse 201 Created
```

## 2. Workflow Ouverture de Compte

```text
[Opérateur] → POST /api/accounts
    │
    ├── Vérification customer existe et est ACTIVE
    ├── Génération numéro de compte unique
    ├── Création en base (balance=0, status=ACTIVE)
    └── Réponse 201 Created
```

## 3. Workflow Virement Interne

```text
[Opérateur/Client] → POST /api/transactions/transfer
    │
    ├── Vérification compte source ACTIVE
    ├── Vérification compte cible ACTIVE
    ├── Vérification solde suffisant
    ├── Création transaction (status=PENDING)
    │
    ├── Analyse fraude (fraud-service)
    │   ├── Score < 70 → Transaction validée automatiquement
    │   │   ├── Débit compte source
    │   │   ├── Crédit compte cible
    │   │   ├── Status → COMPLETED
    │   │   └── Notification email
    │   │
    │   └── Score ≥ 70 → Transaction en attente
    │       ├── Status reste PENDING
    │       ├── Alerte fraude créée
    │       └── Notification opérateur
    │
    └── Réponse 201 Created (avec status)
```

## 4. Workflow Opposition Carte

```text
[Opérateur/Client] → PATCH /api/cards/{id}/oppose
    │
    ├── Vérification carte existe
    ├── Status → OPPOSITION (irréversible)
    ├── Notification email client
    └── Réponse 200 OK
```

## 5. Workflow Détection Fraude Batch

```text
[CRON toutes les heures]
    │
    ├── Récupérer transactions des dernières 24h
    ├── Analyser patterns
    │   ├── Fréquence anormale
    │   ├── Transferts circulaires
    │   └── Montants répétés
    ├── Créer alertes si nécessaire
    └── Notifier les opérateurs
```

## 6. Workflow Mode Idle

```text
[Admin] → PATCH /api/system/idle?enabled=true
    │
    ├── Activation globale du mode Idle
    ├── Tous les endpoints d'écriture retournent 503 Service Unavailable
    ├── Les endpoints de lecture restent accessibles
    └── Réponse 200 OK
```
