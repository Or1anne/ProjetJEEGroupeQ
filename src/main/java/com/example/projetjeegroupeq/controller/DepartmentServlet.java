package com.example.projetjeegroupeq.controller;

import com.example.projetjeegroupeq.dao.implementation.DepartmentDAO;
import com.example.projetjeegroupeq.model.Department;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.example.projetjeegroupeq.model.Employee;
import com.example.projetjeegroupeq.dao.implementation.EmployeeDAO;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;


@WebServlet(name = "departmentServlet", value = "/department")
public class DepartmentServlet extends  HttpServlet {


    private final DepartmentDAO departmentDAO = new DepartmentDAO();
    private final EmployeeDAO employeeDAO = new EmployeeDAO();


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding(StandardCharsets.UTF_8.name());
        String action = request.getParameter("action");
        if (action == null || action.isBlank()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Action manquante");
            return;
        }
        Department receiver = new Department();
        boolean editMode = "edit".equalsIgnoreCase(action);
        try {
            populateDepartmentFromRequest(request, receiver);
            switch (action.toLowerCase()) {
                case "add" -> addDepartementForm(request, response, null, false);
                case "edit" -> handleEditDepartment(request, response);
                case "delete" -> handleDeleteDepartement(request, response);
            }
        }catch (IllegalArgumentException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
            }

        }



    }

    private void populateDepartmentFromRequest(HttpServletRequest request, Department receiver) {
        // parse numeric id if present
        String idParam = request.getParameter("id");
        if (idParam != null && !idParam.isBlank()) {
            try {
                receiver.setId(Integer.parseInt(idParam.trim()));
            } catch (NumberFormatException ignored) {
                // keep unset on parse error
            }
        }

        // set department name (setter expects a String)
        String name = request.getParameter("departmentName");
        if (name != null) {
            receiver.setDepartmentName(name.trim());
        }

        // If your Department#setChefDepartment expects an Employee, you must obtain/create an Employee instance:
        //
        String chefId = request.getParameter("chefId");
        if (chefId != null && !chefId.isBlank()) {
            try {
                int chefInt = Integer.parseInt(chefId.trim());
                Employee chef = employeeDAO.searchById(chefInt);
                if (chef != null) {
                    receiver.setChefDepartment(chef);
                } else {
                    // leave chef null if not found (or throw if you prefer)
                }
            } catch (NumberFormatException ignored) {
                // invalid chef id - ignore or handle as needed
            }
        }
    }
    private int parseId(String idParam, String errorMessage) {
        String trimmed = trimToNull(idParam);
        if (trimmed == null) {
            throw new IllegalArgumentException(errorMessage);
        }
        try {
            return Integer.parseInt(trimmed);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Identifiant invalide.");
        }
    }

    private String extractRequiredDepartmentParameter(HttpServletRequest request, String paramName, String errorMessage) {
        String value = trimToNull(request.getParameter(paramName));
        if (value == null) {
            throw new IllegalArgumentException(errorMessage);
        }
        return value;
    }

    private String trimToNull(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            String action = request.getParameter("action");

            if (action == null || action.isBlank() || "list".equalsIgnoreCase(action)) {
                showDepartmentList(request, response);
                return;
            }

            switch (action.toLowerCase()) {
                case "add" -> showAddDepartmentForm(request, response, null, false);
                case "edit" -> showEditDepartment(request, response);
                case "delete" -> handleDeleteDepartement(request, response);
                case "view" -> handleViewDepartment(request, response);
                default -> response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Action invalide");
            }
    }

    private void showEditDepartment(HttpServletRequest request, HttpServletResponse response) {
    }

    private void showAddDepartmentForm(HttpServletRequest request, HttpServletResponse response, Object o, boolean b) {
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