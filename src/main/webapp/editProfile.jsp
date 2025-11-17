<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Edit Profile</title>
    <link rel="stylesheet" href="CSS/style.css">
</head>
<body>
<section class="hero-body">

    <p class="success">${message}</p>
    <p class="error">${error}</p>

    <form action="UpdateProfileServlet" method="post">
        <h2>Edit Profile</h2>
        <input type="hidden" name="id" value="${user.id}" />

        <label for="name">Name</label>
        <input id="name" name="name" type="text" value="${user.name}" required />

        <label for="email">Email</label>
        <input id="email" name="email" type="email" value="${user.email}" required />

        <label for="role">Role</label>
        <input id="role" name="role" type="text" value="${user.role}" readonly />

        <div class="form-actions">
            <input type="submit" value="Enregistrer">
            <a href="Profile.jsp" class="button">Cancel</a>
        </div>
    </form>
</section>
</body>
</html>
