<%@ page import="com.example.projetjeegroupeq.model.Project" %>
<%@ page import="com.example.projetjeegroupeq.model.Employee" %>
<%@ page import="com.example.projetjeegroupeq.model.EmployeeProject" %>
<%@ page import="java.util.List" %>
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
                <%
                    // On récupère l’utilisateur connecté
                    Object user = session.getAttribute("loggedUser");

                    if (user != null) {
                        // Si connecté, on affiche Recherche et Gestion
                %>
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
    <%
        Project project = (Project) request.getAttribute("project");
        List<EmployeeProject> members = project != null ? project.getEmployees() : null;
    %>

    <div class="card">
        <header>
            <h2>Détails de <%= project.getName_project() %></h2>
        </header>

        <section>
            <h3>Informations générales</h3>
            <ul>
                <li>
                    <strong>Chef de projet :</strong>
                    <%= project.getChefProj() != null
                            ? project.getChefProj().getLastName() + " " + project.getChefProj().getFirstName()
                            : "Non défini" %>
                </li>
                <li>
                    <strong>Statut :</strong>
                    <%= project.getStatus() != null
                            ? project.getStatus().getTranslation()
                            : "Non défini" %>
                </li>
            </ul>
        </section>

        <section>
            <h3>Membres de l'équipe</h3>
            <%
                if (members != null && !members.isEmpty()) {
            %>
            <ul>
                <% for (EmployeeProject ep : members) { %>
                <li>
                    <%= ep.getEmployee().getLastName() %>
                    <%= ep.getEmployee().getFirstName() %>
                </li>
                <% } %>
            </ul>
            <%
            } else {
            %>
            <p>Aucun employé n’est actuellement affecté à ce projet.</p>
            <%
                }
            %>
        </section>
    </div>
</div>
</body>
</html>
