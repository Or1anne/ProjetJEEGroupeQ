<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>Formulaire de modification d'un employé</title>
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
        <form action="EmployeeServlet" method="post" class="employee-form">
            <h2>Modifier "NOM EMPLOYE"</h2> <!-- TODO Mettre dynamiquement le nom de l'employé à modifier avec ces infos -->
            <input type="hidden" name="action" value="add" />

            <p>
                <label for="lastName">Nom</label>
                <input type="text" id="lastname" name="lastname" required>
            </p>
            <p>
                <label for="firstname">Prénom</label>
                <input type="text" id="firstname" name="firstname" required>
            </p>
            <p>
                <label for="grade">Grade</label>
                <select name="gradeID" id="grade" required>
                    <option value="">-- Choisir un grade --</option>
                    <option value="cadre">Cadre</option>
                    <option value="stagiaire">Stagiaire</option>
                </select>
            </p>
            <p>
                <label for="post">Poste</label>
                <input type="number" id="post" name="post" max="50000">
            </p>
            <p>
                <label for="salary">Salaire</label>
                <input type="text" id="salary" name="salary">
            </p>
            <p>
                <label for="department">Département :</label>
                <select name="departmentId" id="department" required>
                    <option value="">-- Choisir un département --</option>
                    <%-- TODO Mettre les bonnes options de départements --%>
                    <%--
                    <c:forEach var="dep" items="${departments}">
                        <option value="${dep.id}">${dep.name}</option>
                    </c:forEach>
                    --%>
                    <option value="informatique">Informatique</option>
                </select>
            </p>

            <input type="submit" value="Enregistrer">
        </form>
    </div>
</div>
</body>
</html>