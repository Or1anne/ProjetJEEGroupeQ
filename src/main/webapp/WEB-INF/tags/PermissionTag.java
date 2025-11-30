package com.example.projetjeegroupeq.tags;

import com.example.projetjeegroupeq.model.Employee;
import com.example.projetjeegroupeq.util.PermissionChecker;
import com.example.projetjeegroupeq.util.RoleUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.SimpleTagSupport;

import java.io.IOException;

/**
 * Tag JSP personnalisé pour vérifier les permissions dans les pages JSP.
 * 
 * Usage dans JSP :
 * <%@ taglib prefix="perm" uri="/WEB-INF/tags/PermissionTag" %>
 * 
 * <perm:hasRole roles="RH,ADMIN">
 *     <a href="...">Lien visible seulement pour RH et ADMIN</a>
 * </perm:hasRole>
 * 
 * <perm:hasPermission route="/employee" action="add">
 *     <a href="...">Lien visible selon les permissions</a>
 * </perm:hasPermission>
 */
public class PermissionTag extends SimpleTagSupport {
    
    private String roles;
    private String route;
    private String action;

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @Override
    public void doTag() throws JspException, IOException {
        HttpServletRequest request = (HttpServletRequest) getJspContext().getAttribute("request");
        if (request == null) {
            // Essayer de récupérer depuis le pageContext
            Object pageContext = getJspContext();
            if (pageContext instanceof jakarta.servlet.jsp.PageContext) {
                request = ((jakarta.servlet.jsp.PageContext) pageContext).getRequest();
            }
        }

        if (request == null) {
            return; // Ne rien afficher si on ne peut pas vérifier
        }

        boolean hasPermission = false;

        // Vérifier par rôles
        if (roles != null && !roles.isBlank()) {
            Employee loggedUser = PermissionChecker.getLoggedUser(request);
            if (loggedUser != null) {
                String[] roleArray = roles.split(",");
                for (int i = 0; i < roleArray.length; i++) {
                    roleArray[i] = roleArray[i].trim();
                }
                hasPermission = RoleUtil.hasAnyRole(loggedUser, roleArray);
            }
        }
        // Vérifier par route/action
        else if (route != null && !route.isBlank()) {
            hasPermission = PermissionChecker.hasPermission(request, route, action);
        }

        if (hasPermission) {
            getJspBody().invoke(null);
        }
    }
}

