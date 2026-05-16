# Moteur de Fraude — AI Core Banking

## Vue d'Ensemble

Le moteur de fraude analyse chaque transaction et attribue un score de risque de 0 à 100.

## Règles de Scoring

### Règle 1 : Montant élevé
- Montant > 5 000 EUR → +20 points
- Montant > 10 000 EUR → +40 points
- Montant > 50 000 EUR → +60 points

### Règle 2 : Fréquence élevée
- Plus de 5 transactions en 1 heure → +15 points
- Plus de 10 transactions en 1 heure → +30 points

### Règle 3 : Heure inhabituelle
- Transaction entre 00h00 et 05h00 → +10 points

### Règle 4 : Nouveau compte
- Compte créé depuis moins de 7 jours → +10 points

### Règle 5 : Solde inhabituel
- Transaction > 80% du solde du compte → +15 points

## Niveaux d'Alerte

| Score | Niveau | Action |
|-------|--------|--------|
| 0-29 | Aucun | Pas d'alerte |
| 30-49 | LOW | Alerte créée, pas de blocage |
| 50-69 | MEDIUM | Alerte créée, notification opérateur |
| 70-89 | HIGH | Alerte créée, transaction en attente de validation manuelle |
| 90-100 | CRITICAL | Alerte créée, transaction bloquée, notification email urgente |

## Analyse Batch

- Exécution : toutes les heures (CRON)
- Périmètre : transactions des dernières 24 heures
- Objectif : détecter des patterns qui ne sont pas visibles transaction par transaction
- Patterns recherchés :
  - Même compte source avec plus de 20 transactions en 24h
  - Transferts circulaires (A → B → C → A)
  - Montants identiques répétés

## API

### Analyser une transaction
- **Endpoint** : `POST /api/fraud/analyze`
- **Input** : TransactionDTO
- **Output** : FraudScoreDTO (score, level, reasons[])

### Consulter les alertes
- **Endpoint** : `GET /api/fraud/alerts?level={level}&resolved={bool}`
- **Pagination** : Oui

### Résoudre une alerte
- **Endpoint** : `PATCH /api/fraud/alerts/{id}/resolve`
