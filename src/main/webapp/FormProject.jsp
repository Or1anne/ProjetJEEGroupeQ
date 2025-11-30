<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.example.projetjeegroupeq.model.Project" %>
<%@ page import="com.example.projetjeegroupeq.model.ProjectStatus" %>
<%@ page import="com.example.projetjeegroupeq.model.Employee" %>
<%@ page import="com.example.projetjeegroupeq.model.EmployeeProject" %>
<html>
<head>
    <title>Créer un projet</title>
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

            if (project == null) project = new Project();

            List<Employee> employees = (List<Employee>) request.getAttribute("employees");

            boolean isEditMode = "edit".equals(request.getAttribute("formMode"));
            String errorMessage = request.getParameter("errorMessage");
            String contextePath = request.getContextPath();
        %>
        <form action="<%= contextePath %>/project" method="post" class="employee-form">
            <h2><%= isEditMode ? "Modifier le projet" + project.getName_project() : "Créer un projet"%></h2>
            <input type="hidden" name="action" value="<%= isEditMode ? "edit" : "add"%>"/>

            <% if (isEditMode) {%>
                <input type="hidden" name="id" value="<%= project.getId()%>" />
            <% } %>

            <% if (errorMessage != null && !errorMessage.isBlank()) { %>
                <div class="error-message"><%= errorMessage %></div>
            <% } %>

            <p>
                <label for="projectName">Nom du projet</label>
                <input type="text" id="projectName" name="projectName" value="<%= project.getName_project() != null ? project.getName_project() : ""%>" required>
            </p>
            <p>
                <label for="projectStatus">Statut du projet</label>
                <select id="projectStatus" name="projectStatus" required>
                    <option value="">-- Choisir un statut --</option>
                    <% for (ProjectStatus p : ProjectStatus.values()) {
                        boolean selected = (project.getStatus() != null && project.getStatus() == p);
                    %>
                        <option value="<%= p.name()%>" <%= selected ? "selected" : ""%>>
                            <%= p.getTranslation() %>
                        </option>
                    <% } %>
                </select>
            </p>
            <p>
                <label for="managerId">Chef de projet</label>
                <%if (employees == null || employees.isEmpty()) {%>
                    <select id="managerId" name="managerId" disabled>
                        <option value="">Aucun employé disponible</option>
                    </select>
                    <small>Veuillez créer des employés avant d'assigner un chef de projet.</small>
                <% } else {%>
                    <select id="managerId" name="managerId" required>
                        <option value="">-- Choisir un chef de projet --</option>
                        <%
                            // Récupérer l'ID du chef de projet actuel pour la présélection
                            Integer currentChefId = null;
                            if (project.getChefProj() != null) {
                                currentChefId = project.getChefProj().getId();
                            }
                            for (Employee e : employees) {
                                // Vérifier si c'est le chef de projet actuel
                                boolean isCurrentChef = (currentChefId != null && currentChefId == e.getId());
                                boolean selected = isCurrentChef;

                                // Si ce n'est pas le chef actuel, vérifier s'il est membre du projet
                                boolean isProjectMember = false;
                                if (!isCurrentChef && project.getEmployees() != null) {
                                    for (EmployeeProject ee : project.getEmployees()) {
                                        if (ee.getEmployee() != null && ee.getEmployee().getId() == e.getId()) {
                                            isProjectMember = true;
                                            break;
                                        }
                                    }
                                }

                                // Si l'employé est déjà membre du projet (mais pas le chef actuel), on NE l'affiche PAS comme chef possible
                                // Le chef actuel doit toujours être affiché et présélectionné
                                if (isProjectMember && !isCurrentChef) {
                                    continue;
                                }
                        %>
                        <option value="<%= e.getId()%>" <%= selected ? "selected" : ""%>>
                            <%= e.getLastName() %> <%= e.getFirstName()%>
                        </option>
                        <% } %>
                    </select>
                <% } %>
            </p>

            <!--
            <label>Date de début :</label>
            <input type="date" name="startDate">

            <label>Date de fin prévue :</label>
            <input type="date" name="endDate">
            -->

            <input type="submit" value="Enregistrer">
        </form>
    </div>
</div>
</body>

</html>