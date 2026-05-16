# Règles d'Utilisation de l'IA — AI Core Banking

## Philosophie

L'IA est un **copilote de développement**. Elle assiste, ne décide pas.

## Rôles Autorisés de l'IA

### ✅ L'IA PEUT :
- Générer du code à partir de spécifications documentées
- Proposer des architectures et patterns
- Générer de la documentation technique
- Expliquer des choix techniques
- Aider au debugging et à l'analyse d'erreurs
- Suggérer des optimisations
- Générer des tests unitaires et d'intégration
- Refactorer du code existant selon les conventions

## Interdictions Strictes

### ❌ L'IA NE DOIT JAMAIS :
1. **Décider seule des règles métier** — Les règles bancaires sont définies dans les specs
2. **Modifier l'architecture sans validation humaine** — Toute décision architecturale nécessite un accord explicite
3. **Inventer des comportements bancaires** — Seuls les comportements documentés dans les specs sont autorisés
4. **Produire du code incohérent** — Le code doit respecter les conventions établies
5. **Ignorer les specs existantes** — Toujours consulter vision.md, architecture.md, domain_model.md avant de coder
6. **Supprimer du code sans explication** — Toute suppression doit être justifiée
7. **Changer de stack technique** — Java 21, Spring Boot 3, Maven, MySQL, application.properties sont non négociables

## Processus de Génération

### Ordre obligatoire avant toute génération de code :

1. Lire `meta/generation-order.md`
2. Consulter `specs/vision.md`
3. Consulter `specs/architecture.md`
4. Consulter `specs/domain_model.md`
5. Consulter les `rules/` pertinentes
6. Vérifier les `tickets/` actifs
7. Générer le code

### Validation
- Le code généré doit compiler sans erreur
- Les tests doivent passer
- Les conventions de nommage doivent être respectées
- La documentation doit être à jour

## Interaction Humain + IA

| Situation | Action |
|-----------|--------|
| L'IA propose un changement d'architecture | L'humain doit valider explicitement |
| L'IA détecte une incohérence dans les specs | L'IA signale, l'humain décide |
| L'IA ne comprend pas une exigence | L'IA demande des clarifications |
| L'humain demande un code non conforme aux specs | L'IA signale le conflit, puis exécute si l'humain confirme |
