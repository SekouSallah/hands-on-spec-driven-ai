# Sécurité — AI Core Banking

## Authentification

### JWT (JSON Web Tokens)
- Tous les endpoints (sauf login et health) sont protégés par JWT
- Le token est transmis dans le header `Authorization: Bearer {token}`
- Durée de vie du token : 24 heures
- Algorithme : HS256

### Structure du Token JWT

```json
{
  "sub": "user@ebanking.com",
  "roles": ["ADMIN", "OPERATOR"],
  "iat": 1700000000,
  "exp": 1700086400
}
```

### Rôles

| Rôle | Permissions |
|------|------------|
| `ADMIN` | Accès total, gestion mode Idle, gestion utilisateurs |
| `OPERATOR` | CRUD clients, comptes, cartes, transactions |
| `VIEWER` | Lecture seule, reporting |

## Endpoints Publics (sans JWT)

- `POST /api/auth/login`
- `GET /actuator/health`
- Swagger UI (`/swagger-ui.html`)

## CORS

Configuration CORS permissive pour le développement :
- Origins : `http://localhost:4200` (Angular), `http://localhost:*`
- Methods : GET, POST, PUT, PATCH, DELETE, OPTIONS
- Headers : Authorization, Content-Type

## Bonnes Pratiques

1. **Pas de données sensibles dans les logs** : PAN, CVV, mots de passe ne doivent jamais apparaître dans les logs
2. **PAN masqué** : Seuls les 4 derniers chiffres sont affichés (`**** **** **** 1234`)
3. **CVV hashé** : Le CVV est stocké hashé en base de données
4. **Mots de passe** : BCrypt avec salt
5. **HTTPS** : Recommandé en production (pas obligatoire pour le workshop)
