# PasswordCracker v1

**Dépôt GitHub :** [https://github.com/AliouLoum/PasswordCracker](https://github.com/AliouLoum/PasswordCracker) — visibilité publique

Outil en ligne de commande permettant de retrouver un mot de passe à partir de son hash MD5, en utilisant le patron de conception **Simple Factory**.

## 1. Introduction

PasswordCracker est un outil développé en Java dans un cadre pédagogique de sécurité informatique. Il permet, à partir d'un hash MD5, de retrouver le mot de passe en clair en testant deux stratégies : la recherche par dictionnaire et la recherche par force brute.

## 2. Présentation du problème

Dans une base de données, les mots de passe ne sont jamais stockés en clair : ils sont hachés via une fonction cryptographique comme MD5. Lors d'un audit de sécurité, il est utile de pouvoir tester la robustesse des mots de passe en tentant de retrouver leur valeur d'origine à partir de leur empreinte. PasswordCracker automatise cette démarche via deux approches complémentaires.

## 3. Architecture

Le projet est organisé en 4 packages, correspondant chacun à une responsabilité claire :

| Package | Rôle |
|---|---|
| `com.passwordcracker.core` | Interface `HashCracker` + utilitaire `MD5Utils` |
| `com.passwordcracker.strategy` | Stratégies concrètes `DictionaryHashCracker` et `BruteForceHashCracker` |
| `com.passwordcracker.factory` | Fabrique `HashCrackerFactory` |
| `com.passwordcracker.cli` | Point d'entrée `Main` |

Chaque stratégie implémente l'interface commune `HashCracker`, ce qui permet au reste de l'application de manipuler n'importe quelle stratégie de façon polymorphe, sans connaître son implémentation concrète.

## 4. Diagramme UML

![alt text](image.png)

## 5. Usage du patron Simple Factory

La création des objets `DictionaryHashCracker` et `BruteForceHashCracker` est centralisée dans `HashCrackerFactory.create(String method)`. La classe `Main` ne connaît jamais les classes concrètes : elle demande simplement une instance de `HashCracker` à la fabrique via le paramètre `-m` (`DICO` ou `BRUTE`). Cela évite toute instanciation directe dans le code client et respecte la contrainte du sujet.

```java
HashCracker cracker = HashCrackerFactory.create("DICO");
String password = cracker.crack(hash);
```

## 6. Résultats obtenus

Les résultats ci-dessous ont été obtenus avec `mvn clean install` et une exécution directe du CLI sur le build généré (Java 17).

### Tests unitaires

| Fichier | Tests | Résultat |
|---|---|---|
| `MD5UtilsTest` | 5/5 | ✅ |
| `DictionaryHashCrackerTest` | 4/4 | ✅ |
| `BruteForceHashCrackerTest` | 5/5 | ✅ |
| `HashCrackerFactoryTest` | 6/6 | ✅ |
| **Total** | **20/20** | **✅ BUILD SUCCESS** |

La fabrique `HashCrackerFactory` a été testée unitairement : création réussie pour `"DICO"` et `"BRUTE"`, levée d'exception pour une méthode invalide.

### Cassage par dictionnaire

Le fichier `dictionary.txt` contient 10 mots (test, password, admin, 123456, hello, world, qwerty, azerty, root, user).

```
$ java -cp target/classes com.passwordcracker.cli.Main -m DICO -h 098f6bcd4621d373cade4e832627b4f6
Password found: test
Temps d'exécution : 24 ms
```

Le hash `098f6bcd4621d373cade4e832627b4f6` est le MD5 de `"test"`, présent dans le dictionnaire.

```
$ java -cp target/classes com.passwordcracker.cli.Main -m DICO -h d16fb36f0911f878998c136191af705e
Password not found
Temps d'exécution : 25 ms
```

Le hash `d16fb36f0911f878998c136191af705e` est le MD5 de `"xyz"` — ce mot n'est pas dans le dictionnaire, la recherche échoue.

### Cassage par force brute

Alphabet : `a-z`, longueur maximale : 4 caractères.

```
$ java -cp target/classes com.passwordcracker.cli.Main -m BRUTE -h 098f6bcd4621d373cade4e832627b4f6
Password found: test
Temps d'exécution : 101 ms
Nombre de tentatives : 355414
```

```
$ java -cp target/classes com.passwordcracker.cli.Main -m BRUTE -h 5d41402abc4b2a76b9719d911017c592
Password not found
Temps d'exécution : 122 ms
Nombre de tentatives : 475254
```

Le hash `5d41402abc4b2a76b9719d911017c592` est le MD5 de `"hello"` (5 lettres, hors de la limite de 4 caractères). Aucune des 475 254 combinaisons possibles ne correspond.

### Synthèse

Build Maven réussi, 20 tests passés sans échec. Les deux stratégies (dictionnaire et force brute) fonctionnent correctement, avec gestion des cas de succès et d'échec. La fabrique instancie la bonne stratégie selon le paramètre `-m` et lève une exception claire pour les méthodes invalides.

Vidéo de démonstration : *[lien à insérer, max 10 minutes]*

## 7. Difficultés rencontrées

- Le hash d'exemple fourni dans l'énoncé ne correspondait pas au mot "test" attendu ; nous avons recalculé le hash réel pour valider nos tests plutôt que de nous fier aveuglément à l'énoncé.
- Générer efficacement toutes les combinaisons de 1 à 4 caractères en force brute sans explosion du temps d'exécution.
- Garantir que chaque personne puisse travailler sans bloquer les autres : résolu par une organisation séquentielle avec livrables clairs à chaque étape (voir répartition des tâches).
- Centraliser la création des objets dans la fabrique sans dupliquer de logique entre les stratégies.

## 8. Conclusion

Ce mini-projet a permis de mettre en pratique le patron Simple Factory dans un contexte concret : centraliser la création d'objets polymorphes tout en gardant le code client découplé des implémentations concrètes. La limite principale de cette approche est qu'ajouter une nouvelle stratégie nécessite de modifier la fabrique elle-même, ce qui viole le principe Open/Closed — point qui sera corrigé dans le mini-projet suivant.

---

### Questions de réflexion

1. **Avantages de la fabrique simple** : centralise la création d'objets, découple le client des classes concrètes, simplifie la maintenance du point de création.
2. **Inconvénients** : viole le principe Open/Closed (il faut modifier la fabrique pour ajouter un cas), logique de création concentrée dans une seule classe qui peut grossir.
3. **Ajout d'une nouvelle stratégie** : il faut modifier `HashCrackerFactory.create()` pour ajouter un nouveau `case`, en plus de créer la nouvelle classe.
4. **Respect du principe Open/Closed** : non, car la fabrique doit être modifiée (et non simplement étendue) à chaque nouvelle stratégie.