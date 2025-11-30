package com.example.projetjeegroupeq.util;

import java.util.*;

/**
 * Configuration centralisée des permissions pour toutes les pages et actions.
 * 
 * Pour ajouter une nouvelle permission, il suffit d'ajouter une entrée dans les maps ci-dessous.
 * 
 * Format : 
 * - Route pattern : "/servlet" ou "/page.jsp"
 * - Action : "add", "edit", "delete", "view", "list", etc. (null = toutes les actions)
 * - Rôles requis : Liste des rôles autorisés (ADMIN, RH, EMPLOYE)
 */
public class PermissionConfig {

    /**
     * Map des permissions pour les servlets (format: "route:action" -> [rôles])
     * Si action est null, s'applique à toutes les actions de cette route
     */
    private static final Map<String, List<String>> SERVLET_PERMISSIONS = new HashMap<>();

    /**
     * Map des permissions pour les JSP (format: "/page.jsp" -> [rôles])
     */
    private static final Map<String, List<String>> JSP_PERMISSIONS = new HashMap<>();

    static {
        // ============================================
        // PERMISSIONS POUR LES SERVLETS
        // ============================================
        
        // EMPLOYEE SERVLET
        addServletPermission("/employee", "add", "RH", "ADMIN");
        addServletPermission("/employee", "edit", "RH", "ADMIN");
        addServletPermission("/employee", "delete", "ADMIN");
        // list et view sont accessibles à tous les utilisateurs connectés

        // DEPARTMENT SERVLET
        addServletPermission("/department", "add", "RH", "ADMIN");
        addServletPermission("/department", "edit", "RH", "ADMIN");
        addServletPermission("/department", "delete", "ADMIN");
        // list et view sont accessibles à tous les utilisateurs connectés

        // PROJECT SERVLET
        addServletPermission("/project", "add", "RH", "ADMIN");
        addServletPermission("/project", "edit", "RH", "ADMIN");
        addServletPermission("/project", "delete", "ADMIN");
        addServletPermission("/project", "addemployees", "RH", "ADMIN");
        // list, view et track sont accessibles à tous les utilisateurs connectés

        // PAY SERVLET
        addServletPermission("/pay", "add", "RH", "ADMIN");
        //addServletPermission("/pay", "pdf", "RH", "ADMIN");
        addServletPermission("/pay", "delete", "ADMIN");
        // view est accessibles à tous les utilisateurs connectés
        // Les employés peuvent voir leurs propres fiches de paie (géré dans le servlet)

        // REPORT SERVLET
        addServletPermission("/report", null, "RH", "ADMIN");
        // Toutes les actions du report nécessitent RH ou ADMIN

        // ============================================
        // PERMISSIONS POUR LES JSP
        // ============================================
        
        // Formulaires d'ajout/modification
        addJspPermission("/FormEmployee.jsp", "RH", "ADMIN");
        addJspPermission("/FormDepartment.jsp", "RH", "ADMIN");
        addJspPermission("/FormProject.jsp", "RH", "ADMIN");
        addJspPermission("/FormPay.jsp", "RH", "ADMIN");
        addJspPermission("/FormModifyEmployee.jsp", "RH", "ADMIN");
        addJspPermission("/FormModifyDepartment.jsp", "RH", "ADMIN");
        addJspPermission("/FormProjectEmployees.jsp", "RH", "ADMIN");
        addJspPermission("/AffectEmployeeProject.jsp", "RH", "ADMIN");

        // Pages de gestion (déjà protégées par AuthFilter, mais on peut ajouter des restrictions)
        // Gestion.jsp, ListEmployee.jsp, etc. sont accessibles à tous les utilisateurs connectés
        // mais certains boutons seront masqués selon les rôles (géré dans les JSP)
    }

    /**
     * Ajoute une permission pour un servlet
     */
    private static void addServletPermission(String route, String action, String... roles) {
        String key = action != null ? route + ":" + action : route;
        SERVLET_PERMISSIONS.put(key, Arrays.asList(roles));
    }

    /**
     * Ajoute une permission pour une JSP
     */
    private static void addJspPermission(String jspPath, String... roles) {
        JSP_PERMISSIONS.put(jspPath, Arrays.asList(roles));
    }

    /**
     * Vérifie si une route/action nécessite des permissions spécifiques
     * 
     * @param route La route (ex: "/employee")
     * @param action L'action (ex: "add", "edit", null pour toutes)
     * @return Liste des rôles requis, ou null si aucune restriction
     */
    public static List<String> getRequiredRoles(String route, String action) {
        // Vérifier d'abord avec l'action spécifique
        if (action != null && !action.isBlank()) {
            String key = route + ":" + action;
            List<String> roles = SERVLET_PERMISSIONS.get(key);
            if (roles != null) {
                return roles;
            }
        }
        
        // Vérifier sans action (toutes les actions)
        return SERVLET_PERMISSIONS.get(route);
    }

    /**
     * Vérifie si une JSP nécessite des permissions spécifiques
     * 
     * @param jspPath Le chemin de la JSP (ex: "/FormEmployee.jsp")
     * @return Liste des rôles requis, ou null si aucune restriction
     */
    public static List<String> getRequiredRolesForJsp(String jspPath) {
        return JSP_PERMISSIONS.get(jspPath);
    }

    /**
     * Vérifie si une route nécessite des permissions (pour toutes les actions)
     * 
     * @param route La route
     * @return true si la route nécessite des permissions pour au moins une action
     */
    public static boolean requiresPermission(String route) {
        return SERVLET_PERMISSIONS.containsKey(route) || 
               SERVLET_PERMISSIONS.keySet().stream().anyMatch(k -> k.startsWith(route + ":"));
    }

    /**
     * Retourne toutes les routes protégées (pour le debug)
     */
    public static Set<String> getAllProtectedRoutes() {
        return SERVLET_PERMISSIONS.keySet();
    }

    /**
     * Retourne toutes les JSP protégées (pour le debug)
     */
    public static Set<String> getAllProtectedJsp() {
        return JSP_PERMISSIONS.keySet();
    }
}

