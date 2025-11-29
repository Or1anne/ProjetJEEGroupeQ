package com.example.projetjeegroupeq.dao.implementation;

import com.example.projetjeegroupeq.model.Department;
import com.example.projetjeegroupeq.model.Employee;
import com.example.projetjeegroupeq.model.EmployeeProject;
import com.example.projetjeegroupeq.model.Grade;
import com.example.projetjeegroupeq.model.Project;
import com.example.projetjeegroupeq.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ReportDAO {

    // 1) Nombre d’employés par département
    public Map<Department, Long> getEmployeeCountByDepartment() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Object[]> q = session.createQuery(
                    "select e.department, count(e) " +
                            "from Employee e " +
                            "group by e.department " +
                            "order by e.department.name",
                    Object[].class
            );

            List<Object[]> rows = q.getResultList();
            Map<Department, Long> result = new LinkedHashMap<>();
            for (Object[] row : rows) {
                Department dept = (Department) row[0];
                Long count = (Long) row[1];
                result.put(dept, count);
            }
            return result;
        }
    }

    // 2) Nombre d’employés par projet
    public Map<Project, Long> getEmployeeCountByProject() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Object[]> q = session.createQuery(
                    "select ep.project, count(ep.employee) " +
                            "from EmployeeProject ep " +
                            "group by ep.project " +
                            "order by ep.project.projectName",
                    Object[].class
            );

            List<Object[]> rows = q.getResultList();
            Map<Project, Long> result = new LinkedHashMap<>();
            for (Object[] row : rows) {
                Project project = (Project) row[0];
                Long count = (Long) row[1];
                result.put(project, count);
            }
            return result;
        }
    }

    // 3) Nombre d’employés par grade
    public Map<Grade, Long> getEmployeeCountByGrade() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Object[]> q = session.createQuery(
                    "select e.grade, count(e) " +
                            "from Employee e " +
                            "group by e.grade " +
                            "order by e.grade",
                    Object[].class
            );

            List<Object[]> rows = q.getResultList();
            Map<Grade, Long> result = new LinkedHashMap<>();
            for (Object[] row : rows) {
                Grade grade = (Grade) row[0];
                Long count = (Long) row[1];
                result.put(grade, count);
            }
            return result;
        }
    }
}
