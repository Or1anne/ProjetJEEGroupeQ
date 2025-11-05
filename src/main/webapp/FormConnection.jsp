<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Connexion</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/style.css">
</head>
<body>
    <div class="form-container">
        <form action="" method="post">
            <h2>Connexion</h2>
            <input type="hidden" name="action" value="add" />
            <p>
                <label for="username">Username</label>
                <input type="text" id="username" name="username" required>
            </p>
            <p>
                <label for="password">Mot de passe</label>
                <input type="password" id="password" name="password" required>
            </p>

            <input type="submit" value="Enregistrer">
        </form>
    </div>
</body>
</html>
