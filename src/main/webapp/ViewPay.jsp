<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.example.projetjeegroupeq.model.Pay" %>
<%@ page import="com.example.projetjeegroupeq.model.Employee" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%
    // Récupération de l'objet Pay envoyé par la servlet
    Pay pay = (Pay) request.getAttribute("pay");

    // Sécurité : si on accède à cette page sans passer par le servlet ou si ID invalide
    if (pay == null) {
        response.sendRedirect("pay?action=list");
        return;
    }

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat sdf_long = new SimpleDateFormat("dd MMMM yyyy", new java.util.Locale("fr", "FR"));


    // Récupération de l'employé filtré (peut être null si on affiche tout le monde)
    Employee currentEmployee = pay.getEmployee();

    // Url bouton de retour
    String fromEmployeeId = (String) request.getAttribute("fromEmployeeId");
    String retourUrl;
    // TODO A CORRIGER CE BOUTON RETOUR
    if (fromEmployeeId != null) {
        retourUrl = "pay?action=list&employeeId=" + fromEmployeeId;
    } else {
        // Sinon retour à la liste globale
        retourUrl = "pay?action=list";
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Détail de la fiche de paie</title>
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

    <div class="card">
        <h2>Fiche de paie - <%= currentEmployee.getFirstName() %> <%= currentEmployee.getLastName() %> (<%= sdf.format(pay.getDate()) %>)</h2>
        <table class="table">
            <tr><th>Salaire de base :</th><td><%= String.format("%.2f", pay.getEmployee().getSalary()) %> €</td></tr>
            <tr><th>Primes :</th><td style="color:green;"><%= String.format("%.2f", pay.getBonus()) %> €</td></tr>
            <tr><th>Déductions :</th><td style="color:red;"><%= String.format("%.2f", pay.getDeductions()) %> €</td></tr>
            <tr><th><strong>Net à payer :</strong></th><td><strong><%= String.format("%.2f", pay.getSalary_net()) %> €</strong></td></tr>
        </table>

        <section class="signature">
            <p>Fait à Cergy, le <%= sdf_long.format(pay.getDate()) %></p>
            <p><em>Signature de l’employé</em> : _______________________</p>
        </section>

        <div class="form-action" style="display:flex;gap:10px;margin-top:12px;">
            <a href="<%= retourUrl %>">Retour</a>
            <a href="<%= request.getContextPath() %>/pay?action=pdf&payId=<%= pay.getId() %>">Imprimer</a>
        </div>
    </div>


</div>
</body>
</html>