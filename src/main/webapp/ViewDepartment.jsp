<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %><!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Membres du département</title>
    <link rel="stylesheet" href="CSS/style.css">
</head>
<body>
<div class="hero-body">
    <h2>Membres du département : "NOM DEPARTEMENT"</h2> <!-- TODO Récupérer le nom du département dynamiquement -->
    <nav>
        <a href="ListDepartment.jsp">Retour</a>
    </nav>
    <table class="table">
        <thead>
        <tr>
            <th>Nom</th>
            <th>Prénom</th>
            <th>Grade</th>
            <th>Poste</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td>Durand</td>
            <td>Claire</td>
            <td>Cadre</td>
            <td>Développeuse</td>
        </tr>
        <tr>
            <td>Martin</td>
            <td>Lucas</td>
            <td>Cadre</td>
            <td>Chef de projet</td>
        </tr>
        </tbody>
    </table>
</div>
</body>
</html>