<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>Créer un projet</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/style.css">
</head>

<body>
    <div class="form-container">
        <form action="AddProject" method="post" class="employee-form"> <!-- TODO Ou mettre ProjectServelt ? -->
            <h2>Créer un projet</h2>
            <input type="hidden" name="action" value="add" />
            <p>
                <label for="projectName">Nom du projet</label>
                <input type="text" id="projectName" name="projectName" required>
            </p>
            <p>
                <label for="description">Description</label>
                <textarea name="description" id="description"></textarea>
            </p>

            <p>
                <label for="manager">Chef de projet</label>
                <input type="text" name="manager" id="manager" required>
            </p>

            <!--
            <label>Date de début :</label>
            <input type="date" name="startDate">

            <label>Date de fin prévue :</label>
            <input type="date" name="endDate">
            -->

            <input type="submit" value="Enregistrer">
        </form>
    </div>
</body>

</html>