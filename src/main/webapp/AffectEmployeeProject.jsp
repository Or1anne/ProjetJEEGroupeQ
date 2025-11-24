<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <meta charset="UTF-8">
    <title>Affectation d’un employé à un projet</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/style.css">
</head>
<body>
<div class="hero-head">
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
        <a href="ListEmployee.jsp">Retour à la liste</a>
    </nav>

    <div class="form-container">
        <form action="" method="post" class="form"> <!-- TODO action du formulaire -->
            <h2>Affecter un employé à un projet</h2>
            <input type="hidden" name="action" value="add" />

            <p>
                <label for="employee">Employé</label>
                <select id="employee" name="employee"> <!-- TODO mettre les employés en dynamique -->
                    <option>-- Sélectionner --</option>
                    <option>Durand Claire</option>
                    <option>Martin Lucas</option>
                </select>
            </p>
            <p>
                <label for="project">Projet</label>
                <select id="project" name="project"> <!-- TODO mettre les projets en dynamique -->
                    <option>-- Sélectionner --</option>
                    <option>Projet Intranet</option>
                    <option>Migration Cloud</option>
                </select>
            </p>

            <input type="submit" value="Enregistrer">
        </form>
    </div>
</div>
</body>
</html>