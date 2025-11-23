<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Collections" %>
<%@ page import="com.example.projetjeegroupeq.model.Employee" %>
<%@ page import="com.example.projetjeegroupeq.model.Department" %>
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
                <a href="Search.jsp" class="navbar-item">Recherche</a>
                <a href="Gestion.jsp" class="navbar-item">Gestion</a>
            </div>

            <div class="navbar-end">
                <a href="Profile.jsp" class="navbar-item">Profil</a>
                <a href="FormConnection.jsp" class="navbar-item">Logout</a>
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
%>

<div class="hero-body">
    <h2>Liste des employés</h2>
    <nav>
        <a class="btn" href="<%= contextPath %>/employee?action=add">Ajouter un employé</a>
    </nav>

    <table class="table">
        <thead>
        <tr>
            <th>Nom</th>
            <th>Prénom</th>
            <th>Grade</th>
            <th>Poste</th>
            <th>Département</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <!-- Exemple statique -->
        <!-- TODO Mettre les employés en dynamique -->
        <tr onclick="window.location.href='ViewEmployee.jsp?id=1'">
            <td>Durand</td>
            <td>Claire</td>
            <td>Cadre</td>
            <td>Développeuse</td>
            <td>Informatique</td>
            <td>
                <a href="FormModifyEmployee.jsp">Modifier</a> |
                <a href="DeleteEmployee.jsp">Supprimer</a> <!-- TODO mettre un bouton et non un lien hypertexte-->
            </td>
        </tr>
        <tr>
            <td>Durand</td>
            <td>Claire</td>
            <td>Cadre</td>
            <td>Développeuse</td>
            <td>Informatique</td>
            <td>
                <a href="FormModifyEmployee.jsp">Modifier</a> |
                <a href="DeleteEmployee.jsp">Supprimer</a> <!-- TODO mettre un bouton et non un lien hypertexte-->
            </td>
        </tr>
        <% if (employees.isEmpty()) { %>
            <tr>
                <td colspan="6">Aucun employé enregistré.</td>
            </tr>
        <% } else {
               for (Employee employee : employees) {
                   Department department = employee.getDepartment();
        %>
            <tr>
                <td><%= employee.getLastName() != null ? employee.getLastName() : "" %></td>
                <td><%= employee.getFirstName() != null ? employee.getFirstName() : "" %></td>
                <td><%= employee.getGrade() != null ? employee.getGrade() : "" %></td>
                <td><%= employee.getPost() != null ? employee.getPost() : "" %></td>
                <td><%= department != null ? department.getDepartmentName() : "-" %></td>
                <td>
                    <a href="<%= contextPath %>/employee?action=edit&id=<%= employee.getId() %>">Modifier</a> |
                    <a href="<%= contextPath %>/employee?action=delete&id=<%= employee.getId() %>"
                       onclick="return confirm('Supprimer cet employé ?');">Supprimer</a>
                </td>
            </tr>
        <%     }
           } %>
        </tbody>
    </table>
</div>
</body>
</html>
