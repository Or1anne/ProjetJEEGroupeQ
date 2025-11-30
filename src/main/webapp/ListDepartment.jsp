<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>

<%@ page import="com.example.projetjeegroupeq.model.Department" %>
<%@ page import="com.example.projetjeegroupeq.util.PermissionChecker" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Liste des départements</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/CSS/style.css">
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

<%
    List<Department> departments = (List<Department>) request.getAttribute("departments");
    if (departments == null) {
        departments = java.util.Collections.emptyList();
    }
    String contextPath = request.getContextPath();

    String filterField = (String) request.getAttribute("filterField");
    String filterValue = (String) request.getAttribute("filterValue");
    String sortField = (String) request.getAttribute("sortField");
    String sortOrder = (String) request.getAttribute("sortOrder");
%>
<div class="hero-body">
    <h2>Liste des départements</h2>
    <%
        if (PermissionChecker.hasPermission(request, "/department", "add")) {
    %>
    <nav>
        <a class="btn" href="<%= contextPath %>/department?action=add">Ajouter un département</a>
    </nav>
    <%
        }
    %>

    <div class="filter-panel">
        <div class="filter-panel-header">
            <button type="button" class="filter-toggle-btn" data-target="department-filter-bar" onclick="window.toggleFilter('department-filter-bar', this);">
                <span class="filter-toggle-icon">🔍</span>
                <span class="filter-toggle-label">Recherche / tri</span>
            </button>
        </div>

        <form id="department-filter-bar" method="get" action="<%= contextPath %>/department" class="filter-bar">
            <input type="hidden" name="action" value="list"/>

            <label>Filtrer par nom :</label>
            <input type="hidden" name="filterField" value="name"/>
            <input type="text" name="filterValue" value="<%= filterValue != null ? filterValue : "" %>" placeholder="Nom contient..."/>

            <label>Trier par :</label>
            <select name="sortField">
                <option value="">-- Aucun --</option>
                <option value="id" <%= "id".equals(sortField) ? "selected" : "" %>>ID</option>
                <option value="name" <%= "name".equals(sortField) ? "selected" : "" %>>Nom</option>
            </select>
            <select name="sortOrder">
                <option value="asc" <%= sortOrder == null || "asc".equalsIgnoreCase(sortOrder) ? "selected" : "" %>>Croissant</option>
                <option value="desc" <%= "desc".equalsIgnoreCase(sortOrder) ? "selected" : "" %>>Décroissant</option>
            </select>

            <button type="submit">Appliquer</button>
            <a href="<%= contextPath %>/department?action=list" class="btn-reset">Réinitialiser</a>
        </form>
    </div>

    <table class="table">
        <thead>
        <tr>
            <th>ID</th>
            <th>Nom</th>
            <th>Chef de département</th>
            <%
                if (PermissionChecker.hasPermission(request, "/department", "edit") || PermissionChecker.hasPermission(request, "/department", "delete")) {
            %>
            <th>Action</th>
            <%
                }
            %>
        </tr>
        </thead>
        <tbody>
        <% if (departments.isEmpty()) { %>
        <tr>
            <td colspan="4">Aucun département enregistré.</td>
        </tr>
        <% } else {
            for (Department dep : departments) {
        %>
        <tr style="cursor:pointer"
            onclick="window.location.href='<%= contextPath %>/department?action=view&id=<%= dep.getId() %>'">
            <td><%= dep.getId() %></td>
            <td><%= dep.getDepartmentName() %></td>
            <td>
                <%= (dep.getChefDepartment() != null)
                        ? dep.getChefDepartment().getFirstName() + " " + dep.getChefDepartment().getLastName()
                        : "-" %>
            </td>

            <%
                if (PermissionChecker.hasPermission(request, "/department", "edit") || PermissionChecker.hasPermission(request, "/department", "delete")) {
            %>
            <td>
                <%
                    if (PermissionChecker.hasPermission(request, "/department", "edit")) {
                %>
                <a href="<%= contextPath %>/department?action=edit&id=<%= dep.getId() %>">Modifier</a>
                <%
                    }
                %>
                <%
                    if (PermissionChecker.hasPermission(request, "/department", "delete")) {
                %>
                |
                <a href="<%= contextPath %>/department?action=delete&id=<%= dep.getId() %>"
                   onclick="return confirm('Supprimer ce département ?');">Supprimer</a>
                <%
                    }
                %>
            </td>
            <%
                }
            %>
        </tr>
        <%     }
        } %>
        </tbody>
    </table>
</div>

<script type="text/javascript">
    (function initDepartmentFilter() {
        var formId = 'department-filter-bar';
        var form = document.getElementById(formId);
        var btn = document.querySelector('.filter-toggle-btn[data-target="' + formId + '"]');
        if (!form) return;
        updateFilterToggleState(form, btn);
        bindFilterFieldAutoSubmit(form);
    })();

    function bindFilterFieldAutoSubmit(form) {
        if (!form) return;
        var select = form.querySelector('select[name="filterField"]');
        if (!select) return;
        select.addEventListener('change', function () {
            form.submit();
        });
    }

    function updateFilterToggleState(form, btn) {
        if (!form) return;
        var isCollapsed = form.classList.contains('is-collapsed');
        form.setAttribute('aria-hidden', isCollapsed ? 'true' : 'false');
        if (btn) btn.setAttribute('aria-expanded', (!isCollapsed).toString());
        if (btn) {
            var iconSpan = btn.querySelector('.filter-toggle-icon');
            if (iconSpan) iconSpan.textContent = isCollapsed ? '🔍' : '➖';
        }
    }

    window.toggleFilter = function (id, btn) {
        var form = document.getElementById(id);
        if (!form) return;
        form.classList.toggle('is-collapsed');
        var targetBtn = btn || document.querySelector('.filter-toggle-btn[data-target="' + id + '"]');
        updateFilterToggleState(form, targetBtn);
    };
</script>
</body>
</html>