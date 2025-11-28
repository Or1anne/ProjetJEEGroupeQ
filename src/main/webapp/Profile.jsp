<%@ page import="com.example.projetjeegroupeq.model.Employee" %>
<%@ page import="com.example.projetjeegroupeq.model.Grade" %>
<%@ page import="com.example.projetjeegroupeq.model.EmployeeRole" %>
<%@ page import="com.example.projetjeegroupeq.model.EmployeeProject" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Profile</title>
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
                    Employee user = (Employee) session.getAttribute("loggedUser");

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


<section class="hero-body">
    <nav>
        <a href="changePassword.jsp">Changer le mot de passe</a>
    </nav>
        <div class="card">
            <h2>Informations Personnelles</h2>
            <ul>
                <li><strong>Nom :</strong> <%= user.getLastName() != null ? user.getLastName() : "" %> </li>
                <li><strong>Prénom :</strong> <%= user.getFirstName()%> </li>
                <li><strong>Grade :</strong> ${user.grade.label}</li>
                <li><strong>Poste :</strong> <%= user.getPost()%> </li>
                <li><strong>Salaire :</strong> <%= user.getSalary()%> €</li>
                <li><strong>Département :</strong> <%= user.getDepartment() != null ? user.getDepartment().getDepartmentName() : "Aucun" %> </li>
                <li><strong>Projet(s) :</strong>
                    <ul>
                        <%
                            if (user.getProjects() == null || user.getProjects().isEmpty()) {
                        %>
                        <li>Aucun</li>
                        <%
                        } else {
                            for (EmployeeProject ep : user.getProjects()) {
                        %>
                        <li><%= ep.getProject().getName_project() %></li>
                        <%
                                }
                            }
                        %>
                    </ul>
                </li>
            </ul>
            <h2>Informations du compte</h2>
            <ul>
                <li><strong>Nom d'utilisateur :</strong> <%= user.getUsername() != null ? user.getUsername() : "" %></li>
                <li><strong>Rôle :</strong>
                        <%
                        if (user.getEmployeeRoles() == null || user.getEmployeeRoles().isEmpty()) {
                        %>
                        <li>Aucun</li>
                        <%
                        } else {
                            for (EmployeeRole er : user.getEmployeeRoles()) {
                        %>
                        <li><%= er.getRole().getRoleName() %></li>
                        <%
                                }
                            }
                        %>
                </li>
            </ul>
        </div>
</section>
</body>
</html>