<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Profile</title>
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

<section class="hero-body">
    <nav>
        <a href="editProfile.jsp">Modifier le profil</a>
        <a href="changePassword.jsp">Changer le mot de passe</a>
    </nav>
        <div class="card">
            <h2>Informations Personnelles</h2>
            <ul>
                <li>Nom: </li>
                <li>Prénom: </li>
                <li>Grade: </li>
                <li>Poste: </li>
                <li>Salaire:</li>
                <li>Département: </li>
                <li>Projet(s):</li>
            </ul>
            <h2>Informations du compte</h2>
            <ul>
                <li>Nom d'utilisateur: </li>
                <li>Rôle: </li>
            </ul>
        </div>
</section>
</body>
</html>