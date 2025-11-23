package com.example.projetjeegroupeq.dao.implementation;

import com.example.projetjeegroupeq.dao.interfaces.RoleDAOI;
import com.example.projetjeegroupeq.dao.sortingType.RoleSortingType;
import com.example.projetjeegroupeq.model.Employee;
import com.example.projetjeegroupeq.model.Role;
import com.example.projetjeegroupeq.util.HibernateUtil;
import jakarta.persistence.EntityManager;

import java.util.List;

/**
 * DAO pour l'entité Role : fournit les opérations CRUD et les recherches/tri.
 *
 * Usage API :
 * - Les méthodes de recherche unitaires retournent l'entité ou null si non trouvée.
 * - Les méthodes retournant des collections renvoient une List qui peut être vide.
 * - En cas d'erreur technique, une RuntimeException est propagée.
 */
public class RoleDAO implements RoleDAOI {

    /**
     * Constructeur par défaut.
     *
     * Usage : instancier simplement pour appeler les méthodes d'accès aux données.
     */
    public RoleDAO() {}

    /**
     * Persiste un nouveau rôle en base.
     *
     * @param role rôle à ajouter (doit être initialisé)
     * @throws RuntimeException en cas d'erreur d'accès aux données
     */
    @Override
    public void add(Role role) {
        EntityManager em = null;

        try {
            em = HibernateUtil.getEntityManager();

            em.getTransaction().begin();

            em.persist(role);

            em.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de l'enregistrement d'un rôle dans la bdd : " + e.getMessage());
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * Met à jour le rôle identifié par original.getIdRole() avec les valeurs de update.
     *
     * Comportement observable côté API :
     * - Si le rôle n'existe pas, la méthode retourne sans lancer d'exception (aucune modification effectuée).
     *
     * @param original instance contenant au minimum l'id du rôle à modifier
     * @param update instance contenant les nouvelles valeurs à appliquer
     * @throws RuntimeException en cas d'erreur technique lors de la mise à jour
     */
    @Override
    public void update(Role original, Role update) {
        EntityManager em = null;

        try {
            em = HibernateUtil.getEntityManager();

            em.getTransaction().begin();

            Role roleFound = em.find(Role.class, original.getIdRole());

            if (roleFound == null) {
                System.err.println("Aucun rôle trouvé avec l'id : " + original.getIdRole());
                em.getTransaction().rollback();
                return;
            }

            roleFound.setEmployeeRoles(update.getEmployeeRoles());
            roleFound.setRoleName(update.getRoleName());

            em.merge(roleFound);

            em.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la mise à jour d'un rôle : " + e.getMessage());
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * Supprime le rôle identifié par role.getIdRole().
     *
     * Comportement observable côté API :
     * - Si le rôle n'existe pas, la méthode retourne sans lancer d'exception (aucune suppression effectuée).
     *
     * @param role instance Role dont l'id est utilisé pour la suppression
     * @throws RuntimeException en cas d'erreur technique lors de la suppression
     */
    @Override
    public void delete(Role role) {
        EntityManager em = null;

        try {
            em = HibernateUtil.getEntityManager();

            Role roleFound = em.find(Role.class, role.getIdRole());

            if (roleFound == null) {
                System.err.println("Aucun rôle trouvé avec l'id : " + role.getIdRole());
                em.getTransaction().rollback();
                return;
            }

            em.getTransaction().begin();

            em.remove(role);

            em.getTransaction().commit();

        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la suppression d'un rôle : " + e.getMessage());
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * Recherche un rôle par son identifiant.
     *
     * @param id identifiant recherché
     * @return Role correspondant, ou null si aucun résultat
     * @throws RuntimeException en cas d'erreur technique
     */
    @Override
    public Role searchById(int id) {
        EntityManager em = null;

        try {
            em = HibernateUtil.getEntityManager();

            Role roleFound = em.find(Role.class, id);

            if (roleFound == null) {
                System.err.println("Aucun rôle trouvé avec l'id : " + id);
                em.getTransaction().rollback();
                return null;
            }

            return  roleFound;
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la recherche d'un rôle par id : " + e.getMessage());
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * Recherche un rôle par son nom exact.
     *
     * Remarque : la comparaison utilisée est une égalité exacte sur roleName.
     *
     * @param name nom exact du rôle recherché
     * @return Role correspondant, ou null si aucun résultat
     * @throws RuntimeException en cas d'erreur technique
     */
    @Override
    public Role searchByName(String name) {
        EntityManager em = null;

        try {
            em = HibernateUtil.getEntityManager();

            String query = "SELECT r From Role r WHERE r.roleName = '" + name + "'";

            Role roleFound = em.createQuery(query, Role.class).getSingleResult();

            if (roleFound == null) {
                System.err.println("Aucun rôle trouvé avec le nom : " + name);
                em.getTransaction().rollback();
                return null;
            }

            return roleFound;
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la recherche d'un rôle par nom : " + e.getMessage());
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * Recherche les rôles associés à une liste d'employés.
     *
     * @param employees liste d'Employee utilisée comme critère (chaque Employee doit au minimum avoir son id)
     * @return liste de Role correspondant ; peut être vide si aucun rôle trouvé
     * @throws RuntimeException en cas d'erreur technique
     */
    @Override
    public List<Role> searchByEmployee(List<Employee> employees) {
        EntityManager em = null;

        try {
            em = HibernateUtil.getEntityManager();

            String query = "SELECT DISTINCT e.roles From Employee e WHERE e IN :employees";

            List<Role> roles = List.of();

            roles = em.createQuery(query, Role.class).setParameter("employees", employees).getResultList();

            return roles;
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la recherche d'un rôle par employés : " + e.getMessage());
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * Retourne tous les rôles triés selon le critère fourni.
     *
     * @param sortingType critère de tri (voir RoleSortingType)
     * @return liste triée de Role ; peut être vide
     * @throws RuntimeException en cas d'erreur technique
     */
    @Override
    public List<Role> getAllSorted(RoleSortingType sortingType) {
        EntityManager em = null;

        try {
            em = HibernateUtil.getEntityManager();

            String query;

            List<Role> roles = List.of();

            switch (sortingType) {
                default:
                    query = "SELECT R FROM Role r ORDER BY r.roleName";
                    break;
            }

            roles = em.createQuery(query, Role.class).getResultList();

            return roles;
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la recherche globale de tous les rôles selon le critère : " + sortingType.name() + " : " +  e.getMessage());
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * Retourne tous les rôles triés par nom (ordre par défaut).
     *
     * @return liste de Role triée par défaut ; peut être vide
     * @throws RuntimeException en cas d'erreur technique
     */
    @Override
    public List<Role> getAll() {
        return this.getAllSorted(RoleSortingType.BY_NAME);
    }
}
