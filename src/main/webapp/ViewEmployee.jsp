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
    <h2>Employee Details</h2>

    <div class="card">
        <h3>Informations Personnels</h3>
        <ul>
            <li><strong>Nom :</strong> {{firstName}} {{lastName}}</li>
            <li><strong>Grade :</strong> {{grade}}</li>
            <li><strong>Poste :</strong> {{post}}</li>
            <li><strong>Salaire :</strong> {{salary}}</li>
            <li><strong>Nom d'utilisateur :</strong> {{username}}</li>
        </ul>

        <h3>Département :</h3>
        <p>
            {{departmentName or "Not assigned"}}
        </p>

        <h3>Rôle</h3>
        <p>
            Role
        </p>

        <h3>Projets</h3>
        <ul id="projects-list">
            <li>Project A <small>(Active)</small></li>
            <li>Project B <small>(Completed)</small></li>
        </ul>

        <div class="form-action" style="display:flex;gap:10px;margin-top:12px;">
            <a class="button" href="FormModifyEmployee.jsp">Modifier</a> <!-- TODO Add idEmployee as query parameter -->
            <a class="button" href="ListEmployee.jsp">Retour</a>
            <a class="button" href="">Historique des paies</a>
        </div>
    </div>
</section>

<!-- Optional: small script to demonstrate how to replace placeholders on client side -->
<script>
    const employee = {
        firstName: 'John',
        lastName: 'Doe',
        grade: 'A',
        post: 'Developer',
        salary: '4000',
        username: 'jdoe',
        departmentName: 'Engineering',
        idEmployee: 123,
        roles: ['Developer', 'Team Lead'],
        projects: [{name:'Project A', status:'Active'}]
    };

    // Replace simple placeholders in the static HTML
    document.body.innerHTML = document.body.innerHTML
        .replace('{{firstName}}', employee.firstName)
        .replace('{{lastName}}', employee.lastName)
        .replace('{{grade}}', employee.grade)
        .replace('{{post}}', employee.post)
        .replace('{{salary}}', employee.salary)
        .replace('{{username}}', employee.username)
        .replace('{{departmentName or "Not assigned"}}', employee.departmentName || 'Not assigned')
        .replace(/{{idEmployee}}/g, employee.idEmployee);
</script>
</body>
</html>
