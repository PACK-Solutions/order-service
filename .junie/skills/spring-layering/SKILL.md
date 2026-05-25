---
name: spring-layering
description: Bonnes pratiques d’injection de dépendances, séparation des couches (controller/service/repository), ControllerAdvice et JPA en Spring Boot.
---

# Architecture en couches — Spring Boot

## Injection de dépendances

- Toujours utiliser l'**injection par constructeur** (jamais `@Autowired` sur un champ)
- Déclarer les dépendances `final` pour garantir l'immutabilité
- Si une seule dépendance au constructeur, `@Autowired` est implicite (pas besoin de l'annoter)
- Utiliser `@RequiredArgsConstructor` (Lombok) ou écrire le constructeur explicitement

## Couche Controller (`@RestController`)

- Responsabilité : recevoir les requêtes HTTP, valider les entrées, déléguer au service, retourner la réponse
- **Aucune logique métier** dans le controller
- **Aucun accès direct** au repository depuis le controller
- Utiliser les annotations appropriées : `@GetMapping`, `@PostMapping`, `@PutMapping`, `@DeleteMapping`
- Retourner `ResponseEntity<T>` pour contrôler le code HTTP de retour
- Valider les entrées avec `@Valid` et les annotations Jakarta Validation (`@NotNull`, `@NotBlank`, `@Email`, etc.)

## Couche Service (`@Service`)

- Responsabilité : logique métier, orchestration, règles de gestion
- Ne jamais retourner de `ResponseEntity` depuis un service — retourner des objets métier ou lever des exceptions
- Garder les méthodes courtes et focalisées sur une seule responsabilité
- Utiliser `@Transactional` pour les opérations d'écriture

## Couche Repository (`@Repository` / Spring Data JPA)

- Utiliser les interfaces Spring Data JPA (`JpaRepository`, `CrudRepository`)
- Nommer les méthodes de requête selon les conventions Spring Data (`findByStatus`, `findByCustomerEmail`)
- Utiliser `@Query` uniquement pour les requêtes complexes non exprimables par convention de nommage
- Les repositories ne sont accédés que par la couche service

## Gestion des erreurs (`@ControllerAdvice`)

- Centraliser la gestion des exceptions dans une classe `@ControllerAdvice` / `@RestControllerAdvice`
- Mapper chaque exception métier à un code HTTP approprié :
  - `404 Not Found` pour les ressources absentes
  - `400 Bad Request` pour les entrées invalides
  - `409 Conflict` pour les violations d'état métier
  - `500 Internal Server Error` pour les erreurs inattendues
- Retourner un format d'erreur cohérent (ex. : `{ "error": "...", "message": "..." }`)

## Entités JPA

- Annoter avec `@Entity` et `@Table` si le nom de table diffère
- Utiliser `@Id` et `@GeneratedValue` pour les clés primaires
- Préférer les types `enum` pour les statuts plutôt que des chaînes de caractères dupliquées
- Éviter d'exposer directement les entités JPA dans les réponses API : utiliser des DTOs
- Initialiser les champs temporels (`createdAt`) via `@PrePersist` ou des valeurs par défaut
