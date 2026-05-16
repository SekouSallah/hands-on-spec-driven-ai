# Règles Angular — AI Core Banking

## Stack

- Angular 17+ (standalone components)
- Angular Material
- TypeScript strict mode
- RxJS
- SCSS pour les styles

## Structure du Projet

```text
e-banking-backoffice-frontend/
├── src/
│   ├── app/
│   │   ├── core/               → Services singleton, guards, interceptors
│   │   │   ├── services/
│   │   │   ├── guards/
│   │   │   ├── interceptors/
│   │   │   └── models/
│   │   ├── shared/             → Composants réutilisables, pipes, directives
│   │   │   ├── components/
│   │   │   ├── pipes/
│   │   │   └── directives/
│   │   ├── features/           → Modules fonctionnels
│   │   │   ├── dashboard/
│   │   │   ├── customers/
│   │   │   ├── accounts/
│   │   │   ├── cards/
│   │   │   ├── transactions/
│   │   │   ├── fraud/
│   │   │   └── settings/
│   │   ├── app.component.ts
│   │   ├── app.config.ts
│   │   └── app.routes.ts
│   ├── assets/
│   ├── environments/
│   └── styles/
│       ├── _variables.scss
│       ├── _mixins.scss
│       └── styles.scss
├── angular.json
├── package.json
└── tsconfig.json
```

## Conventions

### Naming
- Composants : kebab-case (`customer-list.component.ts`)
- Services : camelCase (`customer.service.ts`)
- Models/Interfaces : PascalCase (`Customer`, `AccountDTO`)
- Fichiers : kebab-case avec suffix (`customer-list.component.ts`, `.service.ts`, `.guard.ts`)

### Composants
- Standalone components (pas de NgModules)
- Un composant = un fichier `.ts`, `.html`, `.scss`
- Utiliser `ChangeDetectionStrategy.OnPush` par défaut
- Signals pour la gestion d'état (Angular 17+)

### Services
- `@Injectable({ providedIn: 'root' })` pour les services singleton
- HttpClient pour les appels API
- Retourner des `Observable<T>`

### Routing
- Lazy loading par feature
- Guards pour la protection des routes
- Route data pour les titres de page

### API Communication
- Intercepteur HTTP pour ajouter le JWT
- Intercepteur pour la gestion globale des erreurs
- Environment files pour les URLs de base

## Angular Material

- Utiliser les composants Material pour tous les éléments UI
- Thème personnalisé avec palette bancaire
- Tables avec `MatTableDataSource`, pagination, tri
- Formulaires avec validation Material

## Interdictions

- ❌ Pas de NgModules (standalone uniquement)
- ❌ Pas de `any` en TypeScript
- ❌ Pas de logique métier dans les composants (déléguer aux services)
- ❌ Pas de CSS inline
- ❌ Pas de `subscribe` sans `unsubscribe` (utiliser `async` pipe ou `takeUntilDestroyed`)
