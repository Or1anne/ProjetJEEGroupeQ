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
