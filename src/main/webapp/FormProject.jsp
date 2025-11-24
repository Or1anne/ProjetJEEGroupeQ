<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>Créer un projet</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/style.css">
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
    <div class="form-container">
        <form action="AddProject" method="post" class="employee-form"> <!-- TODO Ou mettre ProjectServelt ? -->
            <h2>Créer un projet</h2>
            <input type="hidden" name="action" value="add" />
            <p>
                <label for="projectName">Nom du projet</label>
                <input type="text" id="projectName" name="projectName" required>
            </p>
            <p>
                <label for="description">Description</label>
                <textarea name="description" id="description"></textarea>
            </p>

            <p>
                <label for="manager">Chef de projet</label>
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
</div>
</body>

</html>