<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Suivi du projet</title>
    <link rel="stylesheet" href="CSS/style.css">
</head>
<body>
<div class="hero-head">
    <nav class="navbar">
        <div class="container">
            <div class="navbar-start">
                <a href="index.jsp" class="navbar-item">Accueil</a>
                <a href="Search.jsp" class="navbar-item">Recherche</a>
                <a href="Gestion.jsp" class="navbar-item">Gestion</a>
            </div>

            <div class="navbar-end">
                <a href="Profile.jsp" class="navbar-item">Profil</a>
                <a href="FormConnection.jsp" class="navbar-item">Logout</a>
            </div>
        </div>
    </nav>
</div>
<div class="hero-body">
    <header>
        <h2>Suivi du projet : "NOM DU PROJET"</h2> <!-- Remplacer "NOM DU PROJET" par le nom réel du projet -->
        <nav>
            <a href="ListProject.jsp">Retour à la liste</a>
        </nav>
    </header>

    <main class="container">
        <section>
            <h2>Détails</h2>
            <p><strong>Chef de projet :</strong> Lucas Martin</p>
            <p><strong>État actuel :</strong> En cours</p>
            <p><strong>Progression :</strong> 60%</p>
        </section>

        <form action="UpdateProjectState" method="post" class="form">
            <label>Modifier l’état du projet :</label>
            <select name="state">
                <option>En cours</option>
                <option>Terminé</option>
                <option>Annulé</option>
            </select>

            <label>Commentaires :</label>
            <textarea name="comments" placeholder="Saisir un commentaire..."></textarea>

            <button type="submit">Mettre à jour</button>
        </form>
    </main>
</div>

</body>
</html>