package com.example.projetjeegroupeq.util;

import com.example.projetjeegroupeq.dao.implementation.DepartmentDAO;
import com.example.projetjeegroupeq.dao.implementation.ProjectDAO;
import com.example.projetjeegroupeq.model.Department;
import com.example.projetjeegroupeq.model.Employee;
import com.example.projetjeegroupeq.model.Project;

import java.util.List;

/**
 * Classe utilitaire pour vérifier les fonctions d'un employé (chef de département, chef de projet).
 */
public class FunctionUtil {

    private static final DepartmentDAO departmentDAO = new DepartmentDAO();
    private static final ProjectDAO projectDAO = new ProjectDAO();

    /**
     * Vérifie si un employé est chef d'un département spécifique.
     *
     * @param employee L'employé à vérifier
     * @param departmentId L'ID du département
     * @return true si l'employé est chef de ce département, false sinon
     */
    public static boolean isDepartmentChief(Employee employee, int departmentId) {
        if (employee == null) {
            return false;
        }

        Department department = departmentDAO.searchById(departmentId);
        if (department == null || department.getChefDepartment() == null) {
            return false;
        }

        return department.getChefDepartment().getId() == employee.getId();
    }

    /**
     * Vérifie si un employé est chef d'un projet spécifique.
     *
     * @param employee L'employé à vérifier
     * @param projectId L'ID du projet
     * @return true si l'employé est chef de ce projet, false sinon
     */
    public static boolean isProjectChief(Employee employee, int projectId) {
        if (employee == null) {
            return false;
        }

        Project project = projectDAO.searchById(projectId);
        if (project == null || project.getChefProj() == null) {
            return false;
        }

        return project.getChefProj().getId() == employee.getId();
    }

    /**
     * Vérifie si un employé est chef d'au moins un département.
     *
     * @param employee L'employé à vérifier
     * @return true si l'employé est chef d'au moins un département, false sinon
     */
    public static boolean isAnyDepartmentChief(Employee employee) {
        if (employee == null) {
            return false;
        }

        List<Department> departments = departmentDAO.getAll();
        for (Department department : departments) {
            if (department.getChefDepartment() != null && 
                department.getChefDepartment().getId() == employee.getId()) {
                return true;
            }
        }

        return false;
    }

    /**
     * Vérifie si un employé est chef d'au moins un projet.
     *
     * @param employee L'employé à vérifier
     * @return true si l'employé est chef d'au moins un projet, false sinon
     */
    public static boolean isAnyProjectChief(Employee employee) {
        if (employee == null) {
            return false;
        }

        // Utiliser la relation inverse depuis Employee
        if (employee.getProjectsManaged() != null && !employee.getProjectsManaged().isEmpty()) {
            return true;
        }

        // Fallback : vérifier dans tous les projets
        List<Project> projects = projectDAO.getAll();
        for (Project project : projects) {
            if (project.getChefProj() != null && 
                project.getChefProj().getId() == employee.getId()) {
                return true;
            }
        }

        return false;
    }

    /**
     * Vérifie si un employé est chef du département d'un autre employé.
     *
     * @param chief L'employé à vérifier (potentiel chef)
     * @param employee L'employé dont on vérifie le département
     * @return true si le chef est chef du département de l'employé, false sinon
     */
    public static boolean isEmployeeDepartmentChief(Employee chief, Employee employee) {
        if (chief == null || employee == null || employee.getDepartment() == null) {
            return false;
        }

        return isDepartmentChief(chief, employee.getDepartment().getId());
    }
}

