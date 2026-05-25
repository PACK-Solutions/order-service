# Bug : Un order SHIPPED peut être annulé

## Description

Lorsqu'un order est au statut `SHIPPED`, l'endpoint `POST /orders/{id}/cancel` permet de l'annuler avec succès, alors que cela ne devrait pas être possible.

## Comportement actuel

Un order avec le statut `SHIPPED` peut être annulé via `POST /orders/{id}/cancel`. Le statut passe à `CANCELLED` et un code `200 OK` est retourné.

## Comportement attendu

Un order au statut `SHIPPED` ne doit **pas** pouvoir être annulé. L'API devrait retourner un code `400 Bad Request`, de la même manière qu'un order `DELIVERED` ou déjà `CANCELLED`.

## Localisation

- **Fichier** : `OrderService.java`
- **Méthode** : `doCancel(Long id)`
- **Cause** : Il manque une vérification du statut `SHIPPED` avant d'autoriser l'annulation.

## Étapes de reproduction

1. Créer un order via `POST /orders`
2. Modifier manuellement son statut à `SHIPPED` (via H2 console ou en ajoutant un endpoint dédié)
3. Appeler `POST /orders/{id}/cancel`
4. Observer que l'order passe à `CANCELLED` au lieu d'être rejeté
