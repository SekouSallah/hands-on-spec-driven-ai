# Workflow de Développement — AI Core Banking

## Cycle de Développement

```text
1. Consulter le ticket (backlog/)
2. Lire les specs pertinentes (ai-context/specs/)
3. Lire les règles applicables (ai-context/rules/)
4. Créer la branche (feature/TICKET-NNN-description)
5. Implémenter (avec assistance IA)
6. Tester (unitaire + intégration)
7. Review de code
8. Merge dans develop
9. Déplacer le ticket (in_progress/ → active/)
```

## Ordre de Développement d'un Service

1. **Entity** — Définir le modèle de données
2. **Flyway Migration** — Créer la table en base
3. **Repository** — Accès aux données
4. **DTO** — Objets de transfert
5. **Mapper** — Conversion Entity ↔ DTO
6. **Service** — Logique métier
7. **Controller** — Endpoints REST
8. **Exception Handler** — Gestion des erreurs
9. **Tests** — Unitaires et intégration
10. **Documentation** — OpenAPI / Swagger

## Environnements

| Environnement | Base de données | Port |
|---------------|----------------|------|
| Local (dev) | MySQL Docker | 3306 |
| Test | H2 in-memory | — |

## Outils de Développement

| Outil | Usage |
|-------|-------|
| Maven | Build & dépendances |
| Docker Compose | Infrastructure locale |
| Swagger UI | Test des APIs |
| Mailhog | Test des emails |
| Postman / Insomnia | Tests API manuels |
