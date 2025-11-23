package com.example.projetjeegroupeq.dao.implementation;

import com.example.projetjeegroupeq.dao.interfaces.PayDAOI;
import com.example.projetjeegroupeq.model.Employee;
import com.example.projetjeegroupeq.model.Pay;
import com.example.projetjeegroupeq.dao.sortingType.PaySortingType;
import jakarta.persistence.EntityManager;

import com.example.projetjeegroupeq.util.HibernateUtil;

import java.util.Date;
import java.util.List;

/**
 * DAO pour l'entité Pay : opérations CRUD et recherches filtrées/triées.
 *
 * Règles d'usage :
 * - Les recherches unitaires retournent l'entité ou null si non trouvée.
 * - Les recherches multiples retournent une liste (potentiellement vide).
 * - Les erreurs techniques sont levées sous forme de RuntimeException.
 */
public class PayDAO implements PayDAOI {
    /**
     * Constructeur par défaut.
     */
    public PayDAO() {};

    /**
     * Persiste une nouvelle paye.
     *
     * @param pay instance Pay à ajouter
     * @throws RuntimeException en cas d'erreur technique
     */
    @Override
    public void add(Pay pay) {
        EntityManager em = null;

        try {
            em = HibernateUtil.getEntityManager();

            em.getTransaction().begin();
            em.persist(pay);
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de l'ajout d'une paye : " + e.getMessage());
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * Met à jour la paye identifiée par original.getId() avec les valeurs de update.
     *
     * Comportement observable :
     * - Si la paye n'existe pas, la méthode effectue un rollback et retourne sans exception.
     *
     * @param original entité contenant l'id de la paye à modifier
     * @param update entité contenant les nouvelles valeurs
     * @throws RuntimeException en cas d'erreur technique
     */
    @Override
    public void update(Pay original, Pay update) {
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();

            em.getTransaction().begin();

            Pay payFound = em.find(Pay.class, original.getId());

            if (payFound == null) {
                System.err.println("Aucune paye trouvée avec l'id : " + original.getId());
                em.getTransaction().rollback();
                return;
            }

            payFound.setDate(update.getDate());
            payFound.setBonus(update.getBonus());
            payFound.setDeductions(update.getDeductions());
            payFound.setSalary_net(update.getSalary_net());
            payFound.setEmployee(update.getEmployee());

            em.merge(payFound);

            em.getTransaction().commit();

        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la recherche d'une paye : " + e.getMessage());
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * Supprime la paye identifiée par pay.getId().
     *
     * @param pay entité Pay utilisée pour récupérer l'id à supprimer
     * @throws RuntimeException en cas d'erreur technique
     */
    @Override
    public void delete(Pay pay) {
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();

            em.getTransaction().begin();

            Pay payFound = em.find(Pay.class, pay.getId());
            em.remove(payFound);

            em.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la suppression d'une paye : " + e.getMessage());
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * Recherche une paye par son identifiant.
     *
     * @param id identifiant recherché
     * @return Pay correspondant, ou null si non trouvé
     * @throws RuntimeException en cas d'erreur technique
     */
    @Override
    public Pay searchById(int id) {
        EntityManager em = null;

        try {
            em = HibernateUtil.getEntityManager();
            Pay payFound = em.find(Pay.class, id);

            if (payFound == null) {
                System.err.println("Aucune paye trouvée avec l'id : " + id);
                em.getTransaction().rollback();
                return null;
            }

            return payFound;
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la recherche d'une paye : " + e.getMessage());
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * Recherche les payes dont le bonus est dans l'intervalle (inclus).
     *
     * @param minBonus borne minimale (inclusive)
     * @param maxBonus borne maximale (inclusive)
     * @return liste de Pay correspondant ; peut être vide
     * @throws RuntimeException en cas d'erreur technique
     */
    @Override
    public List<Pay> searchByBonus(double minBonus, double maxBonus) {
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();

            String query = "SELECT p FROM Pay p WHERE p.bonus BETWEEN '" + minBonus + "' AND '" + maxBonus + "'";

            List<Pay> pays = List.of();

            pays = em.createQuery(query, Pay.class).getResultList();

            return pays;
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la recherche de pays par bonus : " + e.getMessage());
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * Recherche les payes dont les déductions sont dans l'intervalle (inclus).
     *
     * @param minDeduction borne minimale (inclusive)
     * @param maxDeduction borne maximale (inclusive)
     * @return liste de Pay correspondant ; peut être vide
     * @throws RuntimeException en cas d'erreur technique
     */
    @Override
    public List<Pay> searchByDeduction(double minDeduction, double maxDeduction) {
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();

            String query = "SELECT p FROM Pay p WHERE p.deductions BETWEEN '" + minDeduction + "' AND '" + maxDeduction + "'";

            List<Pay> pays = List.of();

            pays = em.createQuery(query, Pay.class).getResultList();

            return pays;
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la recherche de pays par deduction : " + e.getMessage());
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * Recherche les payes dont le salaire net est dans l'intervalle (inclus).
     *
     * @param minNet borne minimale (inclusive)
     * @param maxNet borne maximale (inclusive)
     * @return liste de Pay correspondant ; peut être vide
     * @throws RuntimeException en cas d'erreur technique
     */
    @Override
    public List<Pay> searchByNet(double minNet, double maxNet) {
        EntityManager em = null;

        try {
            em = HibernateUtil.getEntityManager();

            String query = "SELECT p FROM Pay p WHERE p.salary_net BETWEEN '" + minNet + "' AND '" + maxNet + "'";

            List<Pay> pays = List.of();

            pays = em.createQuery(query, Pay.class).getResultList();

            return pays;
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la recherche de pays par net : " + e.getMessage());
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * Recherche les payes entre deux dates (inclus).
     *
     * @param minDate date de début (inclusive)
     * @param maxDate date de fin (inclusive)
     * @return liste de Pay correspondant ; peut être vide
     * @throws RuntimeException en cas d'erreur technique
     */
    @Override
    public List<Pay> searchByDate(Date minDate, Date maxDate) {
        EntityManager em = null;

        try {
            em = HibernateUtil.getEntityManager();

            String query = "SELECT p FROM Pay p WHERE p.date BETWEEN '" + minDate + "' AND '" + maxDate + "'";

            List<Pay> pays = List.of();

            pays = em.createQuery(query, Pay.class).getResultList();

            return pays;
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la recherche de pays par dates : " + e.getMessage());
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * Recherche les payes liées à un employé donné.
     *
     * @param employee employé utilisé comme critère
     * @return liste de Pay pour cet employé ; peut être vide
     * @throws RuntimeException en cas d'erreur technique
     */
    @Override
    public List<Pay> searchByEmployee(Employee employee) {
        EntityManager em = null;

        try {
            em = HibernateUtil.getEntityManager();

            String query = "SELECT p FROM Pay p WHERE p.employee = :employee";

            List<Pay> pays = List.of();

            pays = em.createQuery(query, Pay.class).setParameter("employee", employee).getResultList();

            return pays;
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la recherche de pays par employés : " + e.getMessage());
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * Retourne toutes les payes triées selon le critère fourni.
     *
     * @param sortingType critère de tri (PaySortingType)
     * @return liste triée de Pay ; peut être vide
     * @throws RuntimeException en cas d'erreur technique
     */
    @Override
    public List<Pay> getAllSorted(PaySortingType sortingType) {
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();

            String query;

            List<Pay> pays = List.of();

            switch (sortingType) {
                case BY_BONUS:
                    query = "SELECT p FROM Pay p ORDER BY p.bonus";
                    break;
                case BY_EMPLOYEE:
                    query = "SELECT p FROM Pay p ORDER BY p.employee.firstName";
                    break;
                case BY_DEDUCTION:
                    query = "SELECT p FROM Pay p ORDER BY p.deductions";
                    break;
                default:
                    query = "SELECT p FROM Pay p ORDER BY p.date";
                    break;
            }

            pays = em.createQuery(query, Pay.class).getResultList();

            return pays;
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la recherche globale triée avec le critère '" + sortingType.name() + "' : " + e.getMessage());
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * Retourne toutes les payes triées par date (ordre par défaut).
     *
     * @return liste de Pay triée par date ; peut être vide
     * @throws RuntimeException en cas d'erreur technique
     */
    @Override
    public List<Pay> getAll() {
        return this.getAllSorted(PaySortingType.BY_DATE);
    }
}

