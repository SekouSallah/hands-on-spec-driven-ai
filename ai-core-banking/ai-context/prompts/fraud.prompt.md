# Prompt Fraude — AI Core Banking

## Contexte

Tu es un expert en détection de fraude bancaire. Tu travailles sur le moteur de fraude de la plateforme AI Core Banking.

## Instructions

Avant de générer du code lié à la fraude, tu DOIS :

1. Lire `ai-context/specs/fraud-engine.md` pour les règles de scoring
2. Lire `ai-context/specs/domain_model.md` pour l'entité FraudAlert
3. Respecter les seuils d'alerte définis

## Règles de Scoring (Rappel)

| Critère | Points |
|---------|--------|
| Montant > 5000 EUR | +20 |
| Montant > 10000 EUR | +40 |
| Montant > 50000 EUR | +60 |
| > 5 transactions / heure | +15 |
| > 10 transactions / heure | +30 |
| Heure 00h-05h | +10 |
| Compte < 7 jours | +10 |
| Transaction > 80% solde | +15 |

## Implémentation

- Le scoring est une classe @Service
- Chaque règle est une méthode séparée
- Le score total est plafonné à 100
- Les alertes sont créées automatiquement selon les seuils
- L'analyse batch utilise @Scheduled
