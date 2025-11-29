<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.example.projetjeegroupeq.model.Employee" %>
<%@ page import="java.util.List" %>

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
<%
    Employee prefill = (Employee) request.getAttribute("prefillEmployee");
%>
<div class="hero-body">
    <nav>
        <a href="ListPay.jsp">Liste des fiches de paie</a>
    </nav>
<div class="form-container">
    <form action="<%= request.getContextPath() %>/pay" method="post" class="form">
    <h2>Créer une fiche de paie</h2>
        <label>Employé </label>
        <% if (prefill != null) { %>

        <!-- Cas : employé déjà connu -->
        <input type="hidden" name="employeeId" value="<%= prefill.getId() %>">
        <input type="text" value="<%= prefill.getFirstName() + " " + prefill.getLastName() %>" disabled>

        <% } else { %>

        <!-- Cas global : choix manuel -->
        <select name="employeeId" required>
            <option value="">-- Sélectionner un employé --</option>
            <%
                List<Employee> employees = new com.example.projetjeegroupeq.dao.implementation.EmployeeDAO().getAll();
                for (Employee e : employees) {
            %>
            <option value="<%= e.getId() %>"><%= e.getFirstName() %> <%= e.getLastName() %></option>
            <% } %>
        </select>

        <% } %>

        <label>Mois concerné </label>
        <input type="month" name="month" required>

        <label>Salaire de base (€) </label>
        <% if (prefill != null) { %>
        <input type="number" name="baseSalary" value="<%= prefill.getSalary() %>" readonly>
        <% } else { %>
        <input type="number" name="baseSalary" step="0.01" required>
        <% } %>

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