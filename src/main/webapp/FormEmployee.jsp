<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.example.projetjeegroupeq.model.Department" %>
<%@ page import="com.example.projetjeegroupeq.model.Employee" %>
<!DOCTYPE html>
<html>
<head>
    <title>Gestion des employés</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/style.css">
</head>
<body>
<div class="form-container">
    <%
        Employee employee = (Employee) request.getAttribute("employee");
        if (employee == null) {
            employee = new Employee();
        }
        List<Department> departments = (List<Department>) request.getAttribute("departments");
        boolean isEditMode = "edit".equals(request.getAttribute("formMode"));
        String errorMessage = (String) request.getAttribute("errorMessage");
        String gradeValue = employee.getGrade() != null ? employee.getGrade().toLowerCase() : "";
        String contextPath = request.getContextPath();
    %>

    <form action="<%= contextPath %>/employee" method="post" class="employee-form">
        <h2><%= isEditMode ? "Modifier un employé" : "Ajouter un employé" %></h2>

        <input type="hidden" name="action" value="<%= isEditMode ? "edit" : "add" %>" />
        <% if (isEditMode) { %>
            <input type="hidden" name="id" value="<%= employee.getId() %>" />
        <% } %>

        <% if (errorMessage != null && !errorMessage.isBlank()) { %>
            <div class="error-message"><%= errorMessage %></div>
        <% } %>

        <p>
            <label for="lastName">Nom</label>
            <input type="text" id="lastName" name="lastName" value="<%= employee.getLastName() != null ? employee.getLastName() : "" %>" required>
        </p>

        <p>
            <label for="firstName">Prénom</label>
            <input type="text" id="firstName" name="firstName" value="<%= employee.getFirstName() != null ? employee.getFirstName() : "" %>" required>
        </p>

        <p>
            <label for="grade">Grade</label>
            <select name="grade" id="grade" required>
                <option value="">-- Choisir un grade --</option>
                <option value="cadre" <%= "cadre".equals(gradeValue) ? "selected" : "" %>>Cadre</option>
                <option value="stagiaire" <%= "stagiaire".equals(gradeValue) ? "selected" : "" %>>Stagiaire</option>
            </select>
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
</body>
</html>
