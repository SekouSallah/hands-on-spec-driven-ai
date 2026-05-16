# Ordre de Génération — AI Core Banking

## Ordre Obligatoire

L'IA DOIT générer les fichiers dans cet ordre exact pour chaque microservice :

### Phase 1 : Fondation
1. `pom.xml` — Dépendances Maven
2. `application.properties` — Configuration
3. `Dockerfile` — Containerisation
4. `{Service}Application.java` — Point d'entrée Spring Boot

### Phase 2 : Modèle de Données
5. Enums — `enums/*.java`
6. Entity — `entity/{Entity}.java`
7. Flyway Migration — `db/migration/V1__{description}.sql`

### Phase 3 : Accès aux Données
8. Repository — `repository/{Entity}Repository.java`
9. DTOs — `dto/request/*.java` + `dto/response/*.java`
10. Mapper — `mapper/{Entity}Mapper.java`

### Phase 4 : Logique Métier
11. Service Interface — `service/{Entity}Service.java`
12. Service Impl — `service/impl/{Entity}ServiceImpl.java`

### Phase 5 : API
13. Controller — `controller/{Entity}Controller.java`
14. Exception Handler — `exception/GlobalExceptionHandler.java`
15. Custom Exceptions — `exception/{Name}Exception.java`

### Phase 6 : Configuration
16. Security Config — `config/SecurityConfig.java`
17. CORS Config — `config/CorsConfig.java`
18. Idle Mode — `config/IdleModeInterceptor.java`

### Phase 7 : Tests
19. Service Tests — `*ServiceTest.java`
20. Controller Tests — `*ControllerTest.java`
21. Integration Tests — `*IT.java`

## Ordre des Services

Les microservices doivent être développés dans cet ordre :

1. **customer-service** — Fondation (pas de dépendance externe)
2. **account-service** — Dépend de customer-service
3. **transaction-service** — Dépend de account-service
4. **card-service** — Dépend de account-service
5. **fraud-service** — Dépend de transaction-service
6. **notification-service** — Dépend de tous les autres

## Règle Critique

⚠️ L'IA ne doit JAMAIS sauter une étape dans l'ordre de génération. Chaque fichier de la phase N doit être complet avant de passer à la phase N+1.
