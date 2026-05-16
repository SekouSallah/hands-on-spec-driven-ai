# Comportement Système de l'IA — AI Core Banking

## Mode Opératoire

L'IA fonctionne en mode **Spec-Driven Development** :

1. **Lire** les spécifications avant toute action
2. **Vérifier** la cohérence avec l'existant
3. **Générer** le code dans l'ordre prescrit
4. **Valider** que le code respecte les conventions
5. **Documenter** les choix techniques

## Réactions aux Situations

### Situation : Demande de code pour un service non encore créé
→ L'IA doit d'abord vérifier les dépendances (generation-order.md) et signaler si un service prérequis n'existe pas encore.

### Situation : Incohérence détectée dans les specs
→ L'IA signale l'incohérence, propose une résolution, mais ne corrige PAS sans validation humaine.

### Situation : Demande contraire aux conventions
→ L'IA signale le conflit avec la convention applicable, explique pourquoi la convention existe, puis exécute si l'humain confirme.

### Situation : Demande floue ou incomplète
→ L'IA demande des clarifications en citant les specs pertinentes qui pourraient aider à préciser la demande.

### Situation : Bug reporté
→ L'IA analyse le code en se référant aux specs, identifie la cause probable, et propose un fix avec test de non-régression.

## Interdictions Absolues

1. ❌ Ne JAMAIS modifier `ai-context/meta/` sans validation explicite
2. ❌ Ne JAMAIS supprimer de migrations Flyway
3. ❌ Ne JAMAIS changer la stack technique
4. ❌ Ne JAMAIS utiliser de YAML pour la configuration Spring
5. ❌ Ne JAMAIS créer de dossier `backend/` global
6. ❌ Ne JAMAIS exposer d'entités JPA directement en API
7. ❌ Ne JAMAIS ignorer le mode Idle dans un nouveau service
8. ❌ Ne JAMAIS générer de code sans avoir lu les specs pertinentes

## Logging de l'IA

Pour chaque génération de code, l'IA devrait mentionner :
- Les specs consultées
- Les règles appliquées
- Les décisions prises
- Les alternatives considérées
