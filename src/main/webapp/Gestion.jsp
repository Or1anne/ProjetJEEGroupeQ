<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.example.projetjeegroupeq.model.Employee" %>
<%@ page import="com.example.projetjeegroupeq.util.PermissionChecker" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Tableau de bord</title>
    <link rel="stylesheet" href="CSS/style.css">
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
                    Employee user = (Employee) session.getAttribute("loggedUser");

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
    <header>
        <h2>Tableau de bord</h2>
    </header>

    <main class="container">
        <section class="dashboard-grid">

            <!-- === Employés === -->
            <div class="card">
                <h2>Employés</h2>
                <ul>
                    <li><a href="${pageContext.request.contextPath}/employee">Liste des employés</a></li>
                    <%
                        if (PermissionChecker.hasPermission(request, "/employee", "add")) {
                    %>
                    <li><a href="${pageContext.request.contextPath}/employee?action=add">Ajouter un employé</a></li>
                    <%
                        }
                    %>
                </ul>
            </div>

            <!-- === Départements === -->
            <div class="card">
                <h2>Départements</h2>
                <ul>
                    <li><a href="${pageContext.request.contextPath}/department">Liste des départements</a></li>
                    <%
                        // Vérifier les permissions via PermissionChecker (utilise PermissionConfig)
                        if (PermissionChecker.hasPermission(request, "/department", "add")) {
                    %>
                    <li><a href="${pageContext.request.contextPath}/department?action=add">Créer un département</a></li>
                    <%
                        }
                    %>
                </ul>
            </div>

            <!-- === Projets === -->
            <div class="card">
                <h2>Projets</h2>
                <ul>
                    <li><a href="${pageContext.request.contextPath}/project">Liste des projets</a></li>
                    <%
                        // Vérifier les permissions via PermissionChecker (utilise PermissionConfig)
                        if (PermissionChecker.hasPermission(request, "/project", "add")) {
                    %>
                    <li><a href="${pageContext.request.contextPath}/project?action=add">Créer un projet</a></li>
                    <%
                        }
                    %>
                </ul>
            </div>

            <!-- === Fiches de paies === -->
            <div class="card">
                <h2>Fiches de paie</h2>
                <ul>
                    <%
                        // Afficher "Liste des fiches de paie" seulement pour RH et ADMIN
                        if (PermissionChecker.hasRole(request, "RH", "ADMIN")) {
                    %>
                    <li><a href="${pageContext.request.contextPath}/pay">Liste des fiches de paie</a></li>
                    <%
                        }
                    %>
                    <li><a href="${pageContext.request.contextPath}/pay?action=list&employeeId=<%=user.getId()%>">Mes fiches de paie</a></li>
                </ul>
            </div>
        </section>

        <!-- === Rapports / Statistiques === -->
        <%
            if (PermissionChecker.hasPermission(request, "/report", "")) {
        %>
        <section class="dashboard-grid">

        <div class="card">
            <h2>Rapports / Statistiques</h2>
            <ul>
                <li><a href="${pageContext.request.contextPath}/report">Voir les rapports</a></li>
            </ul>
        </div>
        </section>
        <%
            }
        %>

    </main>
</div>
</body>
</html>