package com.example.projetjeegroupeq.controller;

import com.example.projetjeegroupeq.dao.implementation.EmployeeDAO;
import com.example.projetjeegroupeq.model.Employee;
import com.example.projetjeegroupeq.util.PermissionChecker;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Servlet pour gérer le changement de mot de passe des utilisateurs.
 */
@WebServlet("/ChangePasswordServlet")
public class ChangePasswordServlet extends HttpServlet {

    private final EmployeeDAO employeeDAO = new EmployeeDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Rediriger vers la page de changement de mot de passe
        req.getRequestDispatcher("/changePassword.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Récupérer l'utilisateur connecté
        Employee loggedUser = PermissionChecker.getLoggedUser(req);
        
        if (loggedUser == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        // Récupérer les paramètres du formulaire
        String currentPassword = req.getParameter("currentPassword");
        String newPassword = req.getParameter("newPassword");
        String confirmPassword = req.getParameter("confirmPassword");
        String userIdStr = req.getParameter("id");

        // Vérifier que l'utilisateur modifie son propre mot de passe
        int userId;
        try {
            userId = Integer.parseInt(userIdStr);
        } catch (NumberFormatException e) {
            req.setAttribute("error", "Identifiant utilisateur invalide");
            req.getRequestDispatcher("/changePassword.jsp").forward(req, resp);
            return;
        }

        // Vérifier que l'utilisateur modifie son propre mot de passe
        if (loggedUser.getId() != userId) {
            req.setAttribute("error", "Vous ne pouvez modifier que votre propre mot de passe");
            req.getRequestDispatcher("/changePassword.jsp").forward(req, resp);
            return;
        }

        // Vérifier que tous les champs sont remplis
        if (currentPassword == null || currentPassword.isBlank() ||
            newPassword == null || newPassword.isBlank() ||
            confirmPassword == null || confirmPassword.isBlank()) {
            req.setAttribute("error", "Tous les champs sont obligatoires");
            req.getRequestDispatcher("/changePassword.jsp").forward(req, resp);
            return;
        }

        // Vérifier que le mot de passe actuel est correct
        if (!loggedUser.getPassword().equals(currentPassword)) {
            req.setAttribute("error", "Le mot de passe actuel est incorrect");
            req.getRequestDispatcher("/changePassword.jsp").forward(req, resp);
            return;
        }

        // Vérifier que le nouveau mot de passe a au moins 6 caractères
        if (newPassword.length() < 6) {
            req.setAttribute("error", "Le nouveau mot de passe doit contenir au moins 6 caractères");
            req.getRequestDispatcher("/changePassword.jsp").forward(req, resp);
            return;
        }

        // Vérifier que les nouveaux mots de passe correspondent
        if (!newPassword.equals(confirmPassword)) {
            req.setAttribute("error", "Les nouveaux mots de passe ne correspondent pas");
            req.getRequestDispatcher("/changePassword.jsp").forward(req, resp);
            return;
        }

        // Vérifier que le nouveau mot de passe est différent de l'ancien
        if (currentPassword.equals(newPassword)) {
            req.setAttribute("error", "Le nouveau mot de passe doit être différent de l'ancien");
            req.getRequestDispatcher("/changePassword.jsp").forward(req, resp);
            return;
        }

        try {
            // Récupérer l'employé depuis la base de données
            Employee employee = employeeDAO.searchById(userId);
            if (employee == null) {
                req.setAttribute("error", "Utilisateur introuvable");
                req.getRequestDispatcher("/changePassword.jsp").forward(req, resp);
                return;
            }

            // Mettre à jour le mot de passe
            employee.setPassword(newPassword);
            
            // Créer un objet Employee avec seulement le mot de passe modifié pour l'update
            Employee updateEmployee = new Employee();
            updateEmployee.setPassword(newPassword);
            updateEmployee.setUsername(employee.getUsername());
            updateEmployee.setLastName(employee.getLastName());
            updateEmployee.setFirstName(employee.getFirstName());
            updateEmployee.setGrade(employee.getGrade());
            updateEmployee.setPost(employee.getPost());
            updateEmployee.setSalary(employee.getSalary());
            updateEmployee.setDepartment(employee.getDepartment());

            // Mettre à jour dans la base de données
            employeeDAO.update(employee, updateEmployee);

            // Mettre à jour l'utilisateur dans la session
            HttpSession session = req.getSession();
            Employee updatedEmployee = employeeDAO.searchById(userId);
            if (updatedEmployee != null) {
                session.setAttribute("loggedUser", updatedEmployee);
            }

            // Stocker le message de succès dans la session pour éviter les problèmes d'encodage
            session.setAttribute("passwordChangeSuccess", "Mot de passe modifié avec succès");

            // Rediriger vers le profil
            resp.sendRedirect(req.getContextPath() + "/Profile.jsp");

        } catch (Exception e) {
            req.setAttribute("error", "Erreur lors de la modification du mot de passe : " + e.getMessage());
            req.getRequestDispatcher("/changePassword.jsp").forward(req, resp);
        }
    }
}

