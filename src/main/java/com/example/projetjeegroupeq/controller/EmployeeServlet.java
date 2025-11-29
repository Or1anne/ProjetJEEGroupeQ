package com.example.projetjeegroupeq.controller;

import com.example.projetjeegroupeq.dao.implementation.DepartmentDAO;
import com.example.projetjeegroupeq.dao.implementation.EmployeeDAO;
import com.example.projetjeegroupeq.dao.implementation.RoleDAO;
import com.example.projetjeegroupeq.model.Department;
import com.example.projetjeegroupeq.model.Employee;
import com.example.projetjeegroupeq.model.Grade;
import com.example.projetjeegroupeq.model.Role;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@WebServlet(name = "employeeServlet", value = "/employee")
public class EmployeeServlet extends HttpServlet {
    private final EmployeeDAO employeeDAO = new EmployeeDAO();
    private final DepartmentDAO departmentDAO = new DepartmentDAO();
    private final RoleDAO roleDAO = new RoleDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null || action.isBlank() || "list".equalsIgnoreCase(action)) {
            showEmployeeList(req, resp);
            return;
        }

/*
        List<Employee> list = employeeDAO.getAllEmployees();
        req.setAttribute("employees", list);
        req.getRequestDispatcher("/ListEmployee.jsp").forward(req, resp);
*/

        switch (action.toLowerCase()) {
            case "add" -> showEmployeeForm(req, resp, null, false);
            case "edit" -> handleEdit(req, resp);
            case "delete" -> handleDelete(req, resp);
            case "view" -> handleView(req, resp);
            default -> resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Action invalide");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding(StandardCharsets.UTF_8.name());
        String action = req.getParameter("action");
        if (action == null || action.isBlank()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Action manquante");
            return;
        }

        // "payload" contient les données du formulaire (Nom, Prénom, Poste, Salaire, Dept)
        Employee payload = new Employee();
        boolean editMode = "edit".equalsIgnoreCase(action);

        try {
            // Remplissage des données de base depuis le formulaire
            populateEmployeeFromRequest(req, payload);

            if ("add".equalsIgnoreCase(action)) {
                String firstName = payload.getFirstName().trim().toLowerCase();
                String lastName = payload.getLastName().trim().toLowerCase();

                String username = firstName.substring(0, 1)
                        + lastName.substring(0, Math.min(7, lastName.length()));

                payload.setUsername(username);

                // Mot de passe : nom du département sans espaces
                if (payload.getDepartment() != null) {
                    String deptName = payload.getDepartment().getDepartmentName();
                    String password = deptName.replace(" ", "");
                    payload.setPassword(password);
                }

                employeeDAO.add(payload);

                // Assigner le rôle si fourni
                String roleIdStr = req.getParameter("roleId");
                if (roleIdStr != null && !roleIdStr.isBlank()) {
                    try {
                        int roleId = Integer.parseInt(roleIdStr);
                        Role role = roleDAO.searchById(roleId);
                        if (role != null) {
                            roleDAO.assignRoleToEmployee(payload, role);
                        }
                    } catch (NumberFormatException ignore) {}
                }

            } else if (editMode) {
                int id = parseId(req.getParameter("id"), "Identifiant employé manquant");
                //employeeDAO.update(payload, payload);

                // 1. Récupérer l'employé actuel en base pour ne pas perdre ses infos sensibles (mdp, username)
                Employee existingEmployee = employeeDAO.searchById(id);
                if (existingEmployee == null) {
                    throw new IllegalArgumentException("L'employé à modifier n'existe plus.");
                }

                // 2. Compléter le payload avec les infos techniques existantes
                payload.setUsername(existingEmployee.getUsername());
                payload.setPassword(existingEmployee.getPassword());
                //payload.setEmployeeRoles(existingEmployee.getEmployeeRoles());

                // 3. Appeler le DAO : update(original, nouvelles_valeurs)
                employeeDAO.update(existingEmployee, payload);

                // 4. Assigner le rôle si fourni
                String roleIdStr = req.getParameter("roleId");
                if (roleIdStr != null && !roleIdStr.isBlank()) {
                    try {
                        int roleId = Integer.parseInt(roleIdStr);
                        Role role = roleDAO.searchById(roleId);
                        if (role != null) {
                            roleDAO.assignRoleToEmployee(existingEmployee, role);
                        }
                    } catch (NumberFormatException ignore) {}
                }

            } else {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Action invalide");
                return;
            }
            resp.sendRedirect(req.getContextPath() + "/employee?action=list");
        } catch (IllegalArgumentException ex) {
            req.setAttribute("errorMessage", ex.getMessage());
            showEmployeeForm(req, resp, payload, editMode);
        }
    }

    private void showEmployeeList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Employee> employees = employeeDAO.getAll();
        //System.out.println("[EmployeeServlet] Nombre d'employés remontés : " + employees.size());
        employees.forEach(e -> System.out.println(" - " + e.getId() + " " + e.getLastName()));
        req.setAttribute("employees", employees);
        req.getRequestDispatcher("/ListEmployee.jsp").forward(req, resp);
    }

    private void handleEdit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = parseId(req.getParameter("id"), "Identifiant employé manquant pour l'édition");
        Employee employee = employeeDAO.searchById(id);
        if (employee == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Employé introuvable");
            return;
        }
        showEmployeeForm(req, resp, employee, true);
    }

    private void handleDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
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

    private void handleView(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = parseId(req.getParameter("id"), "Identifiant employé manquant pour la visualisation");
        Employee employee = employeeDAO.searchById(id);
        if (employee == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Employé introuvable");
            return;
        }
        req.setAttribute("employee", employee);
        req.getRequestDispatcher("/ViewEmployee.jsp").forward(req, resp);
    }

    private void showEmployeeForm(HttpServletRequest req, HttpServletResponse resp, Employee employee, boolean editMode) throws ServletException, IOException {
        req.setAttribute("employee", employee != null ? employee : new Employee());
        req.setAttribute("grades", Grade.values());
        req.setAttribute("departments", departmentDAO.getAll());
        req.setAttribute("roles", roleDAO.getAll());
        req.setAttribute("formMode", editMode ? "edit" : "add");
        req.getRequestDispatcher("/FormEmployee.jsp").forward(req, resp);
    }

    private void populateEmployeeFromRequest(HttpServletRequest req, Employee target) {
        target.setLastName(extractRequiredParameter(req, "lastname", "Le nom est obligatoire."));
        target.setFirstName(extractRequiredParameter(req, "firstname", "Le prénom est obligatoire."));

        String gradeParam = extractRequiredParameter(req, "grade", "Le grade est obligatoire.");
        try {
            Grade gradeEnum = Grade.valueOf(gradeParam); // convertit la String en enum
            target.setGrade(gradeEnum);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Grade invalide.");
        }

        target.setPost(trimToNull(req.getParameter("post")));
        target.setSalary(parseSalary(req.getParameter("salary")));
        target.setDepartment(resolveDepartment(req.getParameter("departmentId")));
    }

    private String extractRequiredParameter(HttpServletRequest req, String paramName, String errorMessage) {
        String value = trimToNull(req.getParameter(paramName));
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

    private Double parseSalary(String salaryParam) {
        String trimmed = trimToNull(salaryParam);
        if (trimmed == null) {
            return null;
        }
        try {
            return Double.valueOf(trimmed);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Le salaire doit être un nombre valide.");
        }
    }

    private Department resolveDepartment(String departmentParam) {
        int departmentId = parseId(departmentParam, "Veuillez sélectionner un département.");
        Department department = departmentDAO.searchById(departmentId);
        if (department == null) {
            throw new IllegalArgumentException("Le département sélectionné est introuvable.");
        }
        return department;
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


}