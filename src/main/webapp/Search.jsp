<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <meta charset="UTF-8">
    <title>Recherche employé</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/style.css">
</head>
<body>
<div class="hero-body">
    <h2>Recherche d’un employé</h2>
    <!-- TODO Je ne sais pas si c'est la bonne solution -->
    <form action="SearchEmployeeServlet" method="get" class="search-form">
        <p>
            <label for="searchCriteria">Critère de recherche :</label>
            <select id="searchCriteria" name="searchCriteria" required>
                <option value="">-- Sélectionner --</option>
                <option value="name">Nom</option>
                <option value="department">Département</option>
                <option value="grade">Grade</option>
            </select>
        </p>
        <p>
            <label for="searchValue">Valeur :</label>
            <input type="text" id="searchValue" name="searchValue" required>
        </p>
        <input type="submit" value="Rechercher">
    </form>
</div>
</body>
</html>