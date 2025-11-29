package com.example.projetjeegroupeq.dao.implementation;

import com.example.projetjeegroupeq.dao.interfaces.ReportDAOI;
import com.example.projetjeegroupeq.model.Department;
import com.example.projetjeegroupeq.model.EmployeeProject;
import com.example.projetjeegroupeq.model.Grade;
import com.example.projetjeegroupeq.model.Project;
import com.example.projetjeegroupeq.util.HibernateUtil;
import jakarta.persistence.EntityManager;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Implémentation du DAO de rapports/statistiques.
 *
 * Toutes les méthodes sont en lecture seule (SELECT avec GROUP BY),
 * donc pas de gestion explicite de transaction.
 */
public class ReportDAO implements ReportDAOI {

    public ReportDAO() {}

    /**
     * Nombre d'employés par département.
     */
    @Override
    public Map<Department, Long> getEmployeeCountByDepartment() {
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();

            String jpql =
                    "SELECT e.department, COUNT(e) " +
                            "FROM Employee e " +
                            "GROUP BY e.department " +
                            "ORDER BY e.department.departmentName";

            List<Object[]> rows = em.createQuery(jpql, Object[].class).getResultList();

            Map<Department, Long> result = new LinkedHashMap<>();
            for (Object[] row : rows) {
                Department dept = (Department) row[0];
                Long count = (Long) row[1];
                result.put(dept, count);
            }

            return result;
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors du calcul du nombre d'employés par département : " + e.getMessage());
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * Nombre d'employés par projet.
     */
    @Override
    public Map<Project, Long> getEmployeeCountByProject() {
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();

            String jpql =
                    "SELECT ep.project, COUNT(ep.employee) " +
                            "FROM EmployeeProject ep " +
                            "GROUP BY ep.project " +
                            "ORDER BY ep.project.name_project";

            List<Object[]> rows = em.createQuery(jpql, Object[].class).getResultList();

            Map<Project, Long> result = new LinkedHashMap<>();
            for (Object[] row : rows) {
                Project project = (Project) row[0];
                Long count = (Long) row[1];
                result.put(project, count);
            }

            return result;
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors du calcul du nombre d'employés par projet : " + e.getMessage());
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * Nombre d'employés par grade.
     */
    @Override
    public Map<Grade, Long> getEmployeeCountByGrade() {
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();

            String jpql =
                    "SELECT e.grade, COUNT(e) " +
                            "FROM Employee e " +
                            "GROUP BY e.grade " +
                            "ORDER BY e.grade";

            List<Object[]> rows = em.createQuery(jpql, Object[].class).getResultList();

            Map<Grade, Long> result = new LinkedHashMap<>();
            for (Object[] row : rows) {
                Grade grade = (Grade) row[0];
                Long count = (Long) row[1];
                result.put(grade, count);
            }

            return result;
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors du calcul du nombre d'employés par grade : " + e.getMessage());
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
}
