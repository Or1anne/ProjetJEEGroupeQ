<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Créer un projet</title>
    <link rel="stylesheet" href="CSS/style.css">
</head>
<body>
<header>

    <nav>
        <a href="Dashboard.jsp">Tableau de bord</a> |
        <a href="ListProjects.jsp">Liste des projets</a>
    </nav>
</header>

<div class="form-container">
    <form action="AddProject" method="post" class="form"> <!-- TODO Ou mettre ProjectServelt ? -->
        <h2>Créer un projet</h2>
        <input type="hidden" name="action" value="add" />

        <p>
            <label for="projectName">Nom du projet :</label>
            <input type="text" id="projectName" name="projectName" required>
        </p>
        <p>
            <label for="description">Description :</label>
            <textarea name="description" id="description"></textarea>
        </p>

        <p>
            <label for="manager">Chef de projet :</label>
            <input type="text" name="manager" id="manager" required>
        </p>

        <!--
        <label>Date de début :</label>
        <input type="date" name="startDate">

        <label>Date de fin prévue :</label>
        <input type="date" name="endDate">
        -->

        <input type="submit" value="Enregistrer">
    </form>
</div>
</body>
</html>