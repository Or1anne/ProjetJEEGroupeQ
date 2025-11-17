<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Liste des projets</title>
    <link rel="stylesheet" href="CSS/style.css">
</head>
<body>

<div class="hero-body">
    <h2>Liste des projets</h2>
    <nav>
        <a href="FormProject.jsp">Créer un projet</a>
    </nav>

    <table class="table">
        <thead>
        <tr>
            <th>ID</th>
            <th>Nom</th>
            <th>Chef de projet</th>
            <th>État</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td>PRJ001</td>
            <td>Migration Cloud</td>
            <td>Lucas Martin</td>
            <td>En cours</td>
            <td>
                <a href="AffectEmployeeProject.jsp">Affecter employés</a> |
                <a href="TrackProject.jsp">Suivre</a>
            </td>
        </tr>
        </tbody>
    </table>
</div>
</body>
</html>