<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Profile</title>
    <link rel="stylesheet" href="CSS/style.css">
</head>
<body>


<section class="hero-body">
    <nav>
        <a href="editProfile.jsp">Edit Profile</a>
        <a href="changePassword.jsp">Change Password</a>
    </nav>
    <div class="dashboard-grid">
        <div class="card">
            <h2>Personal Information</h2>
            <ul>
                <li>Name: ${user.name}</li>
                <li>Email: ${user.email}</li>
                <li>Role: ${user.role}</li>
            </ul>
        </div>
    </div>
</section>
</body>
</html>