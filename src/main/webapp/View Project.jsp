<%--
  Created by IntelliJ IDEA.
  User: Cytech
  Date: 17/11/2025
  Time: 15:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
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
        <h2>Détails du Projet</h2>
    </header>

    <main class="container">
        <section class="project-details">
            <h3>Nom du Projet: Projet Exemple</h3>
            <p>Description: Ceci est une description détaillée du projet exemple.</p>
            <p>Date de début: 01/01/2024</p>
            <p>Date de fin: 31/12/2024</p>
            <p>Statut: En cours</p>
            <h4>Membres de l'équipe:</h4>
            <ul>
                <li>Employé 1</li>
                <li>Employé 2</li>
                <li>Employé 3</li>
            </ul>
        </section>
    </main>
</div>
</body>
</html>
