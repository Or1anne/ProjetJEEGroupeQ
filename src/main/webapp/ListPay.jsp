<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.example.projetjeegroupeq.model.Pay" %>
<%@ page import="com.example.projetjeegroupeq.model.Employee" %>

<%
    // Récupération de la liste envoyée par le Servlet
    List<Pay> pays = (List<Pay>) request.getAttribute("pays");
    // Récupération de l'employé filtré (peut être null si on affiche tout le monde)
    Employee currentEmployee = (Employee) request.getAttribute("currentEmployee");
%>


<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Liste des fiches de paie</title>
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
    <h2><% if (currentEmployee != null) { %>
        Historique des paies de <%= currentEmployee.getFirstName() %> <%= currentEmployee.getLastName() %>
        <% } else { %>
        Liste globale des fiches de paie
        <% } %></h2>
    <nav>
        <% if (currentEmployee != null) { %>
        <a href="<%= request.getContextPath() %>/pay?action=add&employeeId=<%= currentEmployee.getId() %>">
            Créer une fiche de paie
        </a>
        <% } else { %>
        <a href="<%= request.getContextPath() %>/pay?action=add">Créer une fiche de paie</a>
        <% } %>
    </nav>

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
                <a href="#">Imprimer</a> |
                <a href="<%= request.getContextPath() %>/pay?action=delete&payId=<%= p.getId() %>"
                   onclick="return confirm('Supprimer cette fiche ?');">Supprimer</a>
            </td>
        </tr>
        <%
                }
            }
        %>
        </tbody>
    </table>
</div>
</body>
</html>