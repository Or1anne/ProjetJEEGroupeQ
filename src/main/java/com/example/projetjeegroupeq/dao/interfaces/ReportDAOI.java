package com.example.projetjeegroupeq.dao.interfaces;

import com.example.projetjeegroupeq.model.Department;
import com.example.projetjeegroupeq.model.Grade;
import com.example.projetjeegroupeq.model.Project;

import java.util.Map;

/**
 * DAO pour les rapports/statistiques.
 *
 * Fournit des méthodes de lecture agrégée (statistiques) sur les entités.
 */
public interface ReportDAOI {

    /**
     * Nombre d'employés par département.
     *
     * @return une map Department -> nombre d'employés
     * @throws RuntimeException en cas d'erreur technique
     */
    Map<Department, Long> getEmployeeCountByDepartment();

    /**
     * Nombre d'employés par projet.
     *
     * @return une map Project -> nombre d'employés affectés
     * @throws RuntimeException en cas d'erreur technique
     */
    Map<Project, Long> getEmployeeCountByProject();

    /**
     * Nombre d'employés par grade.
     *
     * @return une map Grade -> nombre d'employés
     * @throws RuntimeException en cas d'erreur technique
     */
    Map<Grade, Long> getEmployeeCountByGrade();
}
