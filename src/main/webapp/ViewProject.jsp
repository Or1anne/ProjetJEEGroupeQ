<%@ page import="com.example.projetjeegroupeq.model.Project" %>
<%@ page import="com.example.projetjeegroupeq.model.Employee" %>
<%@ page import="com.example.projetjeegroupeq.model.EmployeeProject" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Projet</title>
    <link rel="stylesheet" href="CSS/style.css" />
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
<div class="hero-body">
    <%
        Project project = (Project) request.getAttribute("project");
    %>

    <div class="card">
        <h2>Détails de <%=project.getName_project()%></h2>
        <li><strong>Chef de projet :</strong> <%=project.getChefProj().getLastName()%> <%=project.getChefProj().getFirstName()%></li>
        <li><strong>Statut :</strong> <%=project.getStatus().getTranslation()%>></li>
        <h4>Membres de l'équipe : </h4>
        <ul>
            <% for (EmployeeProject e : project.getEmployees()) { %>
            <li><%=e.getEmployee().getLastName()%> <%=e.getEmployee().getFirstName()%></li>
            <% } %>
        </ul>
    </div>

</div>
</body>
</html>
