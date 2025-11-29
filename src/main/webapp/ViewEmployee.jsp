<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.projetjeegroupeq.model.Employee" %>
<%@ page import="com.example.projetjeegroupeq.model.Grade" %>
<%@ page import="com.example.projetjeegroupeq.model.EmployeeRole" %>
<%@ page import="com.example.projetjeegroupeq.model.EmployeeProject" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <title>View Employee</title>
    <link rel="stylesheet" href="CSS/style.css" />
</head>
<body>
<div class="hero-head">
    <nav class="navbar">
        <div class="container">
            <div class="navbar-start">
                <a href="index.jsp" class="navbar-item">Accueil</a>
                <%
                    // On récupère l’utilisateur connecté
                    Employee employee = (Employee) request.getAttribute("employee");

                    if (employee != null) {
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
                    if (employee != null) {
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


<section class="hero-body">

    <div class="card">
        <h2>Informations Personnelles</h2>
        <ul>
            <li><strong>Nom :</strong> ${employee.lastName}</li>
            <li><strong>Prénom :</strong> ${employee.firstName}</li>
            <li><strong>Grade :</strong> ${employee.grade.label}</li>
            <li><strong>Poste :</strong> ${employee.post}</li>
            <li><strong>Salaire :</strong> ${employee.salary}€</li>
            <li><strong>Nom d'utilisateur :</strong> ${employee.username}</li>
        </ul>

        <h3>Département :</h3>
        <p>
            ${employee.department != null ? employee.department.departmentName : "Non assigné"}
        </p>

        <h3>Rôle :</h3>
        <%
            if (employee.getEmployeeRoles() == null || employee.getEmployeeRoles().isEmpty()) {
        %>
        <ul>Aucun</ul>
        <%
        } else {
            for (EmployeeRole er : employee.getEmployeeRoles()) {
        %>
        <ul><%= er.getRole().getRoleName() %></ul>
        <%
                }
            }
        %>
        <h3>Projets :</h3>
        <ul>
            <%
                if (employee.getProjects() == null || employee.getProjects().isEmpty()) {
            %>
            <li>Aucun</li>
            <%
            } else {
                for (EmployeeProject ep : employee.getProjects()) {
            %>
            <li><%= ep.getProject().getName_project() %></li>
            <%
                    }
                }
            %>
        </ul>

        <div class="form-action" style="display:flex;gap:10px;margin-top:12px;">
            <a class="button" href="employee?action=edit&id=${employee.id}">Modifier</a>
            <a class="button" href="employee">Retour</a>
            <a class="button" href="<%= request.getContextPath() %>/pay?action=list&employeeId=${employee.id}">Historique des paies</a>
        </div>
    </div>
</section>

</body>
</html>
