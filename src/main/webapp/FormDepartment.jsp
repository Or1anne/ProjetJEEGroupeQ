<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.example.projetjeegroupeq.model.Employee" %>
<%@ page import="com.example.projetjeegroupeq.model.Department" %>
<html>
<head>
    <title>Ajouter un département</title>
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

<%
    Department department = (Department) request.getAttribute("department");
    if (department == null) {
        department = new Department();
    }

    List<Employee> employees = (List<Employee>) request.getAttribute("employees");
    if (employees == null) {
        employees = java.util.Collections.emptyList();
    }

    String formMode = (String) request.getAttribute("formMode");
    boolean isEditMode = "edit".equalsIgnoreCase(formMode);

    String contextPath = request.getContextPath();
%>

<div class="hero-body">
    <nav>
        <a href="<%= request.getContextPath() %>/department?action=list">Liste des départements</a>
    </nav>
    <div class="form-container">
        <form action="<%= request.getContextPath() %>/department" method="post">
            <h2>Ajouter un département</h2>

            <!-- action à envoyer au servlet -->
            <input type="hidden" name="action" value="<%= isEditMode ? "edit" : "add" %>">

            <% if (isEditMode) { %>
            <!-- on envoie l'id du département en cas de modification -->
            <input type="hidden" name="id" value="<%= department.getId() %>">
            <% } %>

            <h2><%= isEditMode
                    ? "Modifier le département " + department.getDepartmentName()
                    : "Ajouter un département" %></h2>

            <p>
                <label for="departmentName">Nom du département</label>
                <input type="text"
                       id="departmentName"
                       name="departmentName"
                       value="<%= department.getDepartmentName() != null ? department.getDepartmentName() : "" %>"
                       required>
            </p>

            <p>
                <label for="chefId">Chef de département</label>
                <select name="chefId" id="chefId">
                    <option value="">-- Choisir un chef de département --</option>
                    <%
                        Integer currentChefId = (department.getChefDepartment() != null)
                                ? department.getChefDepartment().getId()
                                : null;

                        for (Employee e : employees) {
                            boolean selected = (currentChefId != null && currentChefId == e.getId());
                    %>
                    <option value="<%= e.getId() %>" <%= selected ? "selected" : "" %>>
                        <%= e.getFirstName() %> <%= e.getLastName() %>
                    </option>
                    <% } %>
                </select>
            </p>


            <input type="submit" value="Enregistrer">
        </form>
    </div>
</div>
</body>
</html>