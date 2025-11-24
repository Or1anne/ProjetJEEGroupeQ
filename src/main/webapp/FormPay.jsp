<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>Créer une fiche de paie</title>
    <link rel="stylesheet" href="CSS/style.css">
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
                <a href="Search.jsp" class="navbar-item">Recherche</a>
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
    <nav>
        <a href="ListPay.jsp">Liste des fiches de paie</a>
    </nav>
<div class="form-container">
    <form action="AddPayslip" method="post" class="form">
    <h2>Créer une fiche de paie</h2>
        <label>Employé </label>
        <select name="employee">
            <option>-- Sélectionner un employé --</option>
            <option>Claire Durand</option>
            <option>Lucas Martin</option>
        </select>

        <label>Mois concerné </label>
        <input type="month" name="month" required>

        <label>Salaire de base (€) </label>
        <input type="number" name="baseSalary" step="0.01" required>

        <label>Primes (€)</label>
        <input type="number" name="bonus" step="0.01">

        <label>Déductions (€)</label>
        <input type="number" name="deduction" step="0.01">

        <button type="button" onclick="calculateNet()">Calculer le net</button>

        <div id="result">
            <p><strong>Net à payer :</strong> <span id="netValue">0.00</span> €</p>
        </div>

        <input type="submit" value="Enregistrer">
    </form>
</div>

<script>
    function calculateNet() {
        const base = parseFloat(document.querySelector('[name=baseSalary]').value) || 0;
        const bonus = parseFloat(document.querySelector('[name=bonus]').value) || 0;
        const deduction = parseFloat(document.querySelector('[name=deduction]').value) || 0;
        const net = base + bonus - deduction;
        document.getElementById('netValue').textContent = net.toFixed(2);
    }
</script>
</div>
</body>
</html>