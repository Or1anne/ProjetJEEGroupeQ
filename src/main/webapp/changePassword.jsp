<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
                alert('New password must be at least 6 characters.');
                return false;
            }
            if (newPwd !== confirmPwd) {
                alert('New password and confirmation do not match.');
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

    <p class="success">${message}</p>
    <p class="error">${error}</p>

    <form action="ChangePasswordServlet" method="post" onsubmit="return validatePasswords()">
        <h2>Change Password</h2>
        <input type="hidden" name="id" value="${user.id}" />

        <label for="currentPassword">Mot de passe actuel</label>
        <input id="currentPassword" name="currentPassword" type="password" required />

        <label for="newPassword">Nouveau mot de passe</label>
        <input id="newPassword" name="newPassword" type="password" minlength="6" required />

        <label for="confirmPassword">Confirmer le nouveau mot de passe</label>
        <input id="confirmPassword" name="confirmPassword" type="password" minlength="6" required />

        <div class="form-action" style="display:flex;gap:10px;margin-top:12px;">
            <input type="submit" value="Enregistrer">
            <a href="Profile.jsp" class="button">Cancel</a>
        </div>
    </form>
</section>
</body>
</html>
