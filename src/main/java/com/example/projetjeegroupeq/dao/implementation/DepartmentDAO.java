package com.example.projetjeegroupeq.dao.implementation;

import com.example.projetjeegroupeq.dao.interfaces.DepartmentDAOI;
import com.example.projetjeegroupeq.model.Department;
import com.example.projetjeegroupeq.model.Employee;
import com.example.projetjeegroupeq.sortingType.DepartmentSortingType;
import jakarta.persistence.EntityManager;

import com.example.projetjeegroupeq.util.HibernateUtil;

import java.util.List;

public class DepartmentDAO implements DepartmentDAOI {
    private EntityManager em;

    public DepartmentDAO() {};

    @Override
    public void addDepartment(Department department) {
        try {
            em = HibernateUtil.getEntityManager();

            em.getTransaction().begin();

            em.persist(department);

            em.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de l'ajout d'un département : " + e.getMessage());
        } finally {
            em.close();
        }
    }

    @Override
    public void updateDepartment(int id, Department department) {
        try {
            em = HibernateUtil.getEntityManager();

            em.getTransaction().begin();

            Department departmentFound = em.find(Department.class, id);

            if (departmentFound == null) {
                System.err.println("Aucun département trouvé avec l'id : " + id);
                em.getTransaction().rollback();
                return;
            }

            departmentFound.setDepartmentName(department.getDepartmentName());
            departmentFound.setChefDepartment(department.getChefDepartment());

            em.merge(departmentFound);

            em.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de l'ajout d'un département : " + e.getMessage());
        } finally {
            em.close();
        }
    }

    @Override
    public void deleteDepartment(int id) {
        try {
            em = HibernateUtil.getEntityManager();

            em.getTransaction().begin();

            Department departmentFound = em.find(Department.class, id);

            if (departmentFound == null) {
                System.err.println("Aucun département trouvé avec l'id : " + id);
                em.getTransaction().rollback();
                return;
            }

            em.remove(departmentFound);

            em.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la suppression d'un département : " + e.getMessage());
        } finally {
            em.close();
        }
    }

    @Override
    public Department searchDepartmentById(int id) {
        try {
            em = HibernateUtil.getEntityManager();

            Department departmentFound = em.find(Department.class, id);

            if (departmentFound == null) {
                System.err.println("Aucun département trouvé avec l'id : " + id);
                em.getTransaction().rollback();
                return null;
            }

            return departmentFound;
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la recherche par id d'un département : " + e.getMessage());
        } finally {
            em.close();
        }
    }

    @Override
    public Department searchByName(String name) {
        try {
            em = HibernateUtil.getEntityManager();

            String query = "SELECT d FROM Department WHERE d.name = '" + name + "'";

            Department departmentFound = em.createQuery(query, Department.class).getSingleResult();

            if (departmentFound == null) {
                System.err.println("Aucun département trouvé avec le nom : " + name);
                em.getTransaction().rollback();
                return null;
            }

            return departmentFound;
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la recherche par id d'un département : " + e.getMessage());
        } finally {
            em.close();
        }
    }

    @Override
    public List<Department> searchByChef(Employee chef) {
        try {
            em = HibernateUtil.getEntityManager();

            String query = "SELECT d FROM Department WHERE d.chefIdDep = '" + chef.getId() + "'";

            List<Department> departments = List.of();

            departments = em.createQuery(query, Department.class).getResultList();

            return departments;
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la recherche par id d'un département : " + e.getMessage());
        } finally {
            em.close();
        }
    }

    @Override
    public List<Department> getAllDepartmentSorted(DepartmentSortingType sortingType) {
        try {
            em = HibernateUtil.getEntityManager();

            String query;

            List<Department> departments = List.of();

            switch (sortingType) {
                case BY_CHEF:
                    query = "SELECT d FROM Department ORDER BY d.chefIdDep";
                    break;
                default:
                    query = "SELECT d FROM Department ORDER BY d.name";
                    break;
            }

            departments = em.createQuery(query, Department.class).getResultList();

            return departments;
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la recherche globale des départements selon le critère '" + sortingType.name() + "' : " + e.getMessage());
        } finally {
            em.close();
        }
    }

    @Override
    public List<Department> getAllDepartment() {
        return this.getAllDepartmentSorted(DepartmentSortingType.BY_NAME);
    }
}
