# Conventions de code — Java 21

## Nommage

- **Classes** : PascalCase (`OrderService`, `OrderController`)
- **Méthodes et variables** : camelCase (`findOrderById`, `totalAmount`)
- **Constantes** : UPPER_SNAKE_CASE (`MAX_RETRY_COUNT`, `DEFAULT_STATUS`)
- **Packages** : tout en minuscules, séparés par des points (`com.ps.orderservice`)
- Les noms doivent être explicites et refléter l'intention : éviter les abréviations obscures (`s`, `tmp`, `x`)
- Les méthodes booléennes commencent par `is`, `has`, `can` (`isValid()`, `hasItems()`)

## Nullabilité

- Éviter de retourner `null` : préférer `Optional<T>` pour les valeurs potentiellement absentes
- Ne jamais passer `null` en paramètre de méthode publique
- Utiliser `Objects.requireNonNull()` pour valider les paramètres critiques dans les constructeurs et méthodes publiques
- Annoter avec `@Nullable` / `@NonNull` (Jakarta) quand la nullabilité n'est pas évidente

## Gestion des erreurs

- Ne jamais avaler une exception silencieusement (catch vide)
- Utiliser des exceptions métier spécifiques (`OrderNotFoundException`, `InvalidOrderStateException`) plutôt que des exceptions génériques
- Laisser remonter les exceptions non récupérables
- Documenter les exceptions dans la Javadoc (`@throws`)
- Centraliser la gestion des erreurs HTTP via `@ControllerAdvice`

## Logging

- Utiliser SLF4J (`LoggerFactory.getLogger(MyClass.class)`)
- Niveaux de log appropriés :
  - `ERROR` : erreurs nécessitant une intervention
  - `WARN` : situations anormales mais récupérables
  - `INFO` : événements métier importants (démarrage, actions clés)
  - `DEBUG` : détails utiles au développement
- Utiliser le placeholder `{}` au lieu de la concaténation de chaînes : `log.info("Order {} created", orderId)`
- Ne jamais logger d'informations sensibles (mots de passe, tokens)

## Erreurs classiques Java

- Ne jamais comparer des objets avec `==` : utiliser `equals()` (ex : `"PENDING".equals(status)` et non `status == "PENDING"`)
- Comparer les `String` avec `equals()` ou `Objects.equals()`, jamais avec `==`
- Attention à l'autoboxing : `Integer a = 128; Integer b = 128; a == b` retourne `false` — utiliser `equals()`
- Ne pas confondre `=` (affectation) et `==` (comparaison) dans les conditions
- Toujours utiliser `@Override` lors de la redéfinition de `equals()`, `hashCode()` et `toString()`
- Redéfinir `hashCode()` systématiquement quand on redéfinit `equals()`
- Éviter `catch (Exception e)` ou `catch (Throwable t)` trop larges : attraper l'exception la plus spécifique
- Ne pas ignorer le retour de méthodes comme `String.replace()` ou `String.trim()` (les `String` sont immuables)
- Fermer les ressources avec try-with-resources (`try (var stream = ...)`) plutôt que manuellement
- Éviter les `ConcurrentModificationException` : ne pas modifier une collection pendant son itération directe
- Attention aux `NullPointerException` : vérifier la nullité avant d'appeler une méthode sur un objet potentiellement `null`
- Ne pas utiliser `new BigDecimal(0.1)` (imprécis) : préférer `BigDecimal.valueOf(0.1)` ou `new BigDecimal("0.1")`

## Style général

- Préférer les records Java pour les DTOs et objets immuables
- Utiliser les `switch` expressions (Java 21) quand approprié
- Préférer `var` pour les variables locales quand le type est évident
- Limiter les méthodes à une responsabilité unique et une taille raisonnable (< 30 lignes)
- Pas de nombres magiques : extraire en constantes nommées
