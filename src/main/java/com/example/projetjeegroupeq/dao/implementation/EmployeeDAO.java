package com.example.projetjeegroupeq.dao.implementation;

import com.example.projetjeegroupeq.dao.interfaces.EmployeeDAOI;
import com.example.projetjeegroupeq.model.Department;
import com.example.projetjeegroupeq.model.Employee;
import com.example.projetjeegroupeq.model.Project;
import com.example.projetjeegroupeq.dao.sortingType.EmployeeSortingType;
import jakarta.persistence.EntityManager;

import com.example.projetjeegroupeq.util.HibernateUtil;

import java.util.List;

/**
 * DAO pour l'entité Employee — interface d'accès aux données (CRUD et recherches).
 *
 * Remarques d'utilisation :
 * - Les méthodes peuvent retourner null (pour les recherches unitaires) ou une liste (potentiellement vide).
 * - En cas d'erreur technique, une RuntimeException est lancée.
 */
public class EmployeeDAO implements EmployeeDAOI {

    /**
     * Constructeur par défaut.
     */
    public EmployeeDAO() {}

    /**
     * Ajoute un employé en base.
     *
     * @param employee entité Employee à persister
     * @throws RuntimeException en cas d'erreur technique
     */
    @Override
    public void add(Employee employee) {
        EntityManager em = null;

        try {
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

    /**
     * Met à jour l'employé identifié par original.getId() avec les valeurs de update.
     *
     * Comportement observable :
     * - Si l'employé n'existe pas, la méthode fait un rollback et retourne sans exception.
     *
     * @param original instance contenant l'id de l'employé à mettre à jour
     * @param update instance contenant les nouvelles valeurs
     * @throws RuntimeException en cas d'erreur technique
     */
    @Override
    public void update(Employee original, Employee update) {
        EntityManager em = null;

        try {
            em = HibernateUtil.getEntityManager();

            em.getTransaction().begin();

            Employee employeeFound = em.find(Employee.class, original.getId());

            if (employeeFound == null) {
                System.err.println("Aucun employé trouvé dans la bdd avec l'employé possédant les paramètres suivants :\n" +
                "\tid : " + original.getId() +
                "\n\tprénom : " + original.getFirstName() +
                "\n\tnom : " + original.getLastName());

                em.getTransaction().rollback();
                return;
            }

            employeeFound.setFirstName(update.getFirstName());
            employeeFound.setLastName(update.getLastName());
            employeeFound.setGrade(update.getGrade());
            employeeFound.setPost(update.getPost());
            employeeFound.setSalary(update.getSalary());
            employeeFound.setPassword(update.getPassword());
            employeeFound.setUsername(update.getUsername());
            employeeFound.setDepartment(update.getDepartment());
            // employeeFound.setEmployeeRoles(update.getEmployeeRoles());

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

    /**
     * Supprime l'employé identifié par employee.getId().
     *
     * @param employee instance Employee dont l'id est utilisé pour la suppression
     * @throws RuntimeException en cas d'erreur technique
     */
    @Override
    public void delete(Employee employee) {
        EntityManager em = null;

        try {
            em = HibernateUtil.getEntityManager();

            em.getTransaction().begin();

            Employee employeeFound = em.find(Employee.class, employee.getId());

            if (employeeFound == null) {
                System.err.println("Aucun employé trouvé dans la bdd avec l'employé possédant les paramètres suivants :" +
                "\n\tid : " + employee.getId() +
                "\n\tprénom : " + employee.getFirstName() +
                "\n\tnom : " + employee.getLastName());

                em.getTransaction().rollback();
                return;
            }

            employeeFound.setDepartment(null);

            List<Department> deps = em.createQuery(
                            "SELECT d FROM Department d WHERE d.chefDepartment.id = :id", Department.class)
                    .setParameter("id", employee.getId()) //TODO A Vérifier
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

    /**
     * Recherche un employé par son identifiant.
     *
     * @param id identifiant recherché
     * @return Employee correspondant, ou null si non trouvé
     * @throws RuntimeException en cas d'erreur technique
     */
    @Override
    public Employee searchById(int id) {
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

    /**
     * Recherche un employé par son nom d'utilisateur (pour le login).
     * @param username le nom d'utilisateur
     * @return l'employé correspondant ou null si non trouvé.
     */
// N'oublie pas d'ajouter la signature dans l'interface EmployeeDAOI si tu l'utilises !
    public Employee searchByUsername(String username) {
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();

            // Requête JPQL simple
            String jpql = "SELECT e FROM Employee e WHERE e.username = :username";

            List<Employee> results = em.createQuery(jpql, Employee.class)
                    .setParameter("username", username)
                    .getResultList();

            // Si la liste est vide, l'utilisateur n'existe pas, on retourne null
            return results.isEmpty() ? null : results.get(0);

        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la recherche par username : " + e.getMessage());
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * Retourne tous les employés triés par défaut (par prénom).
     *
     * @return liste d'Employee triée ; peut être vide
     * @throws RuntimeException en cas d'erreur technique
     */
    @Override
    public List<Employee> getAll() {
        return this.getAllSorted(EmployeeSortingType.BY_FIRSTNAME);
    }

    /**
     * Retourne tous les employés triés selon le critère fourni.
     *
     * @param sortingType critère de tri (EmployeeSortingType)
     * @return liste d'Employee triée ; peut être vide
     * @throws RuntimeException en cas d'erreur technique
     */
    @Override
    public List<Employee> getAllSorted(EmployeeSortingType sortingType) {
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

    /**
     * Recherche les employés correspondant exactement à un grade donné.
     *
     * @param gradeName nom exact du grade
     * @return liste d'Employee correspondant ; peut être vide
     * @throws RuntimeException en cas d'erreur technique
     */
    @Override
    public List<Employee> searchByGrade(String gradeName) {
        EntityManager em = null;

         try {
             em = HibernateUtil.getEntityManager();

             List<Employee> employees = List.of();

             String query = "SELECT e FROM Employee e WHERE e.grade = '" + gradeName + "'";

             employees = em.createQuery(query, Employee.class).getResultList();

             return employees;
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la recherche d'un employé par grade : " + e.getMessage());
        } finally {
             if (em != null) {
                 em.close();
             }
        }
    }

    /**
     * Recherche les employés pour un poste donné (comparaison exacte).
     *
     * @param postName nom du poste
     * @return liste d'Employee correspondant ; peut être vide
     * @throws RuntimeException en cas d'erreur technique
     */
    @Override
    public List<Employee> searchByPost(String postName) {
        EntityManager em = null;

        try {
            em = HibernateUtil.getEntityManager();

            List<Employee> employees = List.of();

            String query = "SELECT e FROM Employee e WHERE e.post = '" + postName + "'";

            employees = em.createQuery(query, Employee.class).getResultList();

            return employees;
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la recherche d'un employé par poste : " + e.getMessage());
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * Recherche les employés appartenant au département fourni.
     *
     * @param departement instance Department utilisée comme critère
     * @return liste d'Employee du département ; peut être vide
     * @throws RuntimeException en cas d'erreur technique
     */
    @Override
    public List<Employee> searchByDepartmentId(Department departement) {
        EntityManager em = null;

        try {
            em = HibernateUtil.getEntityManager();

            List<Employee> employees = List.of();

            String query = "SELECT e FROM Employee e WHERE e.department = :department";

            employees = em.createQuery(query, Employee.class).setParameter("department", departement).getResultList();

            return employees;
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la recherche d'un employé par departement : " + e.getMessage());
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * Recherche les employés associés à la liste de projets fournie.
     *
     * @param projects liste de Project utilisée comme critère
     * @return liste d'Employee correspondant ; peut être vide
     * @throws RuntimeException en cas d'erreur technique
     */
    @Override
    public List<Employee> searchByProjects(List<Project> projects) {
        EntityManager em = null;

        try {
            em = HibernateUtil.getEntityManager();

            String query = "SELECT e FROM Employee e JOIN e.projects pe WHERE pe.project IN :projects HAVING COUNT(DISTINCT pe.project) = :size";

            List<Employee> employees = List.of();

            projects = em.createQuery(query, Project.class).setParameter("projects", projects).setParameter("size", (long) projects.size()).getResultList();

            return employees;
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la recherche d'un projet par chef de projet : " + e.getMessage());
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
}
