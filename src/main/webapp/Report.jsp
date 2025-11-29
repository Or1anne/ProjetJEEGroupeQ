<%--
  Created by IntelliJ IDEA.
  User: Cytech
  Date: 29/11/2025
  Time: 01:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.example.projetjeegroupeq.model.Department" %>
<%@ page import="com.example.projetjeegroupeq.model.Project" %>
<%@ page import="com.example.projetjeegroupeq.model.Grade" %>
<!DOCTYPE html>
<html>
<head>
    <title>Rapports & Statistiques</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/style.css">
</head>
<body>

<h1>Statistiques</h1>

<%
    Map<Department, Long> employeesByDept =
            (Map<Department, Long>) request.getAttribute("employeesByDept");
    Map<Project, Long> employeesByProject =
            (Map<Project, Long>) request.getAttribute("employeesByProject");
    Map<Grade, Long> employeesByGrade =
            (Map<Grade, Long>) request.getAttribute("employeesByGrade");
%>

<!-- 1. Nb d’employés par département -->
<h2>Nombre d’employés par département</h2>
<table border="1">
    <tr>
        <th>Département</th>
        <th>Nombre d’employés</th>
    </tr>
    <%
        if (employeesByDept != null) {
            for (Map.Entry<Department, Long> entry : employeesByDept.entrySet()) {
    %>
    <tr>
        <td><%= entry.getKey().getDepartmentName() %></td>
        <td><%= entry.getValue() %></td>
    </tr>
    <%
            }
        }
    %>
</table>

<!-- 2. Nb d’employés par projet -->
<h2>Nombre d’employés par projet</h2>
<table border="1">
    <tr>
        <th>Projet</th>
        <th>Nombre d’employés</th>
    </tr>
    <%
        if (employeesByProject != null) {
            for (Map.Entry<Project, Long> entry : employeesByProject.entrySet()) {
    %>
    <tr>
        <td><%= entry.getKey().getProjectName() %></td>
        <td><%= entry.getValue() %></td>
    </tr>
    <%
            }
        }
    %>
</table>

<!-- 3. Nb d’employés par grade -->
<h2>Nombre d’employés par grade</h2>
<table border="1">
    <tr>
        <th>Grade</th>
        <th>Nombre d’employés</th>
    </tr>
    <%
        if (employeesByGrade != null) {
            for (Map.Entry<Grade, Long> entry : employeesByGrade.entrySet()) {
    %>
    <tr>
        <td><%= entry.getKey() %></td>
        <td><%= entry.getValue() %></td>
    </tr>
    <%
            }
        }
    %>
</table>

</body>
</html>
