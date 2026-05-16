# Mode Idle — AI Core Banking

## Définition

Le mode Idle est un mécanisme global de la plateforme bancaire permettant de suspendre temporairement toutes les opérations d'écriture tout en maintenant les opérations de lecture.

## Cas d'Usage

- Maintenance planifiée
- Mise à jour de base de données
- Fin de journée bancaire
- Situation d'urgence

## Comportement

### Mode Idle ACTIVÉ

#### ❌ Opérations INTERDITES (retournent 503)
- Création de client
- Modification de client
- Création de compte
- Fermeture / gel de compte
- Transactions (virements, dépôts, retraits)
- Création / modification de carte
- Toute opération d'écriture

#### ✅ Opérations AUTORISÉES
- Consultation client
- Recherche clients
- Consultation solde
- Historique transactions
- Consultation cartes
- Reporting
- Dashboard

### Mode Idle DÉSACTIVÉ
- Toutes les opérations sont autorisées

## API

### Activer / Désactiver le mode Idle
- **Endpoint** : `PATCH /api/system/idle`
- **Body** : `{ "enabled": true/false }`
- **Rôle requis** : `ADMIN`
- **Réponse** : `{ "idleMode": true, "activatedAt": "...", "activatedBy": "..." }`

### Consulter le status du mode Idle
- **Endpoint** : `GET /api/system/idle`
- **Rôle requis** : aucun (public)
- **Réponse** : `{ "idleMode": false }`

## Implémentation Technique

Le mode Idle est implémenté via un **intercepteur HTTP** (`HandlerInterceptor`) qui :

1. Vérifie l'état du mode Idle (stocké en mémoire ou en base)
2. Si activé et que la requête est une écriture (POST, PUT, PATCH, DELETE) :
   - Retourne `503 Service Unavailable`
   - Message : "Le système est en mode maintenance. Seules les opérations de lecture sont autorisées."
3. Si désactivé ou requête en lecture (GET) : laisse passer

### Configuration

```properties
# application.properties
ebanking.idle.enabled=false
ebanking.idle.storage=memory
```
