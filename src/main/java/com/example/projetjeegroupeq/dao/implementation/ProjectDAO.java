package com.example.projetjeegroupeq.dao.implementation;

import com.example.projetjeegroupeq.dao.interfaces.ProjectDAOI;
import com.example.projetjeegroupeq.model.Project;
import com.example.projetjeegroupeq.model.Employee;
import com.example.projetjeegroupeq.model.ProjectStatus;
import com.example.projetjeegroupeq.dao.sortingType.ProjectSortingType;
import jakarta.persistence.EntityManager;

import com.example.projetjeegroupeq.util.HibernateUtil;

import java.util.List;

/**
 * DAO pour l'entité Project.
 *
 * - Fournit les opérations CRUD et des méthodes de recherche/tri.
 * - Les recherches retournent soit une entité (ou null) soit une liste (potentiellement vide).
 * - Les erreurs techniques sont repropagées en RuntimeException.
 */
public class ProjectDAO implements ProjectDAOI {
    public ProjectDAO() {};

    /**
     * Persiste un nouveau projet.
     *
     * @param project projet à ajouter
     * @throws RuntimeException en cas d'erreur technique
     */
    @Override
    public void add(Project project) {
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();

            em.getTransaction().begin();

            em.persist(project);

            em.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de l'ajout d'un projet : " + e.getMessage());
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * Met à jour le projet identifié par original.getId() avec les valeurs de update.
     *
     * @param original instance contenant l'id du projet à modifier
     * @param update instance avec les nouvelles valeurs
     * @throws RuntimeException en cas d'erreur technique
     */
    @Override
    public void update(Project original, Project update) {
        EntityManager em = null;

        try {
            em = HibernateUtil.getEntityManager();

            em.getTransaction().begin();

            Project projectFound = em.find(Project.class, original.getId());

            if (projectFound == null) {
                System.err.println("Aucun projet trouvé avec l'id : " + original.getId());
                em.getTransaction().rollback();
                return;
            }

            projectFound.setName_project(update.getName_project());
            projectFound.setStatus(update.getStatus());
            projectFound.setChefProj(update.getChefProj());
            projectFound.setEmployees(update.getEmployees());

            em.merge(projectFound);

            em.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la mise à jour d'un projet : " + e.getMessage());
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * Supprime le projet identifié par project.getId().
     *
     * @param project instance Project utilisée pour récupérer l'id à supprimer
     * @throws RuntimeException en cas d'erreur technique
     */
    @Override
    public void delete(Project project) {
        EntityManager em = null;

        try {
            em = HibernateUtil.getEntityManager();

            em.getTransaction().begin();

            Project projectFound = em.find(Project.class, project.getId());

            if (projectFound == null) {
                System.err.println("Aucun projet trouvé avec l'id : " + project.getId());
                em.getTransaction().rollback();
                return;
            }

            em.remove(projectFound);

            em.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la suppression d'un projet : " + e.getMessage());
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * Recherche un projet par son identifiant.
     *
     * @param id identifiant recherché
     * @return Project correspondant, ou null si non trouvé
     * @throws RuntimeException en cas d'erreur technique
     */
    @Override
    public Project searchById(int id) {
        EntityManager em = null;

        try {
            em = HibernateUtil.getEntityManager();

            Project projectFound = em.find(Project.class, id);

            if (projectFound == null) {
                System.err.println("Aucun projet trouvé avec l'id : " + id);
                em.getTransaction().rollback();
                return null;
            }

            return projectFound;
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la recherche d'un projet par id : " + e.getMessage());
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * Recherche les projets par statut.
     *
     * @param status statut à filtrer
     * @return Liste de Project correspondant ; peut être vide
     * @throws RuntimeException en cas d'erreur technique
     */
    @Override
    public List<Project> searchByStatus(ProjectStatus status) {
        EntityManager em = null;

        try {
            em = HibernateUtil.getEntityManager();

            String query = "SELECT p FROM Project p WHERE p.status = :status";

            List<Project> projects = List.of();

            projects = em.createQuery(query, Project.class).setParameter("status", status).getResultList();

            return projects;
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la recherche d'un projet par statut : " + e.getMessage());
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * Recherche un projet par nom exact.
     *
     * @param name nom du projet
     * @return Project correspondant, ou null si non trouvé
     * @throws RuntimeException en cas d'erreur technique
     */
    @Override
    public Project searchByName(String name) {
        EntityManager em = null;

        try {
            em = HibernateUtil.getEntityManager();

            String query = "SELECT p FROM Project p WHERE p.name_project = '" + name + "'";

            Project projects;

            projects = em.createQuery(query, Project.class).getSingleResult();

            return projects;
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la recherche d'un projet par nom : " + e.getMessage());
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * Recherche les projets dont le chef est l'employé fourni.
     *
     * @param chef instance Employee (au minimum id)
     * @return Liste de Project correspondant ; peut être vide
     * @throws RuntimeException en cas d'erreur technique
     */
    @Override
    public List<Project> searchByChef(Employee chef) {
        EntityManager em = null;

        try {
            em = HibernateUtil.getEntityManager();

            String query = "SELECT p FROM Project p WHERE p.ChefProj.id = '" + chef.getId() + "'";

            List<Project> projects = List.of();

            projects = em.createQuery(query, Project.class).getResultList();

            return projects;
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la recherche d'un projet par chef de projet : " + e.getMessage());
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * Recherche les projets associés exactement à la liste de membres fournie.
     *
     * @param employees liste de Employee utilisée comme critère
     * @return Liste de Project correspondant ; peut être vide
     * @throws RuntimeException en cas d'erreur technique
     */
    @Override
    public List<Project> searchByMembers(List<Employee> employees) {
        EntityManager em = null;

        try {
            em = HibernateUtil.getEntityManager();

            String query = "SELECT p FROM Project p JOIN p.employees ep WHERE ep.employee IN :employees HAVING COUNT(DISTINCT ep.employee) = :size";

            List<Project> projects = List.of();

            projects = em.createQuery(query, Project.class).setParameter("employees", employees).setParameter("size", (long) employees.size()).getResultList();

            return projects;
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la recherche d'un projet par chef de projet : " + e.getMessage());
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * Retourne tous les projets triés selon le critère fourni.
     *
     * @param sortingType critère de tri (voir ProjectSortingType)
     * @return liste triée de Project ; peut être vide
     * @throws RuntimeException en cas d'erreur technique
     */
    @Override
    public List<Project> getAllSorted(ProjectSortingType sortingType) {
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();

            String query;

            List<Project> projects = List.of();

            switch (sortingType) {
                case BY_STATUS:
                    query = "SELECT p FROM Project p ORDER BY p.status";
                    break;
                case BY_CHEF:
                    query = "SELECT p FROM Project p ORDER BY p.ChefProj.id";
                    break;
                default:
                    query = "SELECT p FROM Project p ORDER BY p.name_project";
                    break;
            }

            projects = em.createQuery(query, Project.class).getResultList();

            return projects;
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la recherche globale de projet selon le critère '" + sortingType.name() + "' : " + e.getMessage());
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * Retourne tous les projets triés par nom (ordre par défaut).
     *
     * @return liste de Project triée
     * @throws RuntimeException en cas d'erreur technique
     */
    @Override
    public List<Project> getAll() {
        return this.getAllSorted(ProjectSortingType.BY_NAME);
    }
}
