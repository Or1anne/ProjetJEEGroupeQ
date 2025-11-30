package com.example.projetjeegroupeq.controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter(urlPatterns = {
        "/employee/*",
        "/department/*",
        "/project/*",
        "/pay/*",
        "/AffectEmployeeProject.jsp",
        "/changePassword.jsp",
        "/editProfile.jsp",
        "/FormConnection.jsp",
        "/FormDepartment.jsp",
        "/FormEmployee.jsp",
        "/FormModifyDepartment.jsp",
        "/FormModifyEmployee.jsp",
        "/FormPay.jsp",
        "/FormProject.jsp",
        "/Gestion.jsp",
        "/ListDepartment.jsp",
        "/ListEmployee.jsp",
        "/ListPay.jsp",
        "/ListProject.jsp",
        "/Profile.jsp",
        "/Search.jsp",
        "/TrackProject.jsp",
        "/ViewDepartment.jsp",
        "/ViewEmployee.jsp",
        "/ViewPay.jsp",
        "/ViewProject.jsp",
        "/Report.jsp",
})
public class AuthFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        HttpSession session = req.getSession(false);

        // Vérification connexion
        boolean logged = (session != null && session.getAttribute("loggedUser") != null);

        if (!logged) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        chain.doFilter(request, response);
    }
}
