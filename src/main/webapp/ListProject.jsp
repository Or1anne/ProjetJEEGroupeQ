<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.example.projetjeegroupeq.model.Project"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Collection" %>
<%@ page import="java.util.Collections" %>
<%@ page import="com.example.projetjeegroupeq.model.Employee" %>
<%@ page import="com.example.projetjeegroupeq.model.ProjectStatus" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Liste des projets</title>
    <link rel="stylesheet" href="CSS/style.css">
</head>
<body>
<div class="hero-head">
    <nav class="navbar">
        <div class="container">
            <div class="navbar-start">
                <a href="index.jsp" class="navbar-item">Accueil</a>
                <%
                    // On récupère l’utilisateur connecté
                    Object user = session.getAttribute("loggedUser");

                    if (user != null) {
                        // Si connecté, on affiche Recherche et Gestion
                %>
                <a href="Search.jsp" class="navbar-item">Recherche</a>
                <a href="Gestion.jsp" class="navbar-item">Gestion</a>
                <%
                    }
                %>
            </div>

            <div class="navbar-end">
                <%
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
    List<Project> projects = (List<Project>) request.getAttribute("projects");

    if (projects == null) projects = Collections.emptyList();

    String contextPath = request.getContextPath();

    String filterField = (String) request.getAttribute("filterField");
    String filterValue = (String) request.getAttribute("filterValue");
    String sortField = (String) request.getAttribute("sortField");
    String sortOrder = (String) request.getAttribute("sortOrder");

    ProjectStatus[] projectStatuses = (ProjectStatus[]) request.getAttribute("projectStatuses");
    if (projectStatuses == null) {
        projectStatuses = ProjectStatus.values();
    }
    List<Employee> allEmployees = (List<Employee>) request.getAttribute("allEmployees");
    if (allEmployees == null) {
        allEmployees = java.util.Collections.emptyList();
    }
%>

<div class="hero-body">
    <h2>Liste des projets</h2>
    <nav>
        <a class="btn" href="<%=contextPath%>/project?action=add">Créer un projet</a>
    </nav>

    <!-- Formulaire de filtre/tri -->
    <form method="get" action="<%= contextPath %>/project" style="margin: 1em 0;">
        <input type="hidden" name="action" value="list"/>

        <label>Filtrer par :</label>
        <select name="filterField">
            <option value="">-- Aucun --</option>
            <option value="name" <%= "name".equals(filterField) ? "selected" : "" %>>Nom du projet</option>
            <option value="status" <%= "status".equals(filterField) ? "selected" : "" %>>Statut</option>
            <option value="manager" <%= "manager".equals(filterField) ? "selected" : "" %>>Chef de projet</option>
        </select>

        <!-- Valeur du filtre (nom, statut ou chef) -->
        <% if ("name".equals(filterField)) { %>
            <input type="text" name="filterValue" value="<%= filterValue != null ? filterValue : "" %>" placeholder="Nom du projet contient..." />
        <% } else { %>
            <select name="filterValue">
                <option value="">-- Valeur --</option>
                <% if ("status".equals(filterField)) { %>
                    <% for (ProjectStatus s : projectStatuses) { %>
                        <option value="<%= s.name() %>" <%= s.name().equals(filterValue) ? "selected" : "" %>>
                            <%= s.getTranslation() %>
                        </option>
                    <% } %>
                <% } else if ("manager".equals(filterField)) { %>
                    <% for (Employee e : allEmployees) { %>
                        <option value="<%= e.getId() %>" <%= String.valueOf(e.getId()).equals(filterValue) ? "selected" : "" %>>
                            <%= e.getLastName() %> <%= e.getFirstName() %>
                        </option>
                    <% } %>
                <% } %>
            </select>
        <% } %>

        <label>Trier par :</label>
        <select name="sortField">
            <option value="">-- Aucun --</option>
            <option value="name" <%= "name".equals(sortField) ? "selected" : "" %>>Nom du projet</option>
            <option value="status" <%= "status".equals(sortField) ? "selected" : "" %>>Statut</option>
            <option value="manager" <%= "manager".equals(sortField) ? "selected" : "" %>>Chef de projet</option>
        </select>
        <select name="sortOrder">
            <option value="asc" <%= sortOrder == null || "asc".equalsIgnoreCase(sortOrder) ? "selected" : "" %>>Croissant</option>
            <option value="desc" <%= "desc".equalsIgnoreCase(sortOrder) ? "selected" : "" %>>Décroissant</option>
        </select>

        <button type="submit">Appliquer</button>
        <a href="<%= contextPath %>/project?action=list">Réinitialiser</a>
    </form>

    <table class="table">
        <thead>
        <tr>
            <th>Nom</th>
            <th>Chef de projet</th>
            <th>État</th>
            <th>Actions rapides</th>
        </tr>
        </thead>
        <tbody>
        <% if (projects.isEmpty()) {%>
            <tr>
                <td colspan="6">Aucun projet enregistré.</td>
            </tr>
        <% } else {
            for (Project p : projects) {
                Employee chef = p.getChefProj();
        %>
            <tr style="cursor:pointer" onclick="window.location.href='<%= request.getContextPath() %>/project?action=view&id=<%= p.getId() %>'">
                <td><%= p.getName_project() != null ? p.getName_project() : "-"%></td>
                <td><%= p.getChefProj() != null ? p.getChefProj().getLastName() + " " + p.getChefProj().getFirstName() : "-"%></td>
                <td><%= p.getStatus() != null ? p.getStatus().getTranslation() : "-"%></td>
                <td>
                    <a href="<%= contextPath %>/project?action=addEmployees&id=<%= p.getId() %>">Affecter employés</a> |
                    <a href="<%= contextPath %>/project?action=edit&id=<%= p.getId() %>">Modifier</a> |
                    <a href="<%= contextPath %>/project?action=delete&id=<%= p.getId() %>"
                       onclick="return confirm('Supprimer ce projet ?');">Supprimer</a>
                </td>
            </tr>
        <% }} %>
        </tbody>
    </table>
</div>
</body>
</html>