# Projet JEE Groupe Q - Artic

## Sommaire
- [Contenu](#contenu)
- [Objectif](#objectif)
- [Groupe](#groupe)
- [Fichiers](#fichiers)
- [Prérequis](#prérequis)
- [Compiler](#compiler)
- [Lancement](#lancement)

## Contenu

**Artic** est une application web Java EE de gestion d'entreprise permettant de :
- Gérer les employés (création, modification, consultation)
- Gérer les départements
- Gérer les projets et affecter des employés
- Gérer les salaires et générer des fiches de paie en PDF
- Suivre l'avancement des projets
- Authentification et gestion des profils utilisateurs

## Objectif

Le but de ce projet est de développer une application web complète utilisant les technologies Java EE (Servlets, JSP, Hibernate) pour gérer les ressources humaines et les projets d'une entreprise, tout en manipulant une base de données MySQL.

## Groupe

- **Groupe Q**
- Membres : Orianne Courtade, Jonathan Ngo, Fantine Bodier, Mathieu Rouet et Tom Conti

## Fichiers

### Structure principale
- **`pom.xml`** : Configuration Maven (dépendances, build)
- **`Data/`** : Scripts SQL pour créer la base de données et les utilisateurs
  - `CreateDataBase.sql` : Création de la base Artic et des tables
  - `CreateUser.sql` : Création de l'utilisateur MySQL
- **`src/main/java/com/example/projetjeegroupeq/`** : Code source Java
  - `controller/` : Servlets (gestion des requêtes HTTP)
  - `dao/` : Classes d'accès aux données (Data Access Object)
  - `model/` : Entités JPA (Employee, Department, Project, Pay...)
  - `util/` : Classes utilitaires (génération PDF, etc.)
- **`src/main/resources/`** : Fichiers de configuration
  - `persistence.xml` : Configuration JPA/Hibernate
  - `templates/` : Templates HTML pour les PDF
- **`src/main/webapp/`** : Pages web et ressources
  - `*.jsp` : Pages JSP (interface utilisateur)
  - `CSS/style.css` : Styles de l'application
  - `WEB-INF/web.xml` : Configuration du déploiement web

## Prérequis

### Logiciels requis
- **Java JDK 21** ou supérieur
- **Maven 3.9+** (pour la compilation)
- **WampServer** (pour le serveur MySQL et Apache)
- **MySQL Workbench** (pour gérer la base de données)
- **Apache Tomcat 10+** (serveur d'applications)

### Bibliothèques (gérées automatiquement par Maven)
- Jakarta Servlet API
- Hibernate ORM 7.0.4
- MySQL Connector
- JSTL (Java Standard Tag Library)
- Flying Saucer (génération de PDF)
- Hibernate Validator

## Compiler

1. Ouvrir un terminal dans le dossier du projet
2. Compiler le projet avec Maven :
   ```powershell
   mvn clean install
   ```
3. Le fichier WAR sera généré dans le dossier `target/` : `ProjetJEEGroupeQ-1.0-SNAPSHOT.war`

## Lancement

### 1. Configurer la base de données

1. **Démarrer WampServer** et vérifier que MySQL est actif (icône verte)

2. **Ouvrir MySQL Workbench** et se connecter au serveur local

3. **Exécuter les scripts SQL** dans l'ordre :
   - Ouvrir `Data/CreateDataBase.sql` et exécuter pour créer la base Artic
   - Ouvrir `Data/CreateUser.sql` et exécuter pour créer l'utilisateur

   Ou manuellement :
   ```sql
   CREATE USER 'artic_user'@'localhost' IDENTIFIED BY 'monSuperPass';
   GRANT ALL PRIVILEGES ON Artic.* TO 'artic_user'@'localhost';
   FLUSH PRIVILEGES;
   ```

### 2. Déployer l'application

1. **Copier le fichier WAR** généré (`target/ProjetJEEGroupeQ-1.0-SNAPSHOT.war`) dans le dossier `webapps/` de votre Tomcat

2. **Démarrer Tomcat** (via IntelliJ IDEA ou en ligne de commande)

### 3. Accéder à l'application

Ouvrir un navigateur et accéder à :
```
http://localhost:8080/ProjetJEEGroupeQ-1.0-SNAPSHOT/
```

La page de connexion s'affiche. Utilisez les identifiants créés dans la base de données pour vous connecter.

| Rôle           | Username | Mot de passe  | Permissions                                       |
|----------------|----------|---------------|---------------------------------------------------|
| Administrateur | `admin`  | `admin`       | Accès complet à toutes les fonctionnalités        |
| RH             | `rh`     | `rh`          | Consultation illimitée mais suppression interdite |
| Employé        | `psilva` | `departement` | Consultation limitée                              |


---

**Note** : Pour le développement avec IntelliJ IDEA, configurer un serveur Tomcat local et déployer l'artefact WAR directement depuis l'IDE.



