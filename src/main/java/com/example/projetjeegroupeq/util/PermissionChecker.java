package com.example.projetjeegroupeq.util;

import com.example.projetjeegroupeq.model.Employee;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

/**
 * Classe utilitaire pour vérifier les permissions dans les servlets et les filtres.
 * Simplifie la vérification des permissions dans le code.
 */
public class PermissionChecker {

    /**
     * Vérifie si l'utilisateur connecté a les permissions nécessaires pour accéder à une route/action.
     * 
     * @param req La requête HTTP
     * @param route La route (ex: "/employee")
     * @param action L'action (ex: "add", "edit", null pour toutes)
     * @return true si l'utilisateur a les permissions, false sinon
     */
    public static boolean hasPermission(HttpServletRequest req, String route, String action) {
        Employee loggedUser = getLoggedUser(req);
        if (loggedUser == null) {
            return false;
        }

        List<String> requiredRoles = PermissionConfig.getRequiredRoles(route, action);
        
        // Si aucune restriction, l'accès est autorisé (pour les utilisateurs connectés)
        if (requiredRoles == null || requiredRoles.isEmpty()) {
            return true;
        }

        // Vérifier si l'utilisateur a au moins un des rôles requis
        return RoleUtil.hasAnyRole(loggedUser, requiredRoles.toArray(new String[0]));
    }

    /**
     * Vérifie si l'utilisateur connecté a les permissions nécessaires pour accéder à une JSP.
     * 
     * @param req La requête HTTP
     * @param jspPath Le chemin de la JSP (ex: "/FormEmployee.jsp")
     * @return true si l'utilisateur a les permissions, false sinon
     */
    public static boolean hasPermissionForJsp(HttpServletRequest req, String jspPath) {
        Employee loggedUser = getLoggedUser(req);
        if (loggedUser == null) {
            return false;
        }

        List<String> requiredRoles = PermissionConfig.getRequiredRolesForJsp(jspPath);
        
        // Si aucune restriction, l'accès est autorisé (pour les utilisateurs connectés)
        if (requiredRoles == null || requiredRoles.isEmpty()) {
            return true;
        }

        // Vérifier si l'utilisateur a au moins un des rôles requis
        return RoleUtil.hasAnyRole(loggedUser, requiredRoles.toArray(new String[0]));
    }

    /**
     * Vérifie les permissions et envoie une erreur 403 si l'utilisateur n'a pas les droits.
     * 
     * @param req La requête HTTP
     * @param resp La réponse HTTP
     * @param route La route
     * @param action L'action
     * @return true si l'utilisateur a les permissions, false sinon (et une erreur 403 a été envoyée)
     * @throws IOException En cas d'erreur lors de l'envoi de la réponse
     */
    public static boolean checkPermission(HttpServletRequest req, HttpServletResponse resp, 
                                         String route, String action) throws IOException {
        if (!hasPermission(req, route, action)) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, 
                "Accès refusé : Vous n'avez pas les permissions nécessaires pour accéder à cette ressource.");
            return false;
        }
        return true;
    }

    /**
     * Vérifie les permissions pour une JSP et envoie une erreur 403 si l'utilisateur n'a pas les droits.
     * 
     * @param req La requête HTTP
     * @param resp La réponse HTTP
     * @param jspPath Le chemin de la JSP
     * @return true si l'utilisateur a les permissions, false sinon (et une erreur 403 a été envoyée)
     * @throws IOException En cas d'erreur lors de l'envoi de la réponse
     */
    public static boolean checkPermissionForJsp(HttpServletRequest req, HttpServletResponse resp, 
                                                 String jspPath) throws IOException {
        if (!hasPermissionForJsp(req, jspPath)) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, 
                "Accès refusé : Vous n'avez pas les permissions nécessaires pour accéder à cette page.");
            return false;
        }
        return true;
    }

    /**
     * Récupère l'utilisateur connecté depuis la session
     * 
     * @param req La requête HTTP
     * @return L'employé connecté, ou null si non connecté
     */
    public static Employee getLoggedUser(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if (session == null) {
            return null;
        }
        return (Employee) session.getAttribute("loggedUser");
    }

    /**
     * Vérifie si l'utilisateur a un rôle spécifique
     * 
     * @param req La requête HTTP
     * @param roleNames Les noms des rôles à vérifier
     * @return true si l'utilisateur a au moins un des rôles
     */
    public static boolean hasRole(HttpServletRequest req, String... roleNames) {
        Employee loggedUser = getLoggedUser(req);
        if (loggedUser == null) {
            return false;
        }
        return RoleUtil.hasAnyRole(loggedUser, roleNames);
    }
}

