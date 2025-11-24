<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
    <h2>Liste des fiches de paie</h2>
    <nav>
        <a href="FormPay.jsp">Créer une fiche de paie</a>
    </nav>

    <table class="table">
        <thead>
        <tr>
            <th>ID</th>
            <th>Employé</th>
            <th>Mois</th>
            <th>Net à payer (€)</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <!-- Exemple -->
        <!-- TODO Mettre les fiches de paie en dynamique lié à un unique employé-->
        <tr onclick="window.location.href='ViewPay.jsp?id=1'">
            <td>PS001</td>
            <td>Claire Durand</td>
            <td>2025-09</td>
            <td>2850.00</td>
            <td>
                <a href="">Imprimer</a> | <!-- TODO Lien vers génération PDF -->
                <a href="">Supprimer</a>
            </td>
        </tr>
        </tbody>
    </table>
</div>
</body>
</html>