<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.projetjeegroupeq.model.Employee" %>
<!DOCTYPE html>
<html>
<head>
    <title>Change Password</title>
    <link rel="stylesheet" href="CSS/style.css">
    <script>
        function validatePasswords() {
            const newPwd = document.getElementById('newPassword').value;
            const confirmPwd = document.getElementById('confirmPassword').value;
            if (newPwd.length < 6) {
                alert('Le mot de passe doit faire au moins 6 caractères');
                return false;
            }
            if (newPwd !== confirmPwd) {
                alert('Les mots de passe ne correspondent pas');
                return false;
            }
            return true;
        }
    </script>
</head>
<body>
<div class="hero-head">
    <nav class="navbar">
        <div class="container">
            <div class="navbar-start">
                <a href="index.jsp" class="navbar-item">Accueil</a>
                <%
                    // On récupère l'utilisateur connecté
                    Employee loggedUser = (Employee) session.getAttribute("loggedUser");

                    if (loggedUser != null) {
                        // Si connecté, on affiche Recherche et Gestion
                %>
                <a href="Gestion.jsp" class="navbar-item">Gestion</a>
                <%
                    }
                %>
            </div>

            <div class="navbar-end">
                <%
                    if (loggedUser != null) {
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
<section class="hero-body">

    <%
        String message = request.getParameter("message");
        String error = (String) request.getAttribute("error");
    %>
    <% if (message != null && !message.isBlank()) { %>
        <p class="success"><%= message %></p>
    <% } %>
    <% if (error != null && !error.isBlank()) { %>
        <p class="error"><%= error %></p>
    <% } %>

    <form action="<%= request.getContextPath() %>/ChangePasswordServlet" method="post" onsubmit="return validatePasswords()">
        <h2>Changer le mot de passe</h2>
        <%
            if (loggedUser != null) {
        %>
        <input type="hidden" name="id" value="<%= loggedUser.getId() %>" />
        <%
            }
        %>

        <label for="currentPassword">Mot de passe actuel</label>
        <input id="currentPassword" name="currentPassword" type="password" required />

        <label for="newPassword">Nouveau mot de passe</label>
        <input id="newPassword" name="newPassword" type="password" minlength="6" required />

        <label for="confirmPassword">Confirmer le nouveau mot de passe</label>
        <input id="confirmPassword" name="confirmPassword" type="password" minlength="6" required />

        <div class="form-action" style="display:flex;gap:10px;margin-top:12px;">
            <input type="submit" value="Enregistrer">
            <a href="Profile.jsp" class="button">Annuler</a>
        </div>
    </form>
</section>
</body>
</html>
