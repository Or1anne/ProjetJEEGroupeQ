<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
        <h2>Tableau de bord</h2>
    </header>

    <main class="container">
        <section class="dashboard-grid">
            <div class="card">
                <h2>Employés</h2>
                <ul>
                    <li><a href="ListEmployee.jsp">Liste des employés</a></li>
                    <li><a href="FormEmployee.jsp">Ajouter un employé</a></li>
                </ul>
            </div>

            <div class="card">
                <h2>Départements</h2>
                <ul>
                    <li><a href="ListDepartment.jsp">Liste des départements</a></li>
                    <li><a href="FormDepartment.jsp">Créer un département</a></li>
                </ul>
            </div>

            <div class="card">
                <h2>Projets</h2>
                <ul>
                    <li><a href="ListProject.jsp">Liste des projets</a></li>
                    <li><a href="FormProject.jsp">Créer un projet</a></li>
                </ul>
            </div>
        </section>
    </main>
</div>
</body>
</html>