# Guidelines Junie — Order Service

## Règles de délégation aux agents

Quand la demande de l'utilisateur correspond à l'un des cas ci-dessous, **délègue systématiquement** à l'agent indiqué.

### Agent `code-review-helper` — Revue de code

Déclencher quand la demande contient des termes ou intentions liés à :
- **Revue / review** : « revue de code », « code review », « review », « relis ce code », « vérifie ce code », « analyse ce code », « audite ce code »
- **Qualité / conventions** : « qualité du code », « conventions Java », « bonnes pratiques », « best practices », « clean code », « code propre », « style de code »
- **Architecture Spring** : « architecture Spring », « séparation des couches », « injection de dépendances », « ControllerAdvice », « couche controller/service/repository »
- **Vérification ciblée** : « vérifie le nommage », « vérifie la gestion des erreurs », « vérifie le logging », « vérifie les imports », « vérifie la nullabilité »
- **Technologies mentionnées avec intention de revue** : « Java 21 », « Spring Boot », « Spring MVC », « JPA », « Spring Data » combinés avec une demande de vérification ou d'analyse

Skills associés : `code-conventions`, `spring-layering`

### Agent `bugfix-investigator` — Correction de bug

Déclencher quand la demande contient des termes ou intentions liés à :
- **Bug / erreur** : « bug », « erreur », « problème », « fix », « corriger », « corriger un bug », « résoudre », « ça ne marche pas », « ça plante », « crash », « régression »
- **Investigation** : « investiguer », « diagnostiquer », « trouver la cause », « cause racine », « root cause », « debug », « déboguer », « pourquoi ça échoue »
- **Exceptions** : « exception », « NullPointerException », « 500 Internal Server Error », « stack trace », « erreur HTTP », « 404 inattendu »
- **Test de régression** : « test de régression », « reproduire le bug », « test qui échoue »

Skills associés : `testing-and-tdd`, `code-conventions`

### Agent `spring-feature-impl` — Nouvelle fonctionnalité

Déclencher quand la demande contient des termes ou intentions liés à :
- **Nouvelle fonctionnalité** : « nouvelle fonctionnalité », « new feature », « ajouter », « implémenter », « créer un endpoint », « créer une API », « développer »
- **CRUD / endpoints** : « ajouter un endpoint », « créer un service », « ajouter une entité », « nouveau controller », « nouveau repository », « CRUD »
- **Spécification** : « implémenter la spec », « implémenter la spécification », « selon le cahier des charges », « d'après le fichier spec »
- **TDD** : « en TDD », « test-driven », « red-green-refactor » combinés avec une demande d'implémentation

Skills associés : `spring-layering`, `testing-and-tdd`

### Règle de priorité

Si la demande est ambiguë ou couvre plusieurs agents :
1. **Bug explicite** → `bugfix-investigator`
2. **Revue / analyse sans modification** → `code-review-helper`
3. **Implémentation / ajout de code** → `spring-feature-impl`

### Règles de comportement des agents

- **Pas de boucle** : chaque agent termine sa mission en soumettant son rapport/résultat. Il ne demande PAS à l'utilisateur s'il souhaite des actions supplémentaires (ex. : « voulez-vous que je corrige ? »).
- **Pas de chevauchement** : chaque agent se limite à sa responsabilité. L'agent de revue ne corrige pas le code. L'agent de bugfix ne fait pas de revue globale. L'agent de feature ne fait pas de revue.
- **Délégation aux skills** : les agents ne redéfinissent pas les règles de conventions ou d'architecture dans leurs instructions. Ils référencent les skills (`code-conventions`, `spring-layering`, `testing-and-tdd`).

## Tech Stack

- **Java 21**
- **Spring Boot 4.0.6** (Web MVNoC, Data JPA)
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
