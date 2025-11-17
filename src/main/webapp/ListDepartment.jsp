<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Liste des départements</title>
    <link rel="stylesheet" href="CSS/style.css">
</head>
<body>

<div class="hero-body">
    <h2>Liste des départements</h2>
    <nav>
        <a href="FormDepartment.jsp">Ajouter un département</a>
    </nav>

    <table class="table">
        <thead>
        <tr>
            <th>ID</th>
            <th>Nom</th>
            <th>Description</th>
        </tr>
        </thead>
        <tbody>
        <!-- Exemple -->
        <!-- TODO Mettre les départements en dynamique -->
        <tr onclick="window.location.href='ViewDepartment.jsp?id=1'"> <!-- TODO Mettre le bon id en dynamique -->
            <td>1</td>
            <td>Informatique</td>
            <td>Développement et maintenance des systèmes</td>

        </tr>
        </tbody>
    </table>
</div>
</body>
</html>