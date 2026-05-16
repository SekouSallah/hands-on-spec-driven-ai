# TICKET-001
Type: FEATURE
Titre: Créer le microservice Customer Service
Description: Implémenter le microservice de gestion des clients avec toutes les opérations CRUD, KYC, et intégration du mode Idle.
Priorité: HIGH
Statut: TODO
Service: customer
Assigné: Non assigné
Créé: 2024-01-15T10:00:00
Modifié: 2024-01-15T10:00:00

## Critères d'Acceptation
- [ ] Endpoint POST /api/customers (création)
- [ ] Endpoint PUT /api/customers/{id} (modification)
- [ ] Endpoint PATCH /api/customers/{id}/deactivate (désactivation)
- [ ] Endpoint GET /api/customers/{id} (consultation)
- [ ] Endpoint GET /api/customers (recherche paginée)
- [ ] Migration Flyway V1
- [ ] Validation des données (email unique, champs obligatoires)
- [ ] Mode Idle : bloque les écritures quand activé
- [ ] Tests unitaires (>70% couverture)
- [ ] Documentation OpenAPI

## Notes Techniques
- Utiliser MapStruct pour le mapping Entity ↔ DTO
- Status initial : ACTIVE
- KYC initial : PENDING
- Email non modifiable après création
