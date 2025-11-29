<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.example.projetjeegroupeq.model.Project"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Collection" %>
<%@ page import="java.util.Collections" %>
<%@ page import="com.example.projetjeegroupeq.model.Employee" %>
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
%>

<div class="hero-body">
    <h2>Liste des projets</h2>
    <nav>
        <a class="btn" href="<%=contextPath%>/project?action=add">Créer un projet</a>
    </nav>

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
                    <a href="<%=contextPath%>/project?action=track&id=<%=p.getId()%>">Suivre</a> |
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