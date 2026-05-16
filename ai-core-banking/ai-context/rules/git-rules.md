# Règles Git — AI Core Banking

## Branches

### Structure
- `main` : branche de production, toujours stable
- `develop` : branche de développement principal
- `feature/{ticket-id}-{description}` : nouvelles fonctionnalités
- `bugfix/{ticket-id}-{description}` : corrections de bugs
- `hotfix/{ticket-id}-{description}` : corrections urgentes

### Exemples
```text
feature/TICKET-001-customer-service
bugfix/TICKET-015-fix-balance-calculation
hotfix/TICKET-020-security-patch
```

## Commits

### Format
```text
{type}({scope}): {description}

{body optionnel}

{footer optionnel}
```

### Types
- `feat` : nouvelle fonctionnalité
- `fix` : correction de bug
- `refactor` : refactoring sans changement fonctionnel
- `docs` : documentation
- `test` : ajout/modification de tests
- `chore` : tâches techniques (dependencies, CI, etc.)
- `style` : formatage, pas de changement fonctionnel

### Scopes
- `customer` : customer-service
- `account` : account-service
- `card` : card-service
- `transaction` : transaction-service
- `fraud` : fraud-service
- `notification` : notification-service
- `backoffice` : frontend Angular
- `mobile` : Flutter app
- `infra` : infrastructure
- `ai-context` : documentation IA

### Exemples
```text
feat(customer): add customer creation endpoint
fix(transaction): correct balance calculation on transfer
docs(ai-context): update domain model with card entity
refactor(account): extract account number generation to utility
test(fraud): add unit tests for scoring engine
```

## Pull Requests

- Titre = commit message principal
- Description avec : contexte, changements, tests effectués
- Lien vers le ticket associé
- Review obligatoire avant merge

## Interdictions

- ❌ Pas de commit direct sur `main`
- ❌ Pas de secrets dans le code (utiliser les variables d'environnement)
- ❌ Pas de fichiers générés (build, node_modules, target)
- ❌ Pas de merge sans review
