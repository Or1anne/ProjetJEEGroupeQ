<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <title>View Employee</title>
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

<section class="hero-body">

    <div class="card">
        <h2>Informations Personnelles</h2>
        <ul>
            <li><strong>Nom :</strong> ${employee.lastName}</li>
            <li><strong>Prénom :</strong> ${employee.firstName}</li>
            <li><strong>Grade :</strong> ${employee.grade}</li>
            <li><strong>Poste :</strong> ${employee.post}</li>
            <li><strong>Salaire :</strong> ${employee.salary}€</li>
            <li><strong>Nom d'utilisateur :</strong> ${employee.username}</li>
        </ul>

        <h3>Département :</h3>
        <p>
            ${employee.department != null ? employee.department.departmentName : "Non assigné"}
        </p>

        <h3>Rôle</h3>
        <!-- TODO Mettre en dynamique -->
        <p>
            Role
        </p>
<!-- TODO Mettre en dynamique -->
        <h3>Projets</h3>
        <ul id="projects-list">
            <li>Project A <small>(Active)</small></li>
            <li>Project B <small>(Completed)</small></li>
        </ul>

        <!-- TODO Historique des paies -->

        <div class="form-action" style="display:flex;gap:10px;margin-top:12px;">
            <a class="button" href="employee?action=edit&id=${employee.id}">Modifier</a> <!-- TODO Add idEmployee as query parameter -->
            <a class="button" href="employee">Retour</a>
            <a class="button" href="ListPay.jsp">Historique des paies</a>
        </div>
    </div>
</section>

</body>
</html>
