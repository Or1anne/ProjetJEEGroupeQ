package com.example.projetjeegroupeq.util;

import com.example.projetjeegroupeq.model.Employee;
import com.example.projetjeegroupeq.model.EmployeeRole;
import com.example.projetjeegroupeq.model.Role;

import java.util.List;

/**
 * Classe utilitaire pour la gestion des permissions basées sur les rôles.
 */
public class RoleUtil {

    /**
     * Vérifie si un employé a un rôle spécifique.
     *
     * @param employee L'employé à vérifier
     * @param roleName Le nom du rôle à vérifier (ex: "ADMIN", "RH", "EMPLOYE")
     * @return true si l'employé a le rôle, false sinon
     */
    public static boolean hasRole(Employee employee, String roleName) {
        if (employee == null || roleName == null) {
            return false;
        }

        List<EmployeeRole> employeeRoles = employee.getEmployeeRoles();
        if (employeeRoles == null || employeeRoles.isEmpty()) {
            return false;
        }

        for (EmployeeRole employeeRole : employeeRoles) {
            Role role = employeeRole.getRole();
            if (role != null && roleName.equalsIgnoreCase(role.getRoleName())) {
                return true;
            }
        }

        return false;
    }

    /**
     * Vérifie si un employé a au moins un des rôles spécifiés.
     *
     * @param employee L'employé à vérifier
     * @param roleNames Les noms des rôles à vérifier
     * @return true si l'employé a au moins un des rôles, false sinon
     */
    public static boolean hasAnyRole(Employee employee, String... roleNames) {
        if (employee == null || roleNames == null || roleNames.length == 0) {
            return false;
        }

        for (String roleName : roleNames) {
            if (hasRole(employee, roleName)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Vérifie si un employé a tous les rôles spécifiés.
     *
     * @param employee L'employé à vérifier
     * @param roleNames Les noms des rôles à vérifier
     * @return true si l'employé a tous les rôles, false sinon
     */
    public static boolean hasAllRoles(Employee employee, String... roleNames) {
        if (employee == null || roleNames == null || roleNames.length == 0) {
            return false;
        }

        for (String roleName : roleNames) {
            if (!hasRole(employee, roleName)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Vérifie si un employé est un administrateur.
     *
     * @param employee L'employé à vérifier
     * @return true si l'employé est ADMIN, false sinon
     */
    public static boolean isAdmin(Employee employee) {
        return hasRole(employee, "ADMIN");
    }

    /**
     * Vérifie si un employé est RH (Ressources Humaines).
     *
     * @param employee L'employé à vérifier
     * @return true si l'employé est RH, false sinon
     */
    public static boolean isRH(Employee employee) {
        return hasRole(employee, "RH");
    }

    /**
     * Vérifie si un employé est un simple employé.
     *
     * @param employee L'employé à vérifier
     * @return true si l'employé est EMPLOYE, false sinon
     */
    public static boolean isEmployee(Employee employee) {
        return hasRole(employee, "EMPLOYE");
    }
}

