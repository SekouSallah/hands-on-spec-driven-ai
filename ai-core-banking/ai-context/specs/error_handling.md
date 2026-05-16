# Gestion des Erreurs — AI Core Banking

## Format Standard des Erreurs

Toutes les erreurs API suivent le format :

```json
{
  "timestamp": "2024-01-15T10:30:00",
  "status": 404,
  "error": "Not Found",
  "message": "Customer not found with id: 42",
  "path": "/api/customers/42",
  "code": "CUSTOMER_NOT_FOUND"
}
```

## Codes d'Erreur par Service

### Customer Service

| Code | HTTP Status | Description |
|------|-------------|-------------|
| `CUSTOMER_NOT_FOUND` | 404 | Client introuvable |
| `CUSTOMER_EMAIL_EXISTS` | 409 | Email déjà utilisé |
| `CUSTOMER_INACTIVE` | 400 | Opération sur client inactif |
| `CUSTOMER_VALIDATION_ERROR` | 400 | Données invalides |

### Account Service

| Code | HTTP Status | Description |
|------|-------------|-------------|
| `ACCOUNT_NOT_FOUND` | 404 | Compte introuvable |
| `ACCOUNT_FROZEN` | 400 | Compte gelé |
| `ACCOUNT_CLOSED` | 400 | Compte fermé |
| `ACCOUNT_BALANCE_NOT_ZERO` | 400 | Solde non nul (fermeture impossible) |
| `INSUFFICIENT_BALANCE` | 400 | Solde insuffisant |

### Transaction Service

| Code | HTTP Status | Description |
|------|-------------|-------------|
| `TRANSACTION_NOT_FOUND` | 404 | Transaction introuvable |
| `TRANSACTION_INVALID_AMOUNT` | 400 | Montant invalide |
| `TRANSACTION_ALREADY_PROCESSED` | 400 | Transaction déjà traitée |

### Card Service

| Code | HTTP Status | Description |
|------|-------------|-------------|
| `CARD_NOT_FOUND` | 404 | Carte introuvable |
| `CARD_ALREADY_BLOCKED` | 400 | Carte déjà bloquée |
| `CARD_IN_OPPOSITION` | 400 | Carte en opposition |

### Système

| Code | HTTP Status | Description |
|------|-------------|-------------|
| `IDLE_MODE_ACTIVE` | 503 | Mode Idle activé |
| `UNAUTHORIZED` | 401 | Non authentifié |
| `FORBIDDEN` | 403 | Accès refusé |
| `INTERNAL_ERROR` | 500 | Erreur serveur |

## Implémentation

Chaque service doit implémenter un `@RestControllerAdvice` global :

```java
@RestControllerAdvice
public class GlobalExceptionHandler {
    // Gestion centralisée des exceptions
}
```

### Exceptions personnalisées
- `ResourceNotFoundException` → 404
- `BusinessRuleException` → 400
- `IdleModeException` → 503
- `ValidationException` → 400
