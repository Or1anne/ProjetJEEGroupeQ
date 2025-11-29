<%--
  Created by IntelliJ IDEA.
  User: Cytech
  Date: 29/11/2025
  Time: 01:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.example.projetjeegroupeq.model.Department" %>
<%@ page import="com.example.projetjeegroupeq.model.Project" %>
<%@ page import="com.example.projetjeegroupeq.model.Grade" %>
<!DOCTYPE html>
<html>
<head>
    <title>Rapports & Statistiques</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/style.css">
    <!-- Librairie pour les graphiques -->
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
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

<div class="report-page">

    <h1 class="report-title">Statistiques</h1>

<%
    Map<Department, Long> employeesByDept =
            (Map<Department, Long>) request.getAttribute("employeesByDept");
    Map<Project, Long> employeesByProject =
            (Map<Project, Long>) request.getAttribute("employeesByProject");
    Map<Grade, Long> employeesByGrade =
            (Map<Grade, Long>) request.getAttribute("employeesByGrade");
%>
    <div class="stats-cards">
        <!-- 1. Nb d’employés par département -->
        <section class="stats-card">
            <h2 class="stats-card-title">Nombre d’employés par département</h2>
            <div class="stats-table-wrapper">
                <table class="stats-table">
                    <thead>
                    <tr>
                        <th>Département</th>
                        <th>Nombre d’employés</th>
                    </tr>
                    </thead>
                    <tbody>
                        <%
                        if (employeesByDept != null) {
                            for (Map.Entry<Department, Long> entry : employeesByDept.entrySet()) {
                        %>
    <tr>
        <td><%= entry.getKey().getDepartmentName() %></td>
        <td class ="stats-value"><%= entry.getValue() %></td>
    </tr>
        <%
                            }
                        } else {
                    %>
    <tr>
        <td colspan="2" class="stats-empty">Aucune donnée disponible</td>
    </tr>
        <%
                        }
                    %>
                </table>
            </div>

            <div class="chart-container">
                <canvas id="deptChart"></canvas>
            </div>
        </section>


<!-- 2. Nb d’employés par projet -->
        <section class="card">
            <h2 class="stats-card-title">Nombre d’employés par projet</h2>
            <div class="stats-table-wrapper">
                <table class="stats-table">
                    <thead>
                    <tr>
                        <th>Projet</th>
                        <th>Nombre d’employés</th>
                    </tr>
                    </thead>
                    <tbody>
    <%
        if (employeesByProject != null) {
            for (Map.Entry<Project, Long> entry : employeesByProject.entrySet()) {
    %>
    <tr>
        <td><%= entry.getKey().getName_project() %></td>
        <td class="stats-value"><%= entry.getValue() %></td>
    </tr>
    <%
        }
    } else {
    %>
    <tr>
        <td colspan="2" class="stats-empty">Aucune donnée disponible</td>
    </tr>
    <%
        }
    %>
                    </tbody>
                </table>
            </div>
            <div class="chart-container">
                <canvas id="projectChart"></canvas>
            </div>
        </section>



        <!-- 3. Nb d’employés par grade -->
        <section class="stats-card">
            <h2 class="stats-card-title">Nombre d’employés par grade</h2>
            <div class="stats-table-wrapper">
                <table class="stats-table">
                    <thead>
                    <tr>
                        <th>Grade</th>
                        <th>Nombre d’employés</th>
                    </tr>
                    </thead>
                    <tbody>
    <%
        if (employeesByGrade != null) {
            for (Map.Entry<Grade, Long> entry : employeesByGrade.entrySet()) {
    %>
    <tr>
        <td><%= entry.getKey() %></td>
        <td class="stats-value"><%= entry.getValue() %></td>
    </tr><%
        }
    } else {
    %>
    <tr>
        <td colspan="2" class="stats-empty">Aucune donnée disponible</td>
    </tr>
    <%
        }
    %>
                    </tbody>
                </table>
            </div>
            <div class="chart-container">
                <canvas id="gradeChart"></canvas>
            </div>
        </section>
    </div>




    <div class="report-footer">
        <a href="${pageContext.request.contextPath}/Gestion.jsp" class="btn-secondary">
            ⬅ Retour au tableau de bord
        </a>
    </div>

</div>

<script>
    // On prépare les tableaux vides
    const deptLabels = [];
    const deptData = [];

    const projectLabels = [];
    const projectData = [];

    const gradeLabels = [];
    const gradeData = [];

    <%-- 1) Remplir les données départements --%>
    <% if (employeesByDept != null) {
           for (Map.Entry<Department, Long> entry : employeesByDept.entrySet()) { %>
    deptLabels.push("<%= entry.getKey().getDepartmentName() %>");
    deptData.push(<%= entry.getValue() %>);
    <%   }
       } %>

    <%-- 2) Remplir les données projets --%>
    <% if (employeesByProject != null) {
           for (Map.Entry<Project, Long> entry : employeesByProject.entrySet()) { %>
    projectLabels.push("<%= entry.getKey().getName_project() %>");
    projectData.push(<%= entry.getValue() %>);
    <%   }
       } %>

    <%-- 3) Remplir les données grades --%>
    <% if (employeesByGrade != null) {
           for (Map.Entry<Grade, Long> entry : employeesByGrade.entrySet()) { %>
    gradeLabels.push("<%= entry.getKey().name() %>");
    gradeData.push(<%= entry.getValue() %>);
    <%   }
       } %>

    // Création du graphique départements (barres horizontales)
    const deptCtx = document.getElementById('deptChart');
    if (deptCtx && deptLabels.length > 0) {
        new Chart(deptCtx, {
            type: 'bar',
            data: {
                labels: deptLabels,
                datasets: [{
                    label: 'Nbr employés',
                    data: deptData
                }]
            },
            options: {
                indexAxis: 'y',
                responsive: true,
                plugins: {
                    legend: { display: false },
                    title: { display: false }
                },
                scales: {
                    x: { beginAtZero: true }
                }
            }
        });
    }

    // Graphique projets
    const projectCtx = document.getElementById('projectChart');
    if (projectCtx && projectLabels.length > 0) {
        new Chart(projectCtx, {
            type: 'bar',
            data: {
                labels: projectLabels,
                datasets: [{
                    label: 'Nbr employés',
                    data: projectData
                }]
            },
            options: {
                responsive: true,
                plugins: {
                    legend: { display: false }
                },
                scales: {
                    y: { beginAtZero: true }
                }
            }
        });
    }

    // Graphique grades (camembert)
    const gradeCtx = document.getElementById('gradeChart');
    if (gradeCtx && gradeLabels.length > 0) {
        new Chart(gradeCtx, {
            type: 'doughnut',
            data: {
                labels: gradeLabels,
                datasets: [{
                    data: gradeData
                }]
            },
            options: {
                responsive: true,
                plugins: {
                    legend: {
                        position: 'bottom'
                    }
                }
            }
        });
    }
</script>


</body>
</html>