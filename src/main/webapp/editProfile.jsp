<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Edit Profile</title>
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
    <!-- TODO Mettre les bonnes valeurs dans les champs du formulaire -->
    <p class="success">${message}</p>
    <p class="error">${error}</p>

    <form action="UpdateProfileServlet" method="post">
        <h2>Modification du profil</h2>
        <input type="hidden" name="id" value="${user.id}" />

        <label for="name">Nom</label>
        <input id="name" name="name" type="text" value="${user.name}" required />

        <label for="firstname">Prénom</label>
        <input id="firstname" name="firstname" type="text" value="${user.firstname}" required />

        <label for="grade">Grade</label>
        <input id="grade" name="grade" type="text" value="${user.grade}" readonly />

        <label for="post">Poste</label>
        <input id="post" name="post" type="text" value="${user.post}" readonly />

        <label for="salary">Salaire</label>
        <input id="salary" name="salary" type="number" value="${user.salary}" readonly />

        <label for="department">Département</label>
        <input id="department" name="department" type="text" value="${user.department}" readonly /> <!-- TODO Mettre liste déroulante dynamique -->

        <label for="project">Projet(s)</label>
        <input id="project" name="project" type="text" value="${user.project}" readonly />  <!-- TODO Mettre liste déroulante dynamique -->

        <div class="form-action" style="display:flex;gap:10px;margin-top:12px;">
            <input type="submit" value="Enregistrer">
            <a href="Profile.jsp" class="button">Annuler</a>
        </div>
    </form>
</section>
</body>
</html>
