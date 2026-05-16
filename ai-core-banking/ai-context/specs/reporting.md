# Reporting — AI Core Banking

## Vue d'Ensemble

Le reporting fournit des données agrégées pour le dashboard backoffice.

## Endpoints de Reporting

### 1. Dashboard KPIs
- **Endpoint** : `GET /api/customers/stats`
- **Retourne** :
  - Nombre total de clients
  - Clients actifs / inactifs
  - Nouveaux clients (7 derniers jours)

### 2. Reporting Comptes
- **Endpoint** : `GET /api/accounts/stats`
- **Retourne** :
  - Nombre total de comptes
  - Comptes actifs / gelés / fermés
  - Solde total agrégé

### 3. Reporting Transactions
- **Endpoint** : `GET /api/transactions/stats`
- **Retourne** :
  - Nombre de transactions par jour (7 derniers jours)
  - Volume total (montant)
  - Transactions par statut

### 4. Reporting Fraude
- **Endpoint** : `GET /api/fraud/stats`
- **Retourne** :
  - Nombre d'alertes par niveau
  - Alertes non résolues
  - Score moyen des alertes
  - Tendance (7 derniers jours)

## Format de Réponse

Toutes les réponses de reporting suivent le format :

```json
{
  "timestamp": "2024-01-15T10:30:00",
  "data": {
    "total": 150,
    "active": 120,
    "inactive": 30
  },
  "period": "LAST_7_DAYS"
}
```
