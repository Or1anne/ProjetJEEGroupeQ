package com.example.projetjeegroupeq.dao.implementation;

import com.example.projetjeegroupeq.dao.interfaces.DepartmentDAOI;
import com.example.projetjeegroupeq.model.Department;
import com.example.projetjeegroupeq.model.Employee;
import com.example.projetjeegroupeq.dao.sortingType.DepartmentSortingType;
import jakarta.persistence.EntityManager;

import com.example.projetjeegroupeq.util.HibernateUtil;

import java.util.List;

/**
 * DAO pour l'entité Department - interface d'accès aux données.
 *
 * Usage :
 * - Instancier et appeler les méthodes publiques pour effectuer des opérations CRUD et des recherches.
 * - Les méthodes de recherche retournent soit une entité (ou null) soit une List (potentiellement vide).
 *
 * Remarques :
 * - Les erreurs techniques sont repropagées sous forme de RuntimeException.
 */
public class DepartmentDAO implements DepartmentDAOI {

    public DepartmentDAO() {};

    /**
     * Persiste un nouveau département en base.
     *
     * @param department département à ajouter (doit être initialisé)
     * @throws RuntimeException en cas d'erreur d'accès aux données
     */
    @Override
    public void add(Department department) {
        EntityManager em = null;
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

    /**
     * Met à jour le département désigné par original.getId() avec les valeurs de update.
     *
     * Comportement observable :
     * - Si l'entité n'existe pas, la méthode effectue un rollback et retourne sans exception.
     *
     * @param original instance contenant au minimum l'id du département à modifier
     * @param update instance contenant les nouvelles valeurs
     * @throws RuntimeException en cas d'erreur d'accès aux données
     */
    @Override
    public void update(Department original, Department update) {
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();

            em.getTransaction().begin();

            Department departmentFound = em.find(Department.class, original.getId());

            if (departmentFound == null) {
                System.err.println("Aucun département trouvé avec l'id : " + original.getId());
                em.getTransaction().rollback();
                return;
            }

            departmentFound.setDepartmentName(update.getDepartmentName());
            departmentFound.setChefDepartment(update.getChefDepartment());

            em.merge(departmentFound);

            em.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de l'ajout d'un département : " + e.getMessage());
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * Supprime le département identifié par department.getId().
     *
     * Comportement observable :
     * - Si l'entité n'existe pas, la méthode effectue un rollback et retourne sans exception.
     *
     * @param department instance Department dont l'id est utilisé pour la suppression
     * @throws RuntimeException en cas d'erreur d'accès aux données
     */
    @Override
    public void delete(Department department) {
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();

            em.getTransaction().begin();

            Department departmentFound = em.find(Department.class, department.getId());

            if (departmentFound == null) {
                System.err.println("Aucun département trouvé avec l'id : " + department.getId());
                em.getTransaction().rollback();
                return;
            }

            em.remove(departmentFound);

            em.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la suppression d'un département : " + e.getMessage());
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * Recherche un département par son identifiant.
     *
     * @param id identifiant recherché
     * @return Department correspondant, ou null si aucun résultat
     * @throws RuntimeException en cas d'erreur technique
     */
    @Override
    public Department searchById(int id) {
        EntityManager em = null;
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
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * Recherche un département par son nom exact.
     *
     * @param name nom du département (comparaison exacte)
     * @return Department correspondant, ou null si aucun résultat
     * @throws RuntimeException en cas d'erreur technique
     */
    @Override
    public Department searchByName(String name) {
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();

            String query = "SELECT d FROM Department d WHERE d.departmentName = '" + name + "'";

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
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * Recherche les départements dont le chef est l'employé fourni.
     *
     * @param chef instance Employee utilisée comme critère (au minimum id)
     * @return Liste de Department correspondant ; liste potentiellement vide
     * @throws RuntimeException en cas d'erreur technique
     */
    @Override
    public List<Department> searchByChef(Employee chef) {
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();

            String query = "SELECT d FROM Department d WHERE d.chefDepartment.id = '" + chef.getId() + "'";

            List<Department> departments = List.of();

            departments = em.createQuery(query, Department.class).getResultList();

            return departments;
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la recherche par id d'un département : " + e.getMessage());
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * Recherche les départements contenant au moins un des membres fournis.
     *
     * @param employees liste d'Employee utilisée comme critère
     * @return Liste de Department correspondant ; liste potentiellement vide
     * @throws RuntimeException en cas d'erreur technique
     */
    @Override
    public List<Department> searchByMembers(List<Employee> employees) {
        EntityManager em = null;

        try {
            em = HibernateUtil.getEntityManager();

            String query = "SELECT DISTINCT e.department FROM Employee e WHERE e IN :employees";

            List<Department> departments = List.of();

            departments = em.createQuery(query, Department.class).setParameter("employees", employees).getResultList();

            return departments;
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la recherche de départements par membres : " + e.getMessage());
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * Retourne tous les départements triés selon le critère fourni.
     *
     * @param sortingType critère de tri (voir DepartmentSortingType)
     * @return liste triée de Department ; peut être vide
     * @throws RuntimeException en cas d'erreur technique
     */
    @Override
    public List<Department> getAllSorted(DepartmentSortingType sortingType) {
        EntityManager em = null;

        try {
            em = HibernateUtil.getEntityManager();

            String query;

            List<Department> departments = List.of();

            switch (sortingType) {
                case BY_CHEF:
                    query = "SELECT d FROM Department d ORDER BY d.chefDepartment.firstName";
                    break;
                default:
                    query = "SELECT d FROM Department d ORDER BY d.departmentName";
                    break;
            }

            departments = em.createQuery(query, Department.class).getResultList();

            return departments;
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la recherche globale des départements selon le critère '" + sortingType.name() + "' : " + e.getMessage());
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * Retourne tous les départements triés par nom (ordre par défaut).
     *
     * @return liste de Department triée
     * @throws RuntimeException en cas d'erreur technique
     */
    @Override
    public List<Department> getAll() {
        return this.getAllSorted(DepartmentSortingType.BY_NAME);
    }
}
