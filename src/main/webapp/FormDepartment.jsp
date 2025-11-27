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
                <%
                    // On récupère l’utilisateur connecté
                    Object user = session.getAttribute("loggedUser");

                    if (user != null) {
                        // Si connecté, on affiche Recherche et Gestion
                %>
                <a href="Search.jsp" class="navbar-item">Recherche</a>
                <a href="Gestion.jsp" class="navbar-item">Gestion</a>
                <%
                    }
                %>
            </div>

            <div class="navbar-end">
                <%
                    if (user != null) {
                        // Si connecté
                %>
                <a href="Profile.jsp" class="navbar-item">Profil</a>
                <a href="<%= request.getContextPath() %>/logout" class="navbar-item">Déconnexion</a>
                <%
                } else {
                    // Si pas connecté
                %>
                <a href="<%= request.getContextPath() %>/login" class="navbar-item">Connexion</a>
                <%
                    }
                %>
            </div>
        </div>
    </nav>
</div>
<div class="hero-body">
    <nav>
        <a href="<%= request.getContextPath() %>/department?action=list">Liste des départements</a>
    </nav>
    <div class="form-container">
        <form action="<%= request.getContextPath() %>/department" method="post">
            <h2>Ajouter un département</h2>

            <!-- On dit à la servlet quoi faire -->
            <input type="hidden" name="action" value="add"/>
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

            <input type="submit" value="Enregistrer">
        </form>
    </div>
</div>
</body>
</html>