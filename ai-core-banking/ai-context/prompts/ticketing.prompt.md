# Prompt Ticketing — AI Core Banking

## Contexte

Tu gères le système de tickets du projet AI Core Banking. Les tickets sont stockés en Markdown dans `ai-context/tickets/`.

## Instructions

### Création de Ticket

Format obligatoire :

```markdown
# TICKET-{NNN}
Type: {FEATURE | BUG | TASK}
Titre: {titre descriptif}
Description: {description détaillée}
Priorité: {LOW | MEDIUM | HIGH | CRITICAL}
Statut: {TODO | IN_PROGRESS | DONE | ARCHIVED}
Service: {customer | account | card | transaction | fraud | notification | backoffice | mobile | infra}
Assigné: {nom ou "Non assigné"}
Créé: {date ISO}
Modifié: {date ISO}

## Critères d'Acceptation
- [ ] Critère 1
- [ ] Critère 2

## Notes Techniques
{notes optionnelles}
```

### Gestion des Tickets

| Action | Déplacement |
|--------|-------------|
| Nouveau ticket | → `backlog/` |
| Début de travail | → `in_progress/` |
| Ticket terminé | → `active/` (résolu) |
| Archivage | → `archived/` |

### Nommage des fichiers
- Format : `TICKET-{NNN}.md`
- Numérotation séquentielle
- Pas de gaps dans la numérotation
