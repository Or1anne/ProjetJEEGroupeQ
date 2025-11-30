<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.example.projetjeegroupeq.model.Employee" %>
<%@ page import="com.example.projetjeegroupeq.dao.implementation.EmployeeDAO" %>
<!DOCTYPE html>
<html>
<head>
    <title>Artic</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/style.css">
</head>
<body>
    <section>
        <div class="hero-head">
            <nav class="navbar">
                <div class="container">
                    <div class="navbar-start">
                        <a href="index.jsp" class="navbar-item">Accueil</a>
                        <%
                            // On récupère l’utilisateur connecté
                            Employee user = (Employee) session.getAttribute("loggedUser");

                            Employee emp = null;

                            if (user != null) {
                                EmployeeDAO employeeDAO = new EmployeeDAO();
                                emp = employeeDAO.searchById(user.getId());
                                session.setAttribute("loggedUser", emp);
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


        <div class="hero-body">
            <h2>Accueil</h2>
        </div>

        <!-- Message si connecté -->
        <%
            if (user != null) {
        %>
        <div class="login-status-message connected-message">
            <p class="welcome-text">
                Bienvenue <strong><%= emp.getFirstName() + " " + emp.getLastName() %></strong> !
            </p>

            <div class="grade-info">
                <p>Grade : <span><%= emp.getGrade().getLabel() %></span></p>
            </div>
        </div>

        <%
        } else {
        %>

        <!-- Message si non connecté -->
        <div class="login-status-message not-connected-message">
            <strong>Vous n'êtes pas connecté</strong>
            <p>Veuillez vous connecter pour accéder à l'application</p>
            <a href="<%= request.getContextPath() %>/login">Se connecter</a>
        </div>

        <%
            }
        %>
    </section>
</body>
</html>