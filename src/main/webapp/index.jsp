<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Artic</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/style.css">
</head>
<body>
    <section>
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
            <h2>Accueil</h2>
        </div>
    </section>
</body>
</html>