---
```markdown
# TP Gestion de Commandes 🚀

Projet d'API REST robuste développée avec **Spring Boot 3.x**, sécurisée par **Spring Security** et **JWT (JSON Web Tokens)**, avec une base de données en mémoire **H2**.

---

## 📋 Prérequis

Avant de lancer l'application, assurez-vous d'avoir installé :
* **Java 17** ou supérieur
* **Maven 3.x**
* **Postman** (pour tester les endpoints)

---

## 🚀 1. Comment lancer l'application

Pour démarrer le serveur de développement, exécutez la commande suivante à la racine du projet (là où se trouve le fichier `pom.xml`) :

```bash
mvn clean spring-boot:run

```

Le serveur démarrera par défaut sur le port **`8080`**.

---

## ⚙️ 2. Comment activer les profils

L'application utilise les profils Spring pour adapter sa configuration. Par défaut, le profil `dev` est configuré pour initialiser automatiquement des données de test (utilisateurs, produits, commandes).

### Option A : Via la ligne de commande (Recommandé)

Vous pouvez forcer l'activation d'un profil spécifique lors du lancement avec l'argument `-Dspring-boot.run.profiles` :

```bash
# Activer le profil de développement
mvn spring-boot:run -Dspring-boot.run.profiles=dev

# Activer le profil de production
mvn spring-boot:run -Dspring-boot.run.profiles=prod

```

### Option B : Via le fichier application.properties

Vous pouvez également fixer le profil actif directement dans votre fichier `src/main/resources/application.properties` :

```properties
spring.profiles.active=dev

```

---

## 📖 3. Comment accéder à Swagger & H2 Console

Une fois l'application démarrée, vous pouvez accéder aux outils d'accompagnement via votre navigateur :

* **Interface Swagger UI :** [http://localhost:8080/swagger-ui/index.html](https://www.google.com/search?q=http://localhost:8080/swagger-ui/index.html)
*(Permet de visualiser et de tester interactivement tous les endpoints de l'API)*
* **Console de la Base de Données H2 :** [http://localhost:8080/h2-console](https://www.google.com/search?q=http://localhost:8080/h2-console)
* **JDBC URL :** `jdbc:h2:mem:commandesdb`
* **User Name :** `SA`
* **Password :** *(laisser vide)*



---

## 🧪 4. Exemples d'appels API (Guide Postman)

> ⚠️ **Règle d'or avec Postman :** Si vous modifiez une URL ou un type d'authentification, fermez l'onglet Postman actif sans le sauvegarder et ouvrez-en un nouveau pour éviter les conflits de cache.

### 🔐 Authentification (Public)

Pour interagir avec les routes protégées, vous devez d'abord récupérer un jeton JWT.

* **Requête :** `POST http://localhost:8080/api/auth/login`
* **Headers :** `Content-Type: application/json`
* **Body (JSON) :**

```json
{
    "username": "admin",
    "password": "admin123"
}

```

* **Réponse attendue (200 OK) :** Copiez la valeur du jeton `"token"` renvoyée pour les requêtes suivantes.

---

### 📦 Gestion des Commandes (Sécurisé)

> 💡 **Note sur l'autorisation :** Pour toutes les requêtes ci-dessous, allez dans l'onglet **Authorization** de Postman, sélectionnez **Bearer Token**, et collez votre jeton JWT (sans les guillemets ni le préfixe `token:`).

#### 1. Lister toutes les commandes (Réservé à l'ADMIN)

* **Méthode :** `GET`
* **URL :** `http://localhost:8080/api/commandes`
* **Droits :** Rôle `ADMIN` uniquement. Un rôle `USER` recevra un message *Access Denied*.

#### 2. Créer une nouvelle commande (ADMIN & USER)

Cette route crée la commande ainsi que toutes ses lignes associées d'un seul coup, tout en mettant à jour les stocks de produits.

* **Méthode :** `POST`
* **URL :** `http://localhost:8080/api/commandes`
* **Body (JSON) :**

```json
{
    "clientId": 1,
    "lignes": [
        {
            "produitId": 1,
            "quantite": 2
        },
        {
            "produitId": 2,
            "quantite": 1
        }
    ]
}

```

* **Réponse attendue (201 Created) :** Renvoie la commande complète calculée avec son prix total.

#### 3. Obtenir le détail d'une commande par son ID (ADMIN & USER)

* **Méthode :** `GET`
* **URL :** `http://localhost:8080/api/commandes/1`

```

---

### 🛠️ Ce qu'il vous reste à faire

1. Ajoutez ce fichier à votre prochain commit Git.
2. Si vous avez modifié le mot de passe ou les identifiants par défaut dans votre `DataInitializer`, ajustez simplement les valeurs dans la section **4. Exemples d'appels API** pour que vos futurs relecteurs s'y retrouvent du premier coup.

```
