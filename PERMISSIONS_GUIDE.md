# Guide du Système de Gestion des Permissions

Ce guide explique comment utiliser et étendre le système de gestion des permissions basé sur les rôles (ADMIN, RH, EMPLOYE).

## Architecture

Le système est composé de plusieurs classes :

1. **PermissionConfig** : Configuration centralisée de toutes les permissions
2. **PermissionChecker** : Classe utilitaire pour vérifier les permissions
3. **RoleUtil** : Classe utilitaire pour vérifier les rôles d'un employé
4. **RoleFilter** : Filtre qui protège automatiquement toutes les routes selon PermissionConfig

## Ajouter une Nouvelle Permission

### Étape 1 : Modifier PermissionConfig

Ouvrez `src/main/java/com/example/projetjeegroupeq/util/PermissionConfig.java` et ajoutez votre permission dans le bloc `static {}`.

#### Pour un Servlet :

```java
// Format : addServletPermission(route, action, rôles...)
addServletPermission("/votreServlet", "add", "RH", "ADMIN");
addServletPermission("/votreServlet", "edit", "RH", "ADMIN");
addServletPermission("/votreServlet", "delete", "ADMIN");
```

#### Pour une JSP :

```java
// Format : addJspPermission(cheminJSP, rôles...)
addJspPermission("/VotrePage.jsp", "RH", "ADMIN");
```

**C'est tout !** Le filtre `RoleFilter` protégera automatiquement cette route/JSP.

### Étape 2 : Vérifier dans le Servlet (optionnel mais recommandé)

Pour une double sécurité, vous pouvez aussi vérifier dans votre servlet :

```java
import com.example.projetjeegroupeq.util.PermissionChecker;

@Override
protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
    String action = req.getParameter("action");
    
    if ("add".equals(action)) {
        // Vérifier les permissions
        if (!PermissionChecker.checkPermission(req, resp, "/votreServlet", "add")) {
            return; // L'erreur 403 a déjà été envoyée
        }
        // Continuer le traitement...
    }
}
```

### Étape 3 : Masquer les boutons dans les JSP

Dans vos pages JSP, incluez le helper et utilisez-le :

```jsp
<%@ include file="/WEB-INF/includes/PermissionHelper.jsp" %>

<%
    // Vérifier par route/action
    if (hasPermission(request, "/employee", "add")) {
%>
    <a href="/employee?action=add">Ajouter un employé</a>
<%
    }
%>

<%
    // Ou vérifier par rôles directement
    if (hasRole(request, "RH", "ADMIN")) {
%>
    <a href="/employee?action=add">Ajouter un employé</a>
<%
    }
%>
```

## Exemples d'Utilisation

### Exemple 1 : Protéger l'ajout d'un département

Dans `PermissionConfig.java` :
```java
addServletPermission("/department", "add", "RH", "ADMIN");
```

Dans `DepartmentServlet.java` :
```java
case "add" -> {
    if (!PermissionChecker.checkPermission(req, resp, "/department", "add")) {
        return;
    }
    showAddDepartmentForm(req, resp);
}
```

Dans `Gestion.jsp` :
```jsp
<%
    if (hasPermission(request, "/department", "add")) {
%>
    <li><a href="/department?action=add">Créer un département</a></li>
<%
    }
%>
```

### Exemple 2 : Protéger une page JSP complète

Dans `PermissionConfig.java` :
```java
addJspPermission("/FormPay.jsp", "RH", "ADMIN");
```

Le filtre protégera automatiquement cette page. Aucun code supplémentaire nécessaire !

### Exemple 3 : Vérifier les permissions dans un servlet POST

```java
@Override
protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
    String action = req.getParameter("action");
    
    if ("add".equals(action)) {
        if (!PermissionChecker.checkPermission(req, resp, "/pay", "add")) {
            return;
        }
        // Traiter l'ajout...
    }
}
```

## Rôles Disponibles

- **ADMIN** : Accès complet à toutes les fonctionnalités
- **RH** : Accès à la gestion des employés, départements, projets, et fiches de paie
- **EMPLOYE** : Accès limité (consultation de ses propres informations)

## Méthodes Utiles

### Dans les Servlets

```java
// Vérifier et envoyer 403 si pas de permission
PermissionChecker.checkPermission(req, resp, "/route", "action");

// Vérifier sans envoyer d'erreur
boolean hasPerm = PermissionChecker.hasPermission(req, "/route", "action");

// Vérifier les rôles
boolean isAdmin = PermissionChecker.hasRole(req, "ADMIN");
```

### Dans les JSP

```jsp
<%@ include file="/WEB-INF/includes/PermissionHelper.jsp" %>

<%
    // Vérifier par route/action
    if (hasPermission(request, "/route", "action")) { ... }
    
    // Vérifier par rôles
    if (hasRole(request, "RH", "ADMIN")) { ... }
%>
```

## Notes Importantes

1. **Le filtre RoleFilter protège automatiquement** toutes les routes définies dans `PermissionConfig`
2. **Double sécurité recommandée** : Vérifier aussi dans les servlets pour plus de robustesse
3. **Les pages publiques** (login, index) ne sont pas protégées par le filtre
4. **Si aucune permission n'est définie** pour une route, elle est accessible à tous les utilisateurs connectés

## Dépannage

### Une page n'est pas protégée

1. Vérifiez que la permission est bien définie dans `PermissionConfig`
2. Vérifiez que le filtre `RoleFilter` est bien configuré dans `web.xml` (ou via `@WebFilter`)
3. Vérifiez que l'utilisateur est bien connecté (session existe)

### Erreur 403 inattendue

1. Vérifiez les rôles de l'utilisateur dans la base de données
2. Vérifiez que les rôles sont bien chargés dans la session (`loggedUser.getEmployeeRoles()`)
3. Vérifiez la configuration dans `PermissionConfig`

## Structure des Fichiers

```
src/main/java/com/example/projetjeegroupeq/
├── util/
│   ├── PermissionConfig.java      # Configuration centralisée
│   ├── PermissionChecker.java     # Vérifications dans les servlets
│   └── RoleUtil.java              # Vérifications de rôles
├── controller/
│   └── RoleFilter.java            # Filtre de protection automatique
└── model/
    └── Employee.java               # Modèle avec méthodes helper

src/main/webapp/
└── WEB-INF/
    └── includes/
        └── PermissionHelper.jsp   # Helper pour les JSP
```

## Permissions Basées sur les Fonctions (Chefs de Département/Projet)

Le système supporte également les permissions basées sur les fonctions, pas seulement les rôles.

### Vérifier si un utilisateur est chef de département/projet

```java
// Dans un servlet
if (PermissionChecker.isDepartmentChief(req, departmentId)) {
    // L'utilisateur est chef de ce département
}

if (PermissionChecker.isProjectChief(req, projectId)) {
    // L'utilisateur est chef de ce projet
}

// Vérifier s'il est chef d'au moins un département/projet
if (PermissionChecker.isAnyDepartmentChief(req)) {
    // L'utilisateur est chef d'au moins un département
}
```

### Permissions contextuelles (rôles OU fonctions)

```java
// Exemple : Un employé peut voir les fiches de paie de son département s'il est :
// - RH/ADMIN (rôle)
// - OU chef du département de l'employé (fonction)
Employee targetEmployee = employeeDAO.searchById(employeeId);
Integer departmentId = targetEmployee.getDepartment() != null ? 
    targetEmployee.getDepartment().getId() : null;

boolean canView = PermissionChecker.hasContextualPermission(
    req, 
    new String[]{"RH", "ADMIN"},  // Rôles requis
    departmentId,                  // ID du département (optionnel)
    null                           // ID du projet (optionnel)
);
```

### Exemple dans une JSP

```jsp
<%
    // Afficher un bouton seulement si l'utilisateur est RH/ADMIN 
    // OU chef du département
    Employee targetEmployee = (Employee) request.getAttribute("employee");
    boolean canEdit = false;
    
    if (PermissionChecker.hasRole(request, "RH", "ADMIN")) {
        canEdit = true;
    } else if (targetEmployee != null && targetEmployee.getDepartment() != null) {
        canEdit = PermissionChecker.isDepartmentChief(
            request, 
            targetEmployee.getDepartment().getId()
        );
    }
    
    if (canEdit) {
%>
    <a href="/employee?action=edit&id=<%= targetEmployee.getId() %>">Modifier</a>
<%
    }
%>
```

### Exemple : Permissions pour les projets

```java
// Dans ProjectServlet
int projectId = Integer.parseInt(req.getParameter("id"));
Project project = projectDAO.searchById(projectId);

// Vérifier si l'utilisateur peut modifier le projet
boolean canEdit = PermissionChecker.hasContextualPermission(
    req,
    new String[]{"RH", "ADMIN"},  // Rôles requis
    null,                          // Pas de département
    projectId                      // ID du projet
);

if (!canEdit) {
    resp.sendError(HttpServletResponse.SC_FORBIDDEN, 
        "Seuls les RH, ADMIN ou le chef de projet peuvent modifier ce projet.");
    return;
}
```

## Conclusion

Avec ce système, ajouter une nouvelle permission ne nécessite qu'une seule ligne dans `PermissionConfig.java`. Le reste est géré automatiquement par le filtre et les helpers !

Le système supporte maintenant :
- **Permissions basées sur les rôles** (ADMIN, RH, EMPLOYE)
- **Permissions basées sur les fonctions** (Chef de département, Chef de projet)
- **Permissions contextuelles** (combinaison de rôles ET fonctions)

