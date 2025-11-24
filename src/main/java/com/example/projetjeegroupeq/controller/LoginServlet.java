package com.example.projetjeegroupeq.controller;

import com.example.projetjeegroupeq.dao.implementation.EmployeeDAO;
import com.example.projetjeegroupeq.model.Employee;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private EmployeeDAO employeeDAO = new EmployeeDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Affiche la page de login
        req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String u = req.getParameter("username");
        String p = req.getParameter("password");

        Employee emp = employeeDAO.findByUsername(u);

        // Vérification (Remplacer p.equals par BCrypt.checkpw(p, emp.getPassword()) en prod)
        if (emp != null && emp.getPassword().equals(p)) {

            // Création de la session
            HttpSession session = req.getSession();

            // On stocke l'objet employee entier
            // Cela servira de preuve de connexion
            session.setAttribute("loggedUser", emp);

            // Redirection selon le rôle
            //if (emp.hasRole("ADMIN")) {
            //    resp.sendRedirect(req.getContextPath() + "/admin/dashboard");
            //} else {
            //    resp.sendRedirect(req.getContextPath() + "/home");
            //}

        } else {
            // Echec
            req.setAttribute("error", "Identifiants invalides");
            req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
        }
    }
}