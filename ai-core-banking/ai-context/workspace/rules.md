# Règles de Travail — AI Core Banking

## Règles Fondamentales

1. **Spec-Driven** : Tout code doit être tracé à une spécification dans `ai-context/specs/`
2. **Convention over Configuration** : Suivre les conventions établies, pas les préférences personnelles
3. **DRY** : Ne pas dupliquer de code. Extraire les patterns communs
4. **KISS** : Garder le code simple. Pas de sur-ingénierie
5. **SOLID** : Respecter les principes SOLID dans l'architecture

## Qualité de Code

- Code propre et lisible
- Noms explicites (pas d'abréviations cryptiques)
- Commentaires pour le "pourquoi", pas le "quoi"
- Pas de code mort ou commenté
- Formatage cohérent (utiliser le formatter de l'IDE)

## Architecture

- Séparation stricte des responsabilités
- Pas de dépendance circulaire entre services
- API first : concevoir l'API avant l'implémentation
- Tests obligatoires pour tout code métier

## Revue de Code

### Checklist de Review
- [ ] Le code compile sans warning
- [ ] Les tests passent
- [ ] Les conventions de nommage sont respectées
- [ ] La documentation est à jour
- [ ] Pas de code dupliqué
- [ ] La gestion des erreurs est complète
- [ ] Les DTOs sont utilisés (pas d'entités en API)
- [ ] Le mode Idle est géré si nécessaire
