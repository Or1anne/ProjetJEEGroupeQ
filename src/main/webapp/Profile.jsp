<%@ page import="com.example.projetjeegroupeq.model.Employee" %>
<%@ page import="com.example.projetjeegroupeq.model.Grade" %>
<%@ page import="com.example.projetjeegroupeq.model.EmployeeRole" %>
<%@ page import="com.example.projetjeegroupeq.model.EmployeeProject" %>
<%@ page import="com.example.projetjeegroupeq.dao.implementation.EmployeeDAO" %>

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
                    // Recharger les données de l'employé depuis la base de données pour avoir les infos à jour
                    EmployeeDAO employeeDAO = new EmployeeDAO();
                    Employee emp = employeeDAO.searchById(user.getId());
                    // Mettre à jour l'objet en session avec les données fraîches
                    session.setAttribute("loggedUser", emp);

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
                <li><strong>Nom :</strong> <%= emp.getLastName() != null ? emp.getLastName() : "" %> </li>
                <li><strong>Prénom :</strong> <%= emp.getFirstName()%> </li>
                <li><strong>Grade :</strong>  <%= emp.getGrade() != null ? emp.getGrade().getLabel() : "" %>
                <li><strong>Poste :</strong> <%= emp.getPost()%> </li>
                <li><strong>Salaire :</strong> <%= emp.getSalary()%> €</li>
                <li><strong>Département :</strong> <%= emp.getDepartment() != null ? emp.getDepartment().getDepartmentName() : "Aucun" %> </li>
                <li><strong>Projet(s) :</strong>
                    <ul>
                        <%
                            if (emp.getProjects() == null || emp.getProjects().isEmpty()) {
                        %>
                        <li>Aucun</li>
                        <%
                        } else {
                            for (EmployeeProject ep : user.getProjects()) {
                        %>
                        <li><%= ep.getProject().getName_project() %>
                            <small>(<%= ep.getProject().getStatus().getTranslation() %>)</small>
                        </li>
                        <%
                                }
                            }
                        %>
                    </ul>
                </li>
            </ul>
            <h2>Informations du compte</h2>
            <ul>
                <li><strong>Nom d'utilisateur :</strong> <%= emp.getUsername() != null ? emp.getUsername() : "" %></li>
                <li><strong>Rôle :</strong>
                        <%
                        if (emp.getEmployeeRoles() == null || emp.getEmployeeRoles().isEmpty()) {
                        %>
                        <li>Aucun</li>
                        <%
                        } else {
                            for (EmployeeRole er : emp.getEmployeeRoles()) {
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