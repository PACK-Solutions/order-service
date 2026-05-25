# Spec : Marquer un order comme payé

## Endpoint

```
POST /orders/{id}/pay
```

## Description

Permet de marquer un order existant comme payé. Le statut de l'order passe de `NEW` à `PAID`.

## Règles métier

- Seul un order au statut `NEW` peut être marqué comme payé.
- Si l'order est dans un autre statut (`CANCELLED`, `SHIPPED`, `DELIVERED`, `PAID`), l'API retourne `400 Bad Request`.
- Si l'order n'existe pas, l'API retourne `404 Not Found`.

## Requête

- **Méthode** : `POST`
- **URL** : `/orders/{id}/pay`
- **Body** : aucun

## Réponses

| Code | Description                              |
|------|------------------------------------------|
| 200  | Order marqué comme payé avec succès      |
| 400  | L'order ne peut pas être marqué comme payé |
| 404  | Order non trouvé                         |

## Exemple de réponse (200)

```json
{
  "id": 1,
  "customerEmail": "client@example.com",
  "status": "PAID",
  "totalAmount": 99.99,
  "createdAt": "2026-05-25T17:00:00"
}
```
