# Conventions de Nommage — AI Core Banking

## Java

| Élément | Convention | Exemple |
|---------|-----------|---------|
| Package | lowercase | `com.ebanking.customer` |
| Classe | PascalCase | `CustomerService` |
| Interface | PascalCase | `CustomerRepository` |
| Méthode | camelCase | `findByEmail()` |
| Variable | camelCase | `accountNumber` |
| Constante | UPPER_SNAKE | `MAX_AMOUNT` |
| Enum | PascalCase | `CustomerStatus` |
| Enum value | UPPER_SNAKE | `ACTIVE`, `IN_PROGRESS` |

## Base de Données

| Élément | Convention | Exemple |
|---------|-----------|---------|
| Table | snake_case, pluriel | `customers` |
| Colonne | snake_case | `first_name` |
| Clé primaire | `id` | `id` |
| Clé étrangère | `{table}_id` | `customer_id` |
| Index | `idx_{table}_{col}` | `idx_customers_email` |
| Unique | `uk_{table}_{col}` | `uk_customers_email` |
| Migration | `V{n}__{desc}.sql` | `V1__create_customers.sql` |

## Angular

| Élément | Convention | Exemple |
|---------|-----------|---------|
| Composant | kebab-case | `customer-list.component.ts` |
| Service | kebab-case | `customer.service.ts` |
| Guard | kebab-case | `auth.guard.ts` |
| Interface | PascalCase | `Customer` |
| Route | kebab-case | `/customers/detail/:id` |

## Flutter

| Élément | Convention | Exemple |
|---------|-----------|---------|
| Fichier | snake_case | `customer_screen.dart` |
| Classe | PascalCase | `CustomerScreen` |
| Variable | camelCase | `customerName` |
| Constante | lowerCamelCase | `maxRetries` |

## API REST

| Élément | Convention | Exemple |
|---------|-----------|---------|
| Endpoint | kebab-case, pluriel | `/api/customers` |
| Path param | camelCase | `/api/customers/{customerId}` |
| Query param | camelCase | `?firstName=John` |
| Request body | camelCase JSON | `{ "firstName": "John" }` |
| Response | camelCase JSON | `{ "accountNumber": "..." }` |

## Git

| Élément | Convention | Exemple |
|---------|-----------|---------|
| Branche | kebab-case | `feature/TICKET-001-customer-service` |
| Commit | conventional | `feat(customer): add creation endpoint` |
| Tag | semver | `v1.0.0` |
