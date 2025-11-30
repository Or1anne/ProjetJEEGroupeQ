package com.example.projetjeegroupeq.controller;

import com.example.projetjeegroupeq.util.PermissionChecker;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Filtre pour gérer les accès basés sur les rôles.
 * Protège automatiquement toutes les routes et JSP selon la configuration dans PermissionConfig.
 * 
 * Ce filtre intercepte toutes les requêtes et vérifie les permissions définies dans PermissionConfig.
 * Pour ajouter une nouvelle protection, il suffit de modifier PermissionConfig.
 */
@WebFilter(urlPatterns = {
        "/*"
})
public class RoleFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialisation si nécessaire
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        HttpSession session = req.getSession(false);

        // Vérifier que l'utilisateur est connecté (géré par AuthFilter, mais on double-vérifie)
        if (session == null || session.getAttribute("loggedUser") == null) {
            // Ne pas bloquer les pages publiques (login, index, etc.)
            String path = getNormalizedPath(req);
            if (!isPublicPath(path)) {
                resp.sendRedirect(req.getContextPath() + "/login");
                return;
            }
            chain.doFilter(request, response);
            return;
        }

        // Normaliser le chemin de la requête
        String normalizedPath = getNormalizedPath(req);
        String method = req.getMethod();

        // Vérifier les permissions pour les JSP
        if (normalizedPath.endsWith(".jsp")) {
            if (!PermissionChecker.checkPermissionForJsp(req, resp, normalizedPath)) {
                return; // L'erreur 403 a déjà été envoyée
            }
        }

        // Vérifier les permissions pour les servlets
        // On extrait la route et l'action depuis la requête
        String action = req.getParameter("action");
        
        // Actions qui nécessitent des vérifications contextuelles (gérées par les servlets)
        // Le filtre ne doit pas les bloquer, les servlets gèrent les permissions
        boolean requiresContextualCheck = isContextualAction(normalizedPath, action);
        
        if (!requiresContextualCheck) {
            // Pour les requêtes POST, on vérifie aussi l'action
            if ("POST".equalsIgnoreCase(method)) {
                String postAction = req.getParameter("action");
                if (!PermissionChecker.checkPermission(req, resp, normalizedPath, postAction)) {
                    return; // L'erreur 403 a déjà été envoyée
                }
            } else {
                // Pour les requêtes GET
                if (!PermissionChecker.checkPermission(req, resp, normalizedPath, action)) {
                    return; // L'erreur 403 a déjà été envoyée
                }
            }
        }
        // Si requiresContextualCheck est true, on laisse passer et le servlet gère les permissions

        chain.doFilter(request, response);
    }

    /**
     * Normalise le chemin de la requête en enlevant le context path
     */
    private String getNormalizedPath(HttpServletRequest req) {
        String requestURI = req.getRequestURI();
        String contextPath = req.getContextPath();
        
        if (contextPath != null && !contextPath.isEmpty() && requestURI.startsWith(contextPath)) {
            return requestURI.substring(contextPath.length());
        }
        
        return requestURI;
    }

    /**
     * Vérifie si une action nécessite des vérifications contextuelles (chef de projet/département).
     * Ces actions sont gérées par les servlets qui vérifient les permissions contextuelles.
     * 
     * @param route La route (ex: "/project")
     * @param action L'action (ex: "edit", "addemployees")
     * @return true si l'action nécessite une vérification contextuelle
     */
    private boolean isContextualAction(String route, String action) {
        if (action == null || action.isBlank()) {
            return false;
        }
        
        // Actions de projet qui nécessitent une vérification contextuelle (chef de projet)
        if ("/project".equals(route)) {
            return "edit".equalsIgnoreCase(action) || 
                   "addemployees".equalsIgnoreCase(action);
        }
        
        // Ajouter d'autres routes avec vérifications contextuelles si nécessaire
        // Exemple : "/department" avec action "edit" si on veut permettre aux chefs de département
        
        return false;
    }

    /**
     * Vérifie si un chemin est public (accessible sans authentification)
     * ou une ressource statique qui ne nécessite pas de vérification de permissions
     */
    private boolean isPublicPath(String path) {
        if (path == null) {
            return true;
        }
        
        // Pages publiques
        if (path.equals("/") || 
            path.equals("/index.jsp") || 
            path.equals("/login") || 
            path.equals("/FormConnection.jsp")) {
            return true;
        }
        
        // Ressources statiques
        if (path.startsWith("/CSS/") ||
            path.startsWith("/js/") ||
            path.startsWith("/images/") ||
            path.startsWith("/img/") ||
            path.startsWith("/assets/") ||
            path.startsWith("/WEB-INF/") ||
            path.endsWith(".css") ||
            path.endsWith(".js") ||
            path.endsWith(".png") ||
            path.endsWith(".jpg") ||
            path.endsWith(".jpeg") ||
            path.endsWith(".gif") ||
            path.endsWith(".ico") ||
            path.endsWith(".svg")) {
            return true;
        }
        
        return false;
    }

    @Override
    public void destroy() {
        // Nettoyage si nécessaire
    }
}

