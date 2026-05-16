# AI Guidelines — AI Core Banking

## Directives Globales

Ce document définit les règles globales que l'IA doit respecter lors de toute interaction avec le projet AI Core Banking.

## Règle 1 : Hiérarchie de Priorité

L'IA doit toujours respecter cet ordre de priorité :

1. **meta/** — Règles de comportement de l'IA (ce fichier)
2. **specs/** — Source de vérité métier et technique
3. **rules/** — Conventions de développement
4. **workspace/** — Règles de travail
5. **prompts/** — Templates de génération
6. **tickets/** — Évolutions en cours

En cas de conflit, le niveau supérieur l'emporte.

## Règle 2 : Cohérence Absolue

- L'IA ne doit JAMAIS produire de code incohérent avec les specs existantes
- Si une incohérence est détectée, l'IA DOIT signaler le conflit avant de continuer
- L'IA doit vérifier la cohérence avec les services existants avant de créer un nouveau service

## Règle 3 : Complétude

- Chaque fichier généré doit être complet et fonctionnel
- Pas de "TODO" ou "à implémenter plus tard" sans justification
- Les imports doivent être complets
- Les tests doivent être inclus

## Règle 4 : Traçabilité

- Chaque génération de code doit être liée à un ticket
- Les changements architecturaux doivent être documentés
- L'historique des décisions techniques doit être maintenu

## Règle 5 : Sécurité

- Ne jamais générer de code avec des failles de sécurité connues
- Toujours valider les entrées utilisateur
- Toujours utiliser des requêtes paramétrées (pas de concaténation SQL)
- Ne jamais logger de données sensibles (PAN, CVV, mots de passe)

## Règle 6 : Performance

- Pagination obligatoire sur les listes
- Lazy loading pour les relations JPA
- Index sur les colonnes fréquemment recherchées
- Pas de N+1 queries
