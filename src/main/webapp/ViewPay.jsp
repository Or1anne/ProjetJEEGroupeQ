<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Détail de la fiche de paie</title>
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

    <div class="card">
        <h2>Fiche de paie - Claire Durand (2025-09)</h2> <!-- TODO Mettre en dynamique -->
        <table class="table">
            <tr><th>Salaire de base :</th><td>2500.00 €</td></tr>
            <tr><th>Primes :</th><td>400.00 €</td></tr>
            <tr><th>Déductions :</th><td>50.00 €</td></tr>
            <tr><th><strong>Net à payer :</strong></th><td><strong>2850.00 €</strong></td></tr>
        </table>

        <section class="signature">
            <p>Fait à Paris, le 30 septembre 2025</p>
            <p><em>Signature de l’employé</em> : _______________________</p>
        </section>

        <div class="form-action" style="display:flex;gap:10px;margin-top:12px;">
            <a href="ListPay.jsp">Retour</a>
            <button type="button" onclick="window.print()">Imprimer</button> <!-- TODO Lien vers génération PDF actuellement il fait juste ctrl+p -->
        </div>
    </div>


</div>
</body>
</html>