<%--
  Created by IntelliJ IDEA.
  User: mathieurouet
  Date: 29/11/2025
  Time: 14:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.projetjeegroupeq.model.Project" %>
<%@ page import="com.example.projetjeegroupeq.model.Employee" %>
<%@ page import="com.example.projetjeegroupeq.model.EmployeeProject" %>
<html>
<head>
    <title>Affecter des employés à un projet</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/style.css">
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

<div class="hero-body">
    <div class="form-container">
        <%
            Project project = (Project) request.getAttribute("project");
            if (project == null) {
                project = new Project();
            }

            List<Employee> employees = (List<Employee>) request.getAttribute("employees");
            List<EmployeeProject> currentMembers = project.getEmployees();

            String errorMessage = (String) request.getAttribute("errorMessage");
            String contextPath = request.getContextPath();
        %>

        <form action="<%= contextPath %>/project" method="post" class="employee-form">
            <h2>Affecter des employés au projet <%= project.getName_project() != null ? project.getName_project() : "" %></h2>

            <!-- Action spécifique + id du projet -->
            <input type="hidden" name="action" value="addEmployees"/>
            <input type="hidden" name="id" value="<%= project.getId() %>"/>

            <% if (errorMessage != null && !errorMessage.isBlank()) { %>
            <div class="error-message"><%= errorMessage %></div>
            <% } %>

            <fieldset>
                <legend>Informations sur le projet</legend>
                <p>
                    <strong>Nom du projet :</strong>
                    <%= project.getName_project() != null ? project.getName_project() : "Non défini" %>
                </p>
                <p>
                    <strong>Chef de projet :</strong>
                    <%= (project.getChefProj() != null)
                            ? project.getChefProj().getLastName() + " " + project.getChefProj().getFirstName()
                            : "Non défini" %>
                </p>
                <p>
                    <strong>Statut :</strong>
                    <%= (project.getStatus() != null)
                            ? project.getStatus().getTranslation()
                            : "Non défini" %>
                </p>
            </fieldset>

            <fieldset>
                <legend>Liste des employés (cocher pour affecter)</legend>

                <% if (employees == null || employees.isEmpty()) { %>
                <p>Aucun employé disponible. Veuillez créer des employés avant de les affecter à un projet.</p>
                <% } else { %>
                <ul>
                    <%
                        for (Employee e : employees) {
                            boolean alreadyMember = false;
                            if (currentMembers != null) {
                                for (EmployeeProject ep : currentMembers) {
                                    if (ep.getEmployee() != null && ep.getEmployee().getId() == e.getId()) {
                                        alreadyMember = true;
                                        break;
                                    }
                                }
                            }

                            if (project.getChefProj() != null) {
                                if (e.getId() != project.getChefProj().getId()) {
                    %>
                    <li>
                        <label>
                            <input type="checkbox" name="employeeIds" value="<%= e.getId() %>" <%= alreadyMember ? "checked" : "" %> />
                            <%= e.getLastName() %> <%= e.getFirstName() %>
                        </label>
                    </li>
                    <% }} else { %>
                    <li>
                        <label>
                            <input type="checkbox" name="employeeIds" value="<%= e.getId() %>" <%= alreadyMember ? "checked" : "" %> />
                            <%= e.getLastName() %> <%= e.getFirstName() %>
                        </label>
                    </li>
                    <% } } %>
                </ul>
                <% } %>
            </fieldset>

            <p>
                <input type="submit" value="Enregistrer les affectations">
                <a href="<%= contextPath %>/project?action=list">Annuler</a>
            </p>
        </form>
    </div>
</div>
</body>
</html>