<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Connexion</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/style.css">
</head>
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
<body>
    <div class="hero-body">
        <div class="form-container">
            <form action="" method="post">
                <h2>Connexion</h2>
                <%
                    String error = (String) request.getAttribute("error");
                    if (error != null) {
                %>
                <div style="color: red; margin-bottom: 15px; text-align: center; font-weight: bold;">
                    <%= error %>
                </div>
                <%
                    }
                %>
                <input type="hidden" name="action" />
                <p>
                    <label for="username">Nom d'utilisateur</label>
                    <input type="text" id="username" name="username" required>
                </p>
                <p>
                    <label for="password">Mot de passe</label>
                    <input type="password" id="password" name="password" required>
                </p>

                <input type="submit" value="Enregistrer">
            </form>
        </div>
    </div>
</body>
</html>
