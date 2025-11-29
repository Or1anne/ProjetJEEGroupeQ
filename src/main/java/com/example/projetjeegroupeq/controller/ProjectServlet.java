package com.example.projetjeegroupeq.controller;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

import com.example.projetjeegroupeq.dao.implementation.EmployeeDAO;
import com.example.projetjeegroupeq.dao.sortingType.ProjectSortingType;
import com.example.projetjeegroupeq.model.Employee;
import com.example.projetjeegroupeq.model.Project;
import com.example.projetjeegroupeq.model.ProjectStatus;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import com.example.projetjeegroupeq.dao.implementation.ProjectDAO;

@WebServlet(name = "ProjectServlet", value = "/project")
public class ProjectServlet extends HttpServlet {
    private final ProjectDAO projectDAO = new ProjectDAO();
    private final EmployeeDAO employeeDAO = new EmployeeDAO();

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String action = req.getParameter("action");
        if (action == null || action.isBlank() || "list".equalsIgnoreCase(action)) {
            showProjectList(req, resp);
            return;
        }

        switch (action.toLowerCase()) {
            case "add" -> showProjectForm(req, resp, null, false);
            case "edit" -> handleEdit(req, resp);
            case "delete" -> handleDelete(req, resp);
            case "view" -> handleView(req, resp);
            default -> resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Action invalide");
        }
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.setCharacterEncoding(StandardCharsets.UTF_8.name());

        String action = req.getParameter("action");

        if (action == null || action.isBlank()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Action manquante");
            return;
        }

        Project payload = new Project();
        ProjectDAO projectDAO = new ProjectDAO();

        try {
            populateProjectFromRequest(req, payload);

            if ("add".equalsIgnoreCase(action)) {
                projectDAO.add(payload);
            } else if ("edit".equalsIgnoreCase(action)) {
                int id = parseId(req.getParameter("id"), "Identifiant projet manquant");

                Project original = projectDAO.searchById(id);

                if (original == null) throw new IllegalArgumentException("Le projet à modifier n'existe plus");

                projectDAO.update(original, payload);
            } else {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Action invalide");
                return;
            }
            resp.sendRedirect(req.getContextPath() + "/project?action=list");
        } catch (IllegalArgumentException ex) {
            req.setAttribute("errorMessage", ex.getMessage());
            showProjectForm(req, resp, payload, "edit".equalsIgnoreCase(action));
        }

    }

    private void showProjectList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Project> projects = projectDAO.getAll();

        projects.forEach(p -> System.out.println(" - " + p.getId() + " " + p.getName_project()));
        req.setAttribute("projects", projects);
        req.getRequestDispatcher("/ListProject.jsp").forward(req, resp);
    }

    private void showProjectForm(HttpServletRequest req, HttpServletResponse resp, Project project, boolean editMode) throws ServletException, IOException {
        req.setAttribute("project", project != null ? project : new Project());
        req.setAttribute("employees", employeeDAO.getAll());
        req.setAttribute("formMode", editMode ? "edit" : "add");
        req.getRequestDispatcher("/FormProject.jsp").forward(req, resp);
    }

    private void handleEdit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = parseId(req.getParameter("id"), "Identifiant projet manquant pour l'édition");

        Project original = projectDAO.searchById(id);

        if (original == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Projet introuvable");
            return;
        }
        showProjectForm(req, resp, original, true);
    }

    private void handleDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = parseId(req.getParameter("id"), "Identifiant projet manquant pour la suppression");

        Project original = projectDAO.searchById(id);
        projectDAO.delete(original);
        resp.sendRedirect(req.getContextPath() + "/project");
    }

    private void handleView(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = parseId(req.getParameter("id"), "Identifiant projet manquant pour la visualisation");

        Project original = projectDAO.searchById(id);

        if (original == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Projet non trouvable");
            return;
        }

        req.setAttribute("project", original);
        req.getRequestDispatcher("ViewProject.jsp").forward(req, resp);
    }

    private String trimToNull(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }

    private String extractRequiredParameter(HttpServletRequest req, String parameter, String errorMess) {
        String value = trimToNull(req.getParameter(parameter));

        if (value == null) throw new IllegalArgumentException(errorMess);

        return value;
    }

    private void populateProjectFromRequest(HttpServletRequest req, Project target) {
        target.setName_project(extractRequiredParameter(req, "projectName", "Le nom est obligatoire"));
        target.setChefProj(resolveChef(req.getParameter("managerId")));
        target.setStatus(resolveStatus(req.getParameter("projectStatus")));
    }

    private Employee resolveChef(String id) {
        int idChef = parseId(id, "Veuillez sélectionner un chef de département");
        Employee chef = employeeDAO.searchById(idChef);
        if (chef == null) throw new IllegalArgumentException("Le chef sélectionné est introuvable");
        return chef;
    }

    private ProjectStatus resolveStatus(String name) {
        String cleanValue = trimToNull(name);

        if (cleanValue == null) {
            throw new IllegalArgumentException("Le statut du projet est obligatoire");
        }

        try {
            return ProjectStatus.valueOf(cleanValue);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Statut de projet invalide : " + cleanValue);
        }
    }

    private int parseId(String id, String errorMessage) {
        String trimmed = trimToNull(id);

        if (trimmed == null) throw new IllegalArgumentException(errorMessage);

        try {
            return Integer.parseInt(trimmed);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Argument invalide");
        }
    }

}