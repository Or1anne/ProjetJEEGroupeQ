<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>Formulaire de création d'un employé</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/style.css">
</head>
<body>
    <div class="form-container">
        <form action="EmployeeServlet" method="post" class="employee-form">
            <h2>Ajouter un employé</h2>
            <input type="hidden" name="action" value="add" />

            <p>
                <label for="lastname">Nom</label>
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
</body>
</html>
