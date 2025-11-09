package com.example.projetjeegroupeq.dao;

import com.example.projetjeegroupeq.model.Employee;
import com.example.projetjeegroupeq.sortingType.EmployeeSortingType;
import jakarta.persistence.EntityManager;

import com.example.projetjeegroupeq.util.HibernateUtil;

import java.util.List;

public class EmployeeDAOImpl implements EmployeeDAO {
    private EntityManager em;

    public EmployeeDAOImpl() {}

    //TODO Actuellement toutes les méthodes sont vides, il faut les remplir

    @Override
    public void addEmployee(Employee employee) {
        em = HibernateUtil.getEntityManager();

        try {
            em.getTransaction().begin();

            em.persist(employee);
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de l'ajout d'un employée", e);
        } finally {
            em.close();
        }
    }

    @Override
    public void updateEmployee(int id, Employee employee) {
        em = HibernateUtil.getEntityManager();

        try {
            em.getTransaction().begin();

            Employee employeeFound = em.find(Employee.class, id);

            if (employeeFound == null) {
                System.err.println("Aucun employé trouvé avec l'id : " + id);
                em.getTransaction().rollback();
                return;
            }

            employeeFound.setFirstName(employee.getFirstName());
            employeeFound.setLastName(employee.getLastName());
            employeeFound.setGrade(employee.getGrade());
            employeeFound.setPost(employee.getPost());

            em.merge(employeeFound);

            em.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la mise à jour d'un employée", e);
        } finally {
            em.close();
        }
    }

    @Override
    public void deleteEmployee(int id) {
        em = HibernateUtil.getEntityManager();

        try {
            em.getTransaction().begin();

            Employee employeeFound = em.find(Employee.class, id);

            if (employeeFound == null) {
                System.err.println("Aucun employé trouvé avec l'id : " + id);
                em.getTransaction().rollback();
                return;
            }

            em.remove(employeeFound);
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la suppression d'un employé", e);
        } finally {
            em.close();
        }
    }

    @Override
    public Employee searchEmployeeById(int id) {
        em = HibernateUtil.getEntityManager();

        try {
            em.getTransaction().begin();
            Employee employeeFound = em.find(Employee.class, id);

            if (employeeFound == null) {
                System.err.println("Aucun employé trouvé avec l'id : " + id);
                em.getTransaction().rollback();
                return null;
            }

            return employeeFound;
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la recherche d'un employé", e);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Employee> getAllEmployees() {
        return this.getAllEmployeesSorted(EmployeeSortingType.BY_FIRSTNAME);
    }

    @Override
    public List<Employee> getAllEmployeesSorted(EmployeeSortingType sortingType) {
        em = HibernateUtil.getEntityManager();

        List<Employee> employees = List.of();
        String query;

        switch (sortingType) {
            case BY_GRADE:
                query = "SELECT e FROM Employee ORDER BY e.grade";
                break;

            case BY_SALARY:
                query = "SELECT e FROM Employee ORDER BY e.salary";
                break;

            case BY_DEPARTMENT:
                query = "SELECT e FROM Employee ORDER BY e.departmentId";
                break;

            case BY_POST:
                query = "SELECT e FROM Employee ORDER BY e.post";
                break;

            case BY_LASTNAME:
                query = "SELECT e FROM Employee ORDER BY e.lastname";
                break;

            default:
                query = "SELECT e FROM Employee ORDER BY e.firstname";
                break;
        }

        try {
            employees = em.createQuery(query, Employee.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la recherche d'employé par " + sortingType.name());
        } finally {
            em.close();
        }

        return employees;
    }

    @Override
    public List<Employee> searchEmployeesByGrade(String gradeName) {
        em = HibernateUtil.getEntityManager();

        List<Employee> employees = List.of();

        String query = "SELECT e FROM Employee WHERE e.grade = '" + gradeName + "'";

        try {
            em.createQuery(query, Employee.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la recherche d'un employé par grade");
        } finally {
            em.close();
        }

        return employees;
    }

    @Override
    public List<Employee> searchEmployeesByPost(String postName) {
        em = HibernateUtil.getEntityManager();

        List<Employee> employees = List.of();

        String query = "SELECT e FROM Employee WHERE e.post = '" + postName + "'";

        try {
            em.createQuery(query, Employee.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la recherche d'un employé par poste");
        } finally {
            em.close();
        }

        return employees;
    }

    @Override
    public List<Employee> searchEmployeesByDepartmentId(int departmentId) {
        em = HibernateUtil.getEntityManager();

        List<Employee> employees = List.of();

        String query = "SELECT e FROM Employee WHERE e.departmentId = '" + departmentId + "'";

        try {
            em.createQuery(query, Employee.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la recherche d'un employé par departement");
        } finally {
            em.close();
        }

        return employees;
    }
}
