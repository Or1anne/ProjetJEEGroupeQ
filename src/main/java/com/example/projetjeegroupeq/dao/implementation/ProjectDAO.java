package com.example.projetjeegroupeq.dao.implementation;

import com.example.projetjeegroupeq.dao.interfaces.ProjectDAOI;
import com.example.projetjeegroupeq.model.Project;
import com.example.projetjeegroupeq.model.Employee;
import com.example.projetjeegroupeq.sortingType.ProjectSortingType;
import jakarta.persistence.EntityManager;

import com.example.projetjeegroupeq.util.HibernateUtil;

import java.util.List;

//TODO Gérer la recherche par membres du projet

public class ProjectDAO implements ProjectDAOI {
    private EntityManager em;

    public ProjectDAO() {};

    @Override
    public void addProject(Project project) {
        try {
            em = HibernateUtil.getEntityManager();

            em.getTransaction().begin();

            em.persist(project);

            em.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de l'ajout d'un projet : " + e.getMessage());
        } finally {
            em.close();
        }
    }

    @Override
    public void updateProject(int id, Project project) {
        try {
            em = HibernateUtil.getEntityManager();

            em.getTransaction().begin();

            Project projectFound = em.find(Project.class, id);

            if (projectFound == null) {
                System.err.println("Aucun projet trouvé avec l'id : " + id);
                em.getTransaction().rollback();
                return;
            }

            projectFound.setName_project(project.getName_project());
            projectFound.setStatus(project.getStatus());
            projectFound.setChefProj(project.getChefProj());

            em.merge(projectFound);

            em.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la mise à jour d'un projet : " + e.getMessage());
        } finally {
            em.close();
        }
    }

    @Override
    public void deleteProject(int id) {
        try {
            em = HibernateUtil.getEntityManager();

            em.getTransaction().begin();

            Project projectFound = em.find(Project.class, id);

            if (projectFound == null) {
                System.err.println("Aucun projet trouvé avec l'id : " + id);
                em.getTransaction().rollback();
                return;
            }

            em.remove(projectFound);

            em.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la suppression d'un projet : " + e.getMessage());
        } finally {
            em.close();
        }
    }

    @Override
    public Project searchProjectById(int id) {
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
            em.close();
        }
    }

    @Override
    public List<Project> searchByStatus(String status) {
        try {
            em = HibernateUtil.getEntityManager();

            String query = "SELECT p FROM Project WHERE p.status = '" + status + "'";

            List<Project> projects = List.of();

            projects = em.createQuery(query, Project.class).getResultList();

            return projects;
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la recherche d'un projet par statut : " + e.getMessage());
        } finally {
            em.close();
        }
    }

    @Override
    public Project searchByName(String name) {
        try {
            em = HibernateUtil.getEntityManager();

            String query = "SELECT p FROM Project WHERE p.name = '" + name + "'";

            Project projects;

            projects = em.createQuery(query, Project.class).getSingleResult();

            return projects;
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la recherche d'un projet par nom : " + e.getMessage());
        } finally {
            em.close();
        }
    }

    @Override
    public List<Project> searchByChef(Employee chef) {
        try {
            em = HibernateUtil.getEntityManager();

            String query = "SELECT p FROM Project WHERE p.chefIdProj = '" + chef.getId() + "'";

            List<Project> projects = List.of();

            projects = em.createQuery(query, Project.class).getResultList();

            return projects;
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la recherche d'un projet par chef de projet : " + e.getMessage());
        } finally {
            em.close();
        }
    }

    /*
    @Override
    public List<Project> searchByMember(Employee member) {
        em = HibernateUtil.getEntityManager();

        String query = "SELECT p FROM Project WHERE p.members = '" + chef.getId() + "'";

        List<Project> projects = List.of();

        try {
            projects = em.createQuery(query, Project.class).getResultList();

            return projects;
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la recherche d'un projet par chef de projet : " + e.getMessage());
        } finally {
            em.close();
        }
    }
     */

    @Override
    public List<Project> getAllProjectSorted(ProjectSortingType sortingType) {
        try {
            em = HibernateUtil.getEntityManager();

            String query;

            List<Project> projects = List.of();

            switch (sortingType) {
                case BY_STATUS:
                    query = "SELECT p FROM Project ORDER BY p.status";
                    break;
                case BY_CHEF:
                    query = "SELECT p FROM Project ORDER BY p.chefIdPro";
                    break;
                default:
                    query = "SELECT p FROM Project ORDER BY p.name";
                    break;
            }

            projects = em.createQuery(query, Project.class).getResultList();

            return projects;
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la recherche globale de projet selon le critère '" + sortingType.name() + "' : " + e.getMessage());
        } finally {
            em.close();
        }
    }

    @Override
    public List<Project> getAllProject() {
        return this.getAllProjectSorted(ProjectSortingType.BY_NAME);
    }
}
