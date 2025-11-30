<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.example.projetjeegroupeq.model.Department" %>
<%@ page import="com.example.projetjeegroupeq.model.Employee" %>
<%@ page import="com.example.projetjeegroupeq.model.Grade" %>
<%@ page import="com.example.projetjeegroupeq.model.Role" %>
<html>
<head>
    <title>Formulaire de création d'un employé</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/style.css">
</head>
<body>
<div class="hero-head">
    <nav class="navbar">
        <div class="container">
            <div class="navbar-start">
                <a href="index.jsp" class="navbar-item">Accueil</a>
                <a href="Gestion.jsp" class="navbar-item">Gestion</a>
            </div>

            <div class="navbar-end">
                <%
                    // On récupère l’utilisateur connecté
                    Object user = session.getAttribute("loggedUser");

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
    <nav>
        <a href="employee">Liste des employés</a>
    </nav>
    <div class="form-container">
        <%
            Employee employee = (Employee) request.getAttribute("employee");
            if (employee == null) {
                employee = new Employee();
            }
            List<Department> departments = (List<Department>) request.getAttribute("departments");
            List<Role> roles = (List<Role>) request.getAttribute("roles");
            boolean isEditMode = "edit".equals(request.getAttribute("formMode"));
            String errorMessage = (String) request.getAttribute("errorMessage");
            Grade[] grades = (Grade[]) request.getAttribute("grades");
            String contextPath = request.getContextPath();
        %>
        <form action="<%= contextPath %>/employee" method="post" class="employee-form">
            <h2><%= isEditMode ? "Modifier " + employee.getLastName() + " " + employee.getFirstName() : "Ajouter un employé" %></h2>
            <input type="hidden" name="action" value="<%= isEditMode ? "edit" : "add" %>" />
            <% if (isEditMode) { %>
            <input type="hidden" name="id" value="<%= employee.getId() %>" />
            <% } %>
            <% if (errorMessage != null && !errorMessage.isBlank()) { %>
            <div class="error-message"><%= errorMessage %></div>
            <% } %>

            <p>
                <label for="lastname">Nom</label>
                <input type="text" id="lastname" name="lastname" value="<%= employee.getLastName() != null ? employee.getLastName() : "" %>" required>
            </p>
            <p>
                <label for="firstname">Prénom</label>
                <input type="text" id="firstname" name="firstname" value="<%= employee.getFirstName() != null ? employee.getFirstName() : "" %>" required>
            </p>
            <p>
                <label for="grade">Grade</label>
                <select name="grade" id="grade" required>
                    <option value="">-- Choisir un grade --</option>
                    <%
                        Grade currentGrade = employee.getGrade();

                        if (grades != null) {
                            for (Grade g : grades) {
                                boolean isSelected = (currentGrade != null && currentGrade == g);
                    %>
                    <option value="<%= g.name() %>" <%= isSelected ? "selected" : "" %>>
                        <%= g.getLabel() %>
                    </option>
                    <%
                            }
                        }
                    %>
                </select>
            </p>
            <p>
                <label for="role">Rôle</label>
                <% if (roles == null || roles.isEmpty()) { %>
                <select id="role" name="roleId" disabled>
                    <option value="">Aucun rôle disponible</option>
                </select>
                <small>Veuillez créer un rôle avant d'ajouter un employé.</small>
                <% } else { %>
                <select name="roleId" id="role" required>
                    <option value="">-- Choisir un rôle --</option>
                    <%
                        int currentRoleId = -1;
                        if (employee.getEmployeeRoles() != null && !employee.getEmployeeRoles().isEmpty()) {
                            currentRoleId = employee.getEmployeeRoles().get(0).getRole().getIdRole();
                        }
                        for (Role role : roles) {
                            boolean selected = currentRoleId == role.getIdRole();
                    %>
                    <option value="<%= role.getIdRole() %>" <%= selected ? "selected" : "" %>>
                        <%= role.getRoleName() %>
                    </option>
                    <% } %>
                </select>
                <% } %>
            </p>
            <p>
                <label for="post">Poste</label>
                <input type="text" id="post" name="post" value="<%= employee.getPost() != null ? employee.getPost() : "" %>">
            </p>
            <p>
                <label for="salary">Salaire</label>
                <input type="number" step="0.01" id="salary" name="salary" value="<%= employee.getSalary() != null ? employee.getSalary() : "" %>">
            </p>
            <p>
                <label for="department">Département</label>
                <% if (departments == null || departments.isEmpty()) { %>
                <select id="department" name="departmentId" disabled>
                    <option value="">Aucun département disponible</option>
                </select>
                <small>Veuillez créer un département avant d'ajouter un employé.</small>
                <% } else { %>
                <select name="departmentId" id="department" required>
                    <option value="">-- Choisir un département --</option>
                    <% for (Department dep : departments) {
                        boolean selected = employee.getDepartment() != null && employee.getDepartment().getId() == dep.getId();
                    %>
                    <option value="<%= dep.getId() %>" <%= selected ? "selected" : "" %>>
                        <%= dep.getDepartmentName() %>
                    </option>
                    <% } %>
                </select>
                <% } %>
            </p>

            <input type="submit" value="Enregistrer">
        </form>
    </div>
</div>
</body>
</html>