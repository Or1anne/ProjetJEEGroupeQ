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
import java.util.Comparator;
import java.util.List;


@WebServlet(name = "departmentServlet", value = "/department")
public class DepartmentServlet extends  HttpServlet {


    private final DepartmentDAO departmentDAO = new DepartmentDAO();
    private final EmployeeDAO employeeDAO = new EmployeeDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding(StandardCharsets.UTF_8.name());
        String action = trimToNull(request.getParameter("action"));

        if (action == null || action.isBlank()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Action manquante");
            return;
        }
        Department receiver = new Department();
        boolean editMode = "edit".equalsIgnoreCase(action);
        try {
            populateDepartmentFromRequest(request, receiver);
            switch (action.toLowerCase()) {
                case "add" -> addDepartment(request, response, receiver);
                case "edit" -> handleEditDepartment(request, response,receiver);
                case "delete" -> handleDeleteDepartement(request, response);
                default ->response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Action invalide");
            }
        }catch (IllegalArgumentException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
            addDepartementForm(request, response, receiver, editMode);
            }

        }


    private void populateDepartmentFromRequest(HttpServletRequest request, Department receiver) {
        // parse numeric id if present
        String idParam = request.getParameter("id");
        if (idParam != null && !idParam.isBlank()) {
            try {
                receiver.setId(Integer.parseInt(idParam.trim()));
            } catch (NumberFormatException ignored) {
            }
        }

        String name = request.getParameter("departmentName");
        if (name != null) {
            receiver.setDepartmentName(name.trim());
        }

        String chefId = request.getParameter("chefId");
        if (chefId != null && !chefId.isBlank()) {
            try {
                int chefInt = Integer.parseInt(chefId.trim());
                Employee chef = employeeDAO.searchById(chefInt);
                if (chef != null) {
                    receiver.setChefDepartment(chef);
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
    private String trimToNull(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            String action = request.getParameter("action");

            if (action == null || action.isBlank() || "list".equalsIgnoreCase(action)) {
                showDepartmentList(request, response);
                return;
            }

            switch (action.toLowerCase()) {
                case "add" -> showAddDepartmentForm(request, response);
                case "edit" -> showEditDepartment(request, response);
                case "view" -> handleViewDepartment(request, response);
                case"list"-> showDepartmentList(request, response);
                default -> response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Action invalide");
            }
    }

    private void showEditDepartment(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            int id = parseId(request.getParameter("id"), "Identifiant département manquant pour l'édition");
            Department department = departmentDAO.searchById(id);
            if (department == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Département introuvable");
                return;
            }
            addDepartementForm(request, response, department, true);
        } catch (IllegalArgumentException | ServletException | IOException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        }
    }

    private void showAddDepartmentForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        addDepartementForm(request, response, null, false);
    }


    private void showDepartmentList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Department> departments = departmentDAO.getAll();

        String filterField = trimToNull(request.getParameter("filterField"));
        String filterValue = trimToNull(request.getParameter("filterValue"));
        String sortField = trimToNull(request.getParameter("sortField"));
        String sortOrder = trimToNull(request.getParameter("sortOrder"));

        if (filterField != null && filterValue != null) {
            switch (filterField) {
                case "name" -> {
                    String lower = filterValue.toLowerCase();
                    departments = departments.stream()
                            .filter(d -> d.getDepartmentName() != null && d.getDepartmentName().toLowerCase().contains(lower))
                            .toList();
                }
                default -> {}
            }
        }

        if (sortField != null) {
            Comparator<Department> comparator = null;
            switch (sortField) {
                case "id" -> comparator = Comparator.comparing(Department::getId);
                case "name" -> comparator = Comparator.comparing(
                        d -> d.getDepartmentName() != null ? d.getDepartmentName().toLowerCase() : ""
                );
                default -> {}
            }

            if (comparator != null) {
                if ("desc".equalsIgnoreCase(sortOrder)) {
                    comparator = comparator.reversed();
                }
                departments = departments.stream().sorted(comparator).toList();
            }
        }

        request.setAttribute("departments", departments);
        request.setAttribute("filterField", filterField);
        request.setAttribute("filterValue", filterValue);
        request.setAttribute("sortField", sortField);
        request.setAttribute("sortOrder", sortOrder);

        request.getRequestDispatcher("/ListDepartment.jsp").forward(request, response);
    }

    private void addDepartementForm(HttpServletRequest request, HttpServletResponse response, Department department, boolean editMode) throws ServletException, IOException {
        request.setAttribute("department", department != null ? department : new Department());
        request.setAttribute("departments", departmentDAO.getAll());
        request.setAttribute("employees", employeeDAO.getAll());
        request.setAttribute("formMode", editMode ? "edit" : "add");
        request.getRequestDispatcher("/FormDepartment.jsp").forward(request, response);
    }

    private void handleViewDepartment (HttpServletRequest request, HttpServletResponse response) throws  ServletException, IOException {
            int id = parseId(request.getParameter("id"), "Identifiant department manquant pour la visualisation");
            Department department = departmentDAO.searchById(id);
            if (department == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Department introuvable");
                return;
            }

        // Récupérer les employés du département
        List<Employee> members = employeeDAO.searchByDepartmentId(department);

        request.setAttribute("department", department);
        request.setAttribute("employees", members);
            request.getRequestDispatcher("/ViewDepartment.jsp").forward(request, response);
        }


        private void handleDeleteDepartement (HttpServletRequest request, HttpServletResponse response) throws
        ServletException, IOException {
            try {
                int id = parseId(request.getParameter("id"), "Identifiant department manquant pour la suppression");

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
        private void handleEditDepartment (HttpServletRequest request, HttpServletResponse response, Department receiver) throws
        ServletException, IOException {
            int id = parseId(request.getParameter("id"), "Identifiant département manquant pour l'édition");
            Department existing = departmentDAO.searchById(id);
            if (existing == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Employé introuvable");
                return;
            }
            // On met à jour uniquement les champs modifiables
            existing.setDepartmentName(receiver.getDepartmentName());
            existing.setChefDepartment(receiver.getChefDepartment());

            departmentDAO.update(existing, existing); // ou (existing, payload) selon ta signature
            response.sendRedirect(request.getContextPath() + "/department?action=list");

        }

        private void addDepartment(HttpServletRequest request, HttpServletResponse response, Department receiver) throws IOException {
            departmentDAO.add(receiver);
            response.sendRedirect(request.getContextPath() + "/department?action=list");
        }

}