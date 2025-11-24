package com.example.projetjeegroupeq.controller;


import com.example.projetjeegroupeq.dao.implementation.DepartmentDAO;
import com.example.projetjeegroupeq.model.Department;
import com.example.projetjeegroupeq.model.Employee;
import com.example.projetjeegroupeq.model.Grade;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet
public class DepartmentServlet extends HttpServlet {

    private final DepartmentDAO departmentDAO = new DepartmentDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action =  request.getParameter("action");

        if (action == null || action.isBlank() || "list".equalsIgnoreCase(action)) {
            showDepartmentList(request, response);
            return;
        }

        switch ( action.toLowerCase()) {
            case "add" -> addDepartementForm(request, response, null, false);
            case "edit" -> handleEditDepartment(request, response);
            case "delete" -> handleDeleteDepartement(request, response);
            case "view" -> handleViewDepartment(request, response);
            default -> response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Action invalide");
        }


    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {}
}



    private void showDepartmentList(HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        List<Department> departments = DepartmentDAO.getAll();
        //System.out.println("[EmployeeServlet] Nombre d'employés remontés : " + employees.size());
        departments.forEach(d -> System.out.println(" - " + d.getId() + " " + d.getLastName()));
        request.setAttribute("employees", employees);
        request.getRequestDispatcher("/ListEmployee.jsp").forward(req, resp);
    }
    private void addDepartementForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        req.setAttribute("employee", employee != null ? employee : new Employee());
        req.setAttribute("grades", Grade.values());
        req.setAttribute("departments", departmentDAO.getAll());
        req.setAttribute("formMode", editMode ? "edit" : "add");
        req.getRequestDispatcher("/FormEmployee.jsp").forward(req, resp);
    }
    private void handleViewDepartment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = parseId(req.getParameter("id"), "Identifiant employé manquant pour la visualisation");
        Employee employee = employeeDAO.searchById(id);
        if (employee == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Employé introuvable");
            return;
        }
        req.setAttribute("employee", employee);
        req.getRequestDispatcher("/ViewEmployee.jsp").forward(req, resp);
    }
    }

    private void handleDeleteDepartement(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int id = parseId(req.getParameter("id"), "Identifiant employé manquant pour la suppression");

            // On récupère l'employé réel
            Employee employeeToDelete = employeeDAO.searchById(id);

            // On supprime seulement s'il existe
            if (employeeToDelete != null) {
                employeeDAO.delete(employeeToDelete);
            }

            resp.sendRedirect(req.getContextPath() + "/employee");
        } catch (IllegalArgumentException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        }
    }
    private void handleEditDepartment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = parseId(request.getParameter("id"), "Identifiant employé manquant pour l'édition");
        Department department = departmentDAO.searchById(id);
        if (department == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Employé introuvable");
            return;
        }
        showDepartmentList(request, response, department, true);
    }

    }

