<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Tableau de bord</title>
    <link rel="stylesheet" href="CSS/style.css">
</head>
<body>
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
                <li><a href="listProjects">Liste des projets</a></li>
                <li><a href="FormProject.jsp">Créer un projet</a></li>
            </ul>
        </div>
    </section>
</main>
</body>
</html>