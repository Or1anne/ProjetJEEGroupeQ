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
    <!-- Mettre les bonnes valeurs dans les champs du formulaire -->
    <p class="success">${message}</p>
    <p class="error">${error}</p>

    <form action="UpdateProfileServlet" method="post">
        <h2>Edit Profile</h2>
        <input type="hidden" name="id" value="${user.id}" />

        <label for="name">Name</label>
        <input id="name" name="name" type="text" value="${user.name}" required />

        <label for="email">Email</label>
        <input id="email" name="email" type="email" value="${user.email}" required />

        <label for="role">Role</label>
        <input id="role" name="role" type="text" value="${user.role}" readonly />

        <div class="form-action" style="display:flex;gap:10px;margin-top:12px;">
            <input type="submit" value="Enregistrer">
            <a href="Profile.jsp" class="button">Cancel</a>
        </div>
    </form>
</section>
</body>
</html>
