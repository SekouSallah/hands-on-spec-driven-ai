# Règles Backend — AI Core Banking

## Stack Obligatoire

- Java 21
- Spring Boot 3.x
- Maven (PAS Gradle)
- `application.properties` (⚠️ STRICTEMENT, pas de YAML)
- JPA / Hibernate
- Flyway (migrations SQL)
- MySQL 8

## Structure des Packages

```text
com.ebanking.{servicename}
├── config/           → Configuration Spring (Security, CORS, etc.)
├── controller/       → REST Controllers
├── dto/              → Data Transfer Objects
│   ├── request/      → Request DTOs
│   └── response/     → Response DTOs
├── entity/           → JPA Entities
├── enums/            → Enumerations
├── exception/        → Custom Exceptions + GlobalExceptionHandler
├── mapper/           → MapStruct Mappers
├── repository/       → Spring Data JPA Repositories
└── service/
    └── impl/         → Service Implementations
```

## Conventions de Code

### Naming
- Classes : PascalCase (`CustomerController`, `AccountService`)
- Méthodes : camelCase (`createCustomer`, `findById`)
- Variables : camelCase (`customerId`, `accountNumber`)
- Constantes : UPPER_SNAKE_CASE (`MAX_TRANSACTION_AMOUNT`)
- Packages : lowercase (`com.ebanking.customer`)
- Tables SQL : snake_case (`fraud_alerts`)

### Controllers
- Annotation `@RestController`
- Préfixe URL : `/api/{resource}`
- Utiliser `@RequestMapping` au niveau classe
- Retourner `ResponseEntity<T>` toujours
- Documenter avec `@Operation` (OpenAPI)

```java
@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/{id}")
    @Operation(summary = "Get customer by ID")
    public ResponseEntity<CustomerDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(customerService.findById(id));
    }
}
```

### Services
- Interface + Implémentation (`CustomerService` + `CustomerServiceImpl`)
- Annotation `@Service` sur l'implémentation
- `@Transactional` sur les méthodes d'écriture
- Injection par constructeur (via `@RequiredArgsConstructor`)

### Repositories
- Étendre `JpaRepository<Entity, Long>`
- Utiliser les query methods de Spring Data
- Requêtes complexes avec `@Query`

### Entities
- Annotation `@Entity`
- `@Table(name = "...")` avec nom en snake_case
- `@Id` + `@GeneratedValue(strategy = GenerationType.IDENTITY)`
- Audit fields : `createdAt`, `updatedAt` avec `@CreationTimestamp` / `@UpdateTimestamp`

### DTOs
- Records Java ou classes avec Lombok
- Validation avec `@Valid`, `@NotNull`, `@NotBlank`, `@Email`, etc.
- Séparation Request / Response DTOs

### Mappers
- MapStruct obligatoire
- Interface annotée `@Mapper(componentModel = "spring")`

### Exceptions
- `GlobalExceptionHandler` avec `@RestControllerAdvice`
- Exceptions métier personnalisées
- Format de réponse d'erreur standardisé

## Flyway

- Répertoire : `src/main/resources/db/migration/`
- Nommage : `V{version}__{description}.sql` (ex: `V1__create_customer_table.sql`)
- Une migration par changement de schéma
- JAMAIS modifier une migration existante

## Tests

- Tests unitaires : JUnit 5 + Mockito
- Tests d'intégration : `@SpringBootTest` + `@AutoConfigureMockMvc`
- Couverture minimum : 70%
- Nommage : `{Classe}Test` pour unitaires, `{Classe}IT` pour intégration

## Interdictions

- ❌ Pas de YAML (application.yml)
- ❌ Pas de Gradle
- ❌ Pas de Kotlin
- ❌ Pas de code métier dans les controllers
- ❌ Pas d'entité exposée directement en API (toujours DTO)
- ❌ Pas de `@Autowired` sur les champs (injection constructeur uniquement)
