<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %><!DOCTYPE html>
<%@ page import="java.util.List" %>
<%@ page import="com.example.projetjeegroupeq.model.Department" %>
<%@ page import="com.example.projetjeegroupeq.model.Employee" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Membres du département</title>
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
    Department department = (Department) request.getAttribute("department");
    List<Employee> employees = (List<Employee>) request.getAttribute("employees");
    if (employees == null) {
        employees = java.util.Collections.emptyList();
    }
    String contextPath = request.getContextPath();
%>

<div class="hero-body">

    <div class="card">
        <strong>Chef du département :</strong>
        <% if (department.getChefDepartment() != null) { %>
        <a href="<%= contextPath %>/employee?action=view&id=<%= department.getChefDepartment().getId() %>">
            <%= department.getChefDepartment().getFirstName() %> <%= department.getChefDepartment().getLastName() %>
        </a>
        <% } else { %>
        Aucun chef
        <% } %>
    </div>
    <h2>Membres du département : "<%= department != null ? department.getDepartmentName() : "" %>"</h2> <!-- TODO Récupérer le nom du département dynamiquement -->
    <nav>
        <a href="<%= contextPath %>/department">Retour</a>
    </nav>
    <table class="table">
        <thead>
        <tr>
            <th>Nom</th>
            <th>Prénom</th>
            <th>Grade</th>
            <th>Poste</th>
        </tr>
        </thead>
        <tbody>
            <% if (employees.isEmpty()) { %>
        <tr>
            <td colspan="4">Aucun employé pour ce département.</td>
        </tr>
            <% } else {
           for (Employee e : employees) {
    %>
        <tr>
            <td><%= e.getLastName() %></td>
            <td><%= e.getFirstName() %></td>
            <td><%= e.getGrade() != null ? e.getGrade().getLabel() : "" %></td>
            <td><%= e.getPost() != null ? e.getPost() : "" %></td>
        </tr>
            <%     }
       } %>
    </table>
</div>
</body>
</html>