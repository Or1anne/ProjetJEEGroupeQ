<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Projet</title>
    <link rel="stylesheet" href="CSS/style.css" />
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

    <div class="card">
        <h2>Détails du "Projet"</h2> <!-- TODO Remplacer par le nom réel du projet -->
        <p><strong>Chef de projet :</strong> Jacky Smith</p> <!-- TODO Remplacer par le nom réel du chef de projet -->
        <p><strong>Description : </strong>Ceci est une description détaillée du projet exemple.</p> <!-- TODO Remplacer par la description réelle du projet -->
        <p><strong>Statut :</strong> En cours</p> <!-- TODO Remplacer par le statut réel du projet -->
        <h4>Membres de l'équipe : </h4>
        <ul>
            <li>Employé 1</li>  <!-- TODO Remplacer par les noms réels des employés -->
            <li>Employé 2</li>
            <li>Employé 3</li>
        </ul>
    </div>

</div>
</body>
</html>
