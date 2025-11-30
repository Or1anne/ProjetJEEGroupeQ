<%@ page import="com.example.projetjeegroupeq.model.Employee" %>
<%@ page import="com.example.projetjeegroupeq.util.PermissionChecker" %>
<%@ page import="com.example.projetjeegroupeq.util.RoleUtil" %>

<%--
    Helper pour vérifier les permissions dans les JSP.
    
    Usage :
    <%
        // Vérifier par route/action (recommandé)
        if (PermissionChecker.hasPermission(request, "/employee", "add")) {
    %>
        <a href="/employee?action=add">Ajouter</a>
    <%
        }
        
        // Vérifier par rôles
        if (PermissionChecker.hasRole(request, "RH", "ADMIN")) {
    %>
        <a href="/employee?action=add">Ajouter</a>
    <%
        }
    %>
--%>

