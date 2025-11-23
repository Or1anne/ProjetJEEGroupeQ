<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>Ajouter un département</title>
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
    <div class="form-container">
        <form action="AddDepartment" method="post">
            <h2>Ajouter un département</h2>
            <p>
                <label for="name">Nom du département</label>
                <input type="text" id="name" name="name" required>
            </p>
            <p>
                <label for="ChefDepartment">Chef de département</label>
                <select name="ChefDepartment" id="ChefDepartment" required>
                    <option value="">-- Choisir un chef de département --</option>
                    <option value="1">Durand Claire</option>
                    <option value="2">Martin Lucas</option>
                </select>
            </p>
            <p>
                <label for="description">Description</label>
                <textarea name="description" id="description"></textarea>
            </p>

            <input type="submit" value="Enregistrer">
        </form>
    </div>
</div>
</body>
</html>