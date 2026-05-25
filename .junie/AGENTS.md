# Guidelines Junie — Order Service

## Règles de délégation aux agents

- **Revue de code** : délègue systématiquement à l'agent `code-review-helper`.
- **Correction de bug** : délègue à l'agent `bugfix-investigator`.
- **Nouvelle fonctionnalité Spring Boot** : délègue à l'agent `spring-feature-impl`.

## Tech Stack

- **Java 21**
- **Spring Boot 4.0.6** (Web MVC, Data JPA)
- **H2** — base de données en mémoire (runtime)
- **SpringDoc OpenAPI 3.0.2** — documentation API (Swagger UI)
- **Maven** (wrapper inclus : `mvnw`)

## Structure du projet

```
src/
├── main/
│   ├── java/com/ps/orderservice/   # Code source principal
│   │   └── OrderServiceApplication.java
│   └── resources/
│       └── application.properties
└── test/
    └── java/com/ps/orderservice/   # Tests
        └── OrderServiceApplicationTests.java
```

## Commandes utiles

### Compiler le projet

```bash
./mvnw clean compile
```

### Lancer les tests

```bash
./mvnw test
```

### Lancer l'application

```bash
./mvnw spring-boot:run
```

L'application démarre par défaut sur `http://localhost:8080`.
La console H2 est accessible et la documentation Swagger UI est disponible via `/swagger-ui.html`.
