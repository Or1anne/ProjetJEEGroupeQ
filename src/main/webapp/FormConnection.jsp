<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/style.css">
<html>
<head>
    <title>Connexion</title>
</head>
<body>
    <form action="" method="post">
        <input type="hidden" name="action" value="add" />
        <p>
            <label for="username">Username</label>
            <input type="text" id="username" name="username" required>
        </p>
        <p>
            <label for="password">Mot de passe</label>
            <input type="text" id="password" name="password" required>
        </p>
    </form>

</body>
</html>
