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
    public ProjectDAO() {};

    @Override
    public void addProject(Project project) {
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

    @Override
    public void updateProject(int id, Project project) {
        EntityManager em = null;
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
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public void deleteProject(int id) {
        EntityManager em = null;
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
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public Project searchProjectById(int id) {
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

    @Override
    public List<Project> searchByStatus(String status) {
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();

            String query = "SELECT p FROM Project p WHERE p.status = '" + status + "'";

            List<Project> projects = List.of();

            projects = em.createQuery(query, Project.class).getResultList();

            return projects;
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la recherche d'un projet par statut : " + e.getMessage());
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

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

    @Override
    public List<Project> searchByMember(Employee member) {
        EntityManager em = null;
        em = HibernateUtil.getEntityManager();

        String query = "SELECT p FROM Project p WHERE p.ChefProj.id = '" + member.getId() + "'";

        List<Project> projects = List.of();

        try {
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

    @Override
    public List<Project> getAllProjectSorted(ProjectSortingType sortingType) {
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

    @Override
    public List<Project> getAllProject() {
        return this.getAllProjectSorted(ProjectSortingType.BY_NAME);
    }
}
