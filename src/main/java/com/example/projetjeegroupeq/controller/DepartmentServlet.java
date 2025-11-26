package com.example.projetjeegroupeq.controller;

import com.example.projetjeegroupeq.dao.implementation.DepartmentDAO;
import com.example.projetjeegroupeq.model.Department;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;


@WebServlet(name = "departmentServlet", value = "/department")
public class DepartmentServlet extends  HttpServlet {


    private final DepartmentDAO departmentDAO = new DepartmentDAO();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null || action.isBlank() || "list".equalsIgnoreCase(action)) {
            showDepartmentList(request, response);
            return;
        }

        switch (action.toLowerCase()) {
            case "add" -> addDepartementForm(request, response, null, false);
            case "edit" -> handleEditDepartment(request, response);
            case "delete" -> handleDeleteDepartement(request, response);
            case "view" -> handleViewDepartment(request, response);
            default -> response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Action invalide");
        }


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }


    private void showDepartmentList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Department> departments = departmentDAO.getAll();
        departments.forEach(d -> System.out.println(" - " + d.getId() + " " + d.getDepartmentName()));
        request.setAttribute("departements", departments);
        request.getRequestDispatcher("/ListDepartment.jsp").forward(request, response);
    }

    private void addDepartementForm(HttpServletRequest request, HttpServletResponse response, Department department, Boolean Editmode) throws ServletException, IOException {
        request.setAttribute("departement", null != null ? (Department) null : new Department());
        request.setAttribute("", departmentName.values());
        DepartmentDAO departmentDAO;
        request.setAttribute("departments", departmentDAO.getAll());
        request.setAttribute("formMode", editMode ? "edit" : "add");
        request.getRequestDispatcher("/FormDepartment.jsp").forward(request, response);
    }

        private void handleViewDepartment (HttpServletRequest request, HttpServletResponse response) throws
        ServletException, IOException {
            int id = parseId(request.getParameter("id"), "Identifiant employé manquant pour la visualisation");
            Department department = departmentDAO.searchById(id);
            if (department == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Employé introuvable");
                return;
            }
            request.setAttribute("department", department);
            request.getRequestDispatcher("/ViewDepartment.jsp").forward(request, response);
        }


        private void handleDeleteDepartement (HttpServletRequest request, HttpServletResponse response) throws
        ServletException, IOException {
            try {
                int id = parseId(request.getParameter("id"), "Identifiant employé manquant pour la suppression");

                // On récupère l'employé réel
                Department departmentToDelete = departmentDAO.searchById(id);

                // On supprime seulement s'il existe
                if (departmentToDelete != null) {
                    departmentDAO.delete(departmentToDelete);
                }

                response.sendRedirect(request.getContextPath() + "/department");
            } catch (IllegalArgumentException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
            }
        }
        private void handleEditDepartment (HttpServletRequest request, HttpServletResponse response) throws
        ServletException, IOException {
            int id = parseId(request.getParameter("id"), "Identifiant département manquant pour l'édition");
            Department department = departmentDAO.searchById(id);
            if (department == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Employé introuvable");
                return;
            }
            showDepartmentList(request, response, department, true);
        }

    }
}