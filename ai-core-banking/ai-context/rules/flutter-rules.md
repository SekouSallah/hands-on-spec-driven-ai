# Règles Flutter — AI Core Banking

## Stack

- Flutter 3.x
- Dart 3.x
- Provider ou Riverpod pour la gestion d'état
- Dio pour les appels HTTP
- go_router pour le routing

## Structure du Projet

```text
e-banking-mobile/
├── lib/
│   ├── core/
│   │   ├── constants/
│   │   ├── theme/
│   │   ├── utils/
│   │   └── network/
│   │       ├── api_client.dart
│   │       ├── api_endpoints.dart
│   │       └── interceptors/
│   ├── data/
│   │   ├── models/
│   │   ├── repositories/
│   │   └── datasources/
│   ├── domain/
│   │   ├── entities/
│   │   ├── repositories/
│   │   └── usecases/
│   ├── presentation/
│   │   ├── screens/
│   │   │   ├── login/
│   │   │   ├── home/
│   │   │   ├── accounts/
│   │   │   ├── transactions/
│   │   │   ├── cards/
│   │   │   └── notifications/
│   │   ├── widgets/
│   │   └── providers/
│   ├── app.dart
│   └── main.dart
├── test/
├── pubspec.yaml
└── analysis_options.yaml
```

## Conventions

### Naming
- Fichiers : snake_case (`customer_screen.dart`)
- Classes : PascalCase (`CustomerScreen`)
- Variables : camelCase (`customerName`)
- Constantes : lowerCamelCase ou UPPER_SNAKE_CASE

### Architecture
- Clean Architecture (data / domain / presentation)
- Séparation des responsabilités stricte
- Repository pattern pour l'accès aux données

### Widgets
- Préférer les `StatelessWidget` quand possible
- Extraire les widgets réutilisables dans `widgets/`
- Utiliser `const` constructors

### Thème
- Material Design 3
- Couleurs bancaires (bleu, vert, blanc)
- Dark mode supporté
- Typographie cohérente

### Navigation
- go_router pour le routing déclaratif
- Guards pour les routes protégées
- Deep linking supporté

## Interdictions

- ❌ Pas de logique métier dans les widgets
- ❌ Pas d'appels HTTP directs dans les widgets
- ❌ Pas de `setState` pour l'état global
- ❌ Pas de magic numbers / strings (utiliser des constantes)
