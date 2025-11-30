<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.example.projetjeegroupeq.model.Pay" %>
<%@ page import="com.example.projetjeegroupeq.model.Employee" %>
<%@ page import="com.example.projetjeegroupeq.util.PermissionChecker" %>
<%@ page import="com.example.projetjeegroupeq.model.Employee" %>
<%@ page import="com.example.projetjeegroupeq.util.PermissionChecker" %>

<%
    List<Pay> pays = (List<Pay>) request.getAttribute("pays");
    Employee currentEmployee = (Employee) request.getAttribute("currentEmployee");

    String contextPath = request.getContextPath();
    String filterField = (String) request.getAttribute("filterField");
    String filterValue = (String) request.getAttribute("filterValue");
    String sortField = (String) request.getAttribute("sortField");
    String sortOrder = (String) request.getAttribute("sortOrder");
%>


<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Liste des fiches de paie</title>
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
<div class="hero-body">
    <h2><% if (currentEmployee != null) { %>
        Historique des paies de <%= currentEmployee.getFirstName() %> <%= currentEmployee.getLastName() %>
        <% } else { %>
        Liste globale des fiches de paie
        <% } %></h2>
    <nav>
        <%
            // Seuls RH et ADMIN peuvent créer des fiches de paie
            if (PermissionChecker.hasPermission(request, "/pay", "add")) {
                if (currentEmployee != null) {
        %>
        <a href="<%= request.getContextPath() %>/pay?action=add&employeeId=<%= currentEmployee.getId() %>">
            Créer une fiche de paie
        </a>
        <%
                } else {
        %>
        <a href="<%= request.getContextPath() %>/pay?action=add">Créer une fiche de paie</a>
        <%
                }
            }
        %>
    </nav>

    <div class="filter-panel">
        <div class="filter-panel-header">
            <button type="button" class="filter-toggle-btn" data-target="pay-filter-bar" onclick="window.toggleFilter('pay-filter-bar', this);">
                <span class="filter-toggle-icon">🔍</span>
                <span class="filter-toggle-label">Recherche / tri</span>
            </button>
        </div>

        <form id="pay-filter-bar" method="get" action="<%= contextPath %>/pay" class="filter-bar">
            <input type="hidden" name="action" value="list"/>
            <% if (currentEmployee != null) { %>
                <input type="hidden" name="employeeId" value="<%= currentEmployee.getId() %>"/>
            <% } %>

            <label>Filtrer par mois :</label>
            <input type="hidden" name="filterField" value="month"/>
            <input type="month" name="filterValue" value="<%= filterValue != null ? filterValue : "" %>"/>

            <label>Trier par :</label>
            <select name="sortField">
                <option value="">-- Aucun --</option>
                <option value="date" <%= "date".equals(sortField) ? "selected" : "" %>>Date</option>
                <option value="net" <%= "net".equals(sortField) ? "selected" : "" %>>Net à payer</option>
            </select>
            <select name="sortOrder">
                <option value="asc" <%= sortOrder == null || "asc".equalsIgnoreCase(sortOrder) ? "selected" : "" %>>Croissant</option>
                <option value="desc" <%= "desc".equalsIgnoreCase(sortOrder) ? "selected" : "" %>>Décroissant</option>
            </select>

            <button type="submit">Appliquer</button>
            <% if (currentEmployee != null) { %>
                <a href="<%= contextPath %>/pay?action=list&employeeId=<%= currentEmployee.getId() %>" class="btn-reset">Réinitialiser</a>
            <% } else { %>
                <a href="<%= contextPath %>/pay?action=list" class="btn-reset">Réinitialiser</a>
            <% } %>
        </form>
    </div>

    <table class="table">
        <thead>
        <!--<tr>
            <th>ID</th>
            <th>Primes</th>
            <th>Déductions</th>
            <th>Net à payer (€)</th>
            <th>Actions</th>
        </tr>-->
        <tr>
            <th>ID</th>
            <% if (currentEmployee == null) { %>
            <th>Employé</th> <!-- On affiche la colonne Employé seulement en vue globale -->
            <% } %>
            <th>Date</th>
            <th>Net à payer (€)</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <%
            if (pays == null || pays.isEmpty()) {
        %>
        <tr>
            <td colspan="6" style="text-align:center;">Aucune fiche de paie trouvée.</td>
        </tr>
        <%
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            for (Pay p : pays) {
        %>
        <tr style="cursor:pointer" onclick="window.location.href='<%= request.getContextPath() %>/pay?action=view&payId=<%= p.getId() %>'">
            <td><%= p.getId() %></td>
            <% if (currentEmployee == null) { %>
            <td>
                <% if (p.getEmployee() != null) { %>
                <%= p.getEmployee().getFirstName() %> <%= p.getEmployee().getLastName() %>
                <% } else { %>
                -
                <% } %>
            </td>
            <% } %>
            <td><%= p.getDate() != null ? sdf.format(p.getDate()) : "-" %></td>


            <!--<td style="color: green;">+ <%= String.format("%.2f", p.getBonus()) %> €</td>
            <td style="color: red;">- <%= String.format("%.2f", p.getDeductions()) %> €</td> -->
            <td><strong><%= String.format("%.2f", p.getSalary_net()) %> €</strong></td>
            <td>
                <a href="<%= request.getContextPath() %>/pay?action=pdf&payId=<%= p.getId() %>">Imprimer</a>
                <%
                    // Seul ADMIN peut supprimer des fiches de paie
                    if (PermissionChecker.hasRole(request, "ADMIN")) {
                %>
                | <a href="<%= request.getContextPath() %>/pay?action=delete&payId=<%= p.getId() %><%= currentEmployee != null ? "&employeeId=" + currentEmployee.getId() : "" %>"
                   onclick="return confirm('Supprimer cette fiche ?');">Supprimer</a>
                <%
                    }
                %>
            </td>
        </tr>
        <%
                }
            }
        %>
        </tbody>
    </table>
</div>

<script type="text/javascript">
    (function initPayFilter() {
        var formId = 'pay-filter-bar';
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