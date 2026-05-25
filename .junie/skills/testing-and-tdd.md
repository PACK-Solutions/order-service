# Tests et TDD — JUnit 5, Mockito

## Cycle Red-Green-Refactor

1. **Red** : écrire un test qui échoue, décrivant le comportement attendu
2. **Green** : écrire le code minimal pour faire passer le test
3. **Refactor** : améliorer le code sans changer le comportement, en gardant les tests verts
- Commiter après chaque cycle complet
- Ne jamais écrire de code de production sans test correspondant

## JUnit 5

- Utiliser `@Test` de `org.junit.jupiter.api`
- Nommer les tests de manière descriptive : `shouldReturnOrderWhenIdExists()`, `shouldThrowExceptionWhenOrderNotFound()`
- Utiliser `@DisplayName` pour les descriptions lisibles si le nom de méthode ne suffit pas
- Structurer chaque test en trois blocs : **Arrange / Act / Assert** (ou Given / When / Then)
- Un seul concept testé par méthode de test
- Utiliser `@Nested` pour regrouper les tests par contexte ou fonctionnalité

## Assertions

- Préférer les assertions AssertJ pour leur lisibilité : `assertThat(order.getStatus()).isEqualTo("NEW")`
- Vérifier les exceptions avec `assertThatThrownBy(() -> ...).isInstanceOf(...)`
- Toujours vérifier le résultat attendu, pas seulement l'absence d'erreur

## Mockito

- Utiliser `@ExtendWith(MockitoExtension.class)` avec JUnit 5
- `@Mock` pour les dépendances simulées, `@InjectMocks` pour la classe sous test
- Utiliser `when(...).thenReturn(...)` pour configurer les comportements
- Vérifier les interactions avec `verify(mock).method(...)` quand nécessaire
- Ne pas abuser des mocks : mocker uniquement les dépendances externes, pas la classe testée
- Préférer les tests d'intégration avec `@SpringBootTest` pour valider le câblage complet

## Tests d'intégration Spring

- `@SpringBootTest` pour charger le contexte complet
- `@AutoConfigureMockMvc` + `MockMvc` pour tester les endpoints REST sans démarrer le serveur
- `@Transactional` sur les tests pour rollback automatique après chaque test
- Utiliser une base H2 en mémoire pour les tests de persistance

## Cas limites et couverture

- Toujours tester les cas nominaux ET les cas d'erreur
- Cas limites à couvrir systématiquement :
  - Entrées `null` ou vides
  - Ressource inexistante (404)
  - État invalide (ex. : annuler une commande déjà expédiée)
  - Valeurs aux bornes (montant négatif, zéro, très grand)
  - Doublons et idempotence
- Écrire un test de régression pour chaque bug corrigé
- Viser une couverture significative de la logique métier, pas un pourcentage arbitraire
