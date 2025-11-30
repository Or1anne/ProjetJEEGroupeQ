<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Collections" %>
<%@ page import="com.example.projetjeegroupeq.model.Employee" %>
<%@ page import="com.example.projetjeegroupeq.model.Grade" %>
<%@ page import="com.example.projetjeegroupeq.model.Department" %>
<%@ page import="com.example.projetjeegroupeq.util.PermissionChecker" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Liste des employés</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/style.css">
</head>
<body>
<div class="hero-head">
    <nav class="navbar">
        <div class="container">
            <div class="navbar-start">
                <a href="index.jsp" class="navbar-item">Accueil</a>
                <a href="Gestion.jsp" class="navbar-item">Gestion</a>
            </div>

            <div class="navbar-end">
                <%
                    // On récupère l’utilisateur connecté
                    Object user = session.getAttribute("loggedUser");

                    if (user != null) {
                        // Si connecté
                %>
                <a href="Profile.jsp" class="navbar-item">Profil</a>
                <a href="<%= request.getContextPath() %>/logout" class="navbar-item">Déconnexion</a>
                <%
                } else {
                    // Si pas connecté
                %>
                <a href="<%= request.getContextPath() %>/login" class="navbar-item">Connexion</a>
                <%
                    }
                %>
            </div>
        </div>
    </nav>
</div>

<%
    List<Employee> employees = (List<Employee>) request.getAttribute("employees");
    if (employees == null) {
        employees = Collections.emptyList();
    }
    String contextPath = request.getContextPath();

    String filterField = (String) request.getAttribute("filterField");
    String filterValue = (String) request.getAttribute("filterValue");
    String sortField = (String) request.getAttribute("sortField");
    String sortOrder = (String) request.getAttribute("sortOrder");

    Grade[] grades = (Grade[]) request.getAttribute("grades");
    if (grades == null) {
        grades = Grade.values();
    }
    List<Department> departments = (List<Department>) request.getAttribute("departments");
    if (departments == null) {
        departments = java.util.Collections.emptyList();
    }
%>

<div class="hero-body">
    <h2>Liste des employés</h2>
    <nav>
        <a class="btn" href="<%= contextPath %>/employee?action=add">Ajouter un employé</a>
    </nav>

    <div class="filter-panel">
        <div class="filter-panel-header">
            <button type="button" class="filter-toggle-btn" onclick="toggleFilter('employee-filter-bar', this);">
                <span class="filter-toggle-icon">🔍</span>
                <span class="filter-toggle-label">Recherche / tri</span>
            </button>
        </div>

        <form id="employee-filter-bar" class="filter-bar" method="get" action="<%= contextPath %>/employee">
            <input type="hidden" name="action" value="list"/>

            <label>Filtrer par :</label>
            <select name="filterField">
                <option value="">-- Aucun --</option>
                <option value="grade" <%= "grade".equals(filterField) ? "selected" : "" %>>Grade</option>
                <option value="department" <%= "department".equals(filterField) ? "selected" : "" %>>Département</option>
                <option value="name" <%= "name".equals(filterField) ? "selected" : "" %>>Nom/Prénom</option>
            </select>

            <!-- valeur de filtre -->
            <% if ("grade".equals(filterField)) { %>
                <select name="filterValue">
                    <option value="">-- Grade --</option>
                    <% for (Grade g : grades) { %>
                        <option value="<%= g.name() %>" <%= g.name().equals(filterValue) ? "selected" : "" %>>
                            <%= g.getLabel() %>
                        </option>
                    <% } %>
                </select>
            <% } else if ("department".equals(filterField)) { %>
                <select name="filterValue">
                    <option value="">-- Département --</option>
                    <% for (Department d : departments) { %>
                        <option value="<%= d.getId() %>" <%= String.valueOf(d.getId()).equals(filterValue) ? "selected" : "" %>>
                            <%= d.getDepartmentName() %>
                        </option>
                    <% } %>
                </select>
            <% } else if ("name".equals(filterField)) { %>
                <input type="text" name="filterValue" value="<%= filterValue != null ? filterValue : "" %>" placeholder="Rechercher par nom..."/>
            <% } else { %>
                <input type="text" name="filterValue" value="" placeholder="Valeur"/>
            <% } %>

            <label>Trier par :</label>
            <select name="sortField">
                <option value="">-- Aucun --</option>
                <option value="lastName" <%= "lastName".equals(sortField) ? "selected" : "" %>>Nom</option>
                <option value="firstName" <%= "firstName".equals(sortField) ? "selected" : "" %>>Prénom</option>
                <option value="grade" <%= "grade".equals(sortField) ? "selected" : "" %>>Grade</option>
                <option value="salary" <%= "salary".equals(sortField) ? "selected" : "" %>>Salaire</option>
                <option value="department" <%= "department".equals(sortField) ? "selected" : "" %>>Département</option>
            </select>
            <select name="sortOrder">
                <option value="asc" <%= sortOrder == null || "asc".equalsIgnoreCase(sortOrder) ? "selected" : "" %>>Croissant</option>
                <option value="desc" <%= "desc".equalsIgnoreCase(sortOrder) ? "selected" : "" %>>Décroissant</option>
            </select>

            <button type="submit">Appliquer</button>
            <a href="<%= contextPath %>/employee?action=list" class="btn-reset">Réinitialiser</a>
        </form>
    </div>

    <table class="table">
        <thead>
        <tr>
            <th>Nom</th>
            <th>Prénom</th>
            <th>Grade</th>
            <th>Poste</th>
            <th>Département</th>
            <%
                if (PermissionChecker.hasPermission(request, "/employee", "edit") || PermissionChecker.hasPermission(request, "/employee", "delete")) {
            %>
            <th>Actions</th>
            <%
                }
            %>
        </tr>
        </thead>
        <tbody>
        <!-- Si aucun employé dans la table une ligne qui le dit, sinon on affiche chacun ligne par ligne -->
        <% if (employees.isEmpty()) { %>
            <tr>
                <td colspan="6">Aucun employé enregistré.</td>
            </tr>
        <% } else {
               for (Employee employee : employees) {
                   Department department = employee.getDepartment();
        %>
            <tr style="cursor:pointer" onclick="window.location.href='<%= request.getContextPath() %>/employee?action=view&id=<%= employee.getId() %>'">
                <td><%= employee.getLastName() != null ? employee.getLastName() : "" %></td>
                <td><%= employee.getFirstName() != null ? employee.getFirstName() : "" %></td>
                <td><%= employee.getGrade() != null ? employee.getGrade().getLabel() : "" %></td>
                <td><%= employee.getPost() != null ? employee.getPost() : "" %></td>
                <td><%= department != null ? department.getDepartmentName() : "-" %></td>
                <%
                    if (PermissionChecker.hasPermission(request, "/employee", "edit") || PermissionChecker.hasPermission(request, "/employee", "delete")) {
                %>
                <td>
                    <%
                        if (PermissionChecker.hasPermission(request, "/employee", "edit")) {
                    %>
                    <a href="<%= contextPath %>/employee?action=edit&id=<%= employee.getId() %>">Modifier</a>
                    <%
                        }
                    %>
                    <%
                        if (PermissionChecker.hasPermission(request, "/employee", "delete")) {
                    %>
                    |
                    <a href="<%= contextPath %>/employee?action=delete&id=<%= employee.getId() %>"
                       onclick="return confirm('Supprimer cet employé ?');">Supprimer</a>
                    <%
                        }
                    %>
                </td>
                <%
                    }
                %>
            </tr>
        <%     }
           } %>
        </tbody>
    </table>
</div>

<script type="text/javascript">
    (function initEmployeeFilter() {
        var el = document.getElementById('employee-filter-bar');
        if (!el) return;
        // Par défaut: visible; si tu préfères fermé au chargement, ajoute la classe is-collapsed ici.
        // el.classList.add('is-collapsed');
    })();

    function toggleFilter(id, btn) {
        var el = document.getElementById(id);
        if (!el) return;
        var isCollapsed = el.classList.toggle('is-collapsed');
        if (btn) {
            var iconSpan = btn.querySelector('.filter-toggle-icon');
            if (iconSpan) {
                iconSpan.textContent = isCollapsed ? '🔍' : '➖';
            }
        }
    }
</script>
</body>
</html>
