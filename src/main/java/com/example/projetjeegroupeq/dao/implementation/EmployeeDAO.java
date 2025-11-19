package com.example.projetjeegroupeq.dao.implementation;

import com.example.projetjeegroupeq.dao.interfaces.EmployeeDAOI;
import com.example.projetjeegroupeq.model.Department;
import com.example.projetjeegroupeq.model.Employee;
import com.example.projetjeegroupeq.sortingType.EmployeeSortingType;
import jakarta.persistence.EntityManager;

import com.example.projetjeegroupeq.util.HibernateUtil;

import java.util.List;

public class EmployeeDAO implements EmployeeDAOI {

    public EmployeeDAO() {}

    @Override
    public void addEmployee(Employee employee) {
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();
            em = HibernateUtil.getEntityManager();

            em.getTransaction().begin();

            em.persist(employee);
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de l'ajout d'un employée : " +  e.getMessage());
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public void updateEmployee(int id, Employee employee) {
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();

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
            throw new RuntimeException("Erreur lors de la mise à jour d'un employé : " + e.getMessage());
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public void deleteEmployee(int id) {
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();

            em.getTransaction().begin();

            Employee employeeFound = em.find(Employee.class, id);

            if (employeeFound == null) {
                System.err.println("Aucun employé trouvé avec l'id : " + id);
                em.getTransaction().rollback();
                return;
            }

            employeeFound.setDepartment(null);

            List<Department> deps = em.createQuery(
                            "SELECT d FROM Department d WHERE d.chefDepartment.id = :id", Department.class)
                    .setParameter("id", id)
                    .getResultList();

            for (Department d : deps) {
                d.setChefDepartment(null); // casser la FK pour éviter TransientObjectException
            }

            em.remove(employeeFound);
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la suppression d'un employé : " + e.getMessage());
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public Employee searchEmployeeById(int id) {
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();

            em.getTransaction().begin();
            Employee employeeFound = em.find(Employee.class, id);

            if (employeeFound == null) {
                System.err.println("Aucun employé trouvé avec l'id : " + id);
                em.getTransaction().rollback();
                return null;
            }

            return employeeFound;
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la recherche d'un employé : " + e.getMessage());
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public List<Employee> getAllEmployees() {
        return this.getAllEmployeesSorted(EmployeeSortingType.BY_FIRSTNAME);
    }

    @Override
    public List<Employee> getAllEmployeesSorted(EmployeeSortingType sortingType) {
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();

            List<Employee> employees = List.of();
            String query;

            switch (sortingType) {
                case BY_GRADE:
                    query = "SELECT e FROM Employee e ORDER BY e.grade";
                    break;

                case BY_SALARY:
                    query = "SELECT e FROM Employee e ORDER BY e.salary";
                    break;

                case BY_DEPARTMENT:
                    query = "SELECT e FROM Employee e ORDER BY e.department.id";
                    break;

                case BY_POST:
                    query = "SELECT e FROM Employee e ORDER BY e.post";
                    break;

                case BY_LASTNAME:
                    query = "SELECT e FROM Employee e ORDER BY e.lastName";
                    break;

                default:
                    query = "SELECT e FROM Employee e ORDER BY e.firstName";
                    break;
            }

            employees = em.createQuery(query, Employee.class).getResultList();

            return employees;
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la recherche d'employé par " + sortingType.name() + " : " + e.getMessage());
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public List<Employee> searchEmployeesByGrade(String gradeName) {
        EntityManager em = null;
         try {
             em = HibernateUtil.getEntityManager();

             List<Employee> employees = List.of();

             String query = "SELECT e FROM Employee e WHERE e.grade = '" + gradeName + "'";

             em.createQuery(query, Employee.class).getResultList();

             return employees;
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la recherche d'un employé par grade : " + e.getMessage());
        } finally {
             if (em != null) {
                 em.close();
             }
        }
    }

    @Override
    public List<Employee> searchEmployeesByPost(String postName) {
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();

            List<Employee> employees = List.of();

            String query = "SELECT e FROM Employee e WHERE e.post = '" + postName + "'";

            em.createQuery(query, Employee.class).getResultList();

            return employees;
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la recherche d'un employé par poste : " + e.getMessage());
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public List<Employee> searchEmployeesByDepartmentId(int departmentId) {
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();

            List<Employee> employees = List.of();

            String query = "SELECT e FROM Employee e WHERE e.department.id = '" + departmentId + "'";

            em.createQuery(query, Employee.class).getResultList();

            return employees;
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la recherche d'un employé par departement : " + e.getMessage());
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
}
