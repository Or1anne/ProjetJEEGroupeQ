package com.example.projetjeegroupeq.dao.implementation;

import com.example.projetjeegroupeq.dao.interfaces.PayDAOI;
import com.example.projetjeegroupeq.model.Pay;
import com.example.projetjeegroupeq.sortingType.PaySortingType;
import jakarta.persistence.EntityManager;

import com.example.projetjeegroupeq.util.HibernateUtil;

import java.util.Date;
import java.util.List;

public class PayDAO implements PayDAOI {
    private EntityManager em;

    public PayDAO() {};

    @Override
    public void addPay(Pay pay) {
        em = HibernateUtil.getEntityManager();

        em.getTransaction().begin();

        try {
            em.persist(pay);
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de l'ajout d'une paye : " + e.getMessage());
        } finally {
            em.close();
        }
    }

    @Override
    public void updatePay(int id, Pay pay) {
        try {
            em = HibernateUtil.getEntityManager();

            em.getTransaction().begin();

            Pay payFound = em.find(Pay.class, id);

            if (payFound == null) {
                System.err.println("Aucune paye trouvée avec l'id : " + id);
                em.getTransaction().rollback();
                return;
            }

            //payFound.setDate(pay.getDate()); TODO ERREUR
            payFound.setBonus(pay.getBonus());
            payFound.setDeductions(pay.getDeductions());
            payFound.setSalary_net(pay.getSalary_net());
            //payFound.setEmployee(pay.getEmployee()); TODO ERREUR

            em.merge(payFound);

            em.getTransaction().commit();

        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la recherche d'une paye : " + e.getMessage());
        } finally {
            em.close();
        }
    }

    @Override
    public void deletePay(int id) {
        try {
            em = HibernateUtil.getEntityManager();

            em.getTransaction().begin();

            Pay payFound = em.find(Pay.class, id);
            em.remove(payFound);

            em.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la suppression d'une paye : " + e.getMessage());
        } finally {
            em.close();
        }
    }

    @Override
    public Pay searchPayById(int id) {
        em = HibernateUtil.getEntityManager();

        try {
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
            em.close();
        }
    }

    @Override
    public List<Pay> searchByBonus(double minBonus, double maxBonus) {
        try {
            em = HibernateUtil.getEntityManager();

            String query = "SELECT p FROM Pay WHERE p.bonus BETWEEN '" + minBonus + "' AND '" + maxBonus + "'";

            List<Pay> pays = List.of();

            pays = em.createQuery(query, Pay.class).getResultList();

            return pays;
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la recherche de pays par bonus : " + e.getMessage());
        } finally {
            em.close();
        }
    }

    @Override
    public List<Pay> searchByDeduction(double minDeduction, double maxDeduction) {
        try {
            em = HibernateUtil.getEntityManager();

            String query = "SELECT p FROM Pay WHERE p.deduction BETWEEN '" + minDeduction + "' AND '" + maxDeduction + "'";

            List<Pay> pays = List.of();

            pays = em.createQuery(query, Pay.class).getResultList();

            return pays;
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la recherche de pays par deduction : " + e.getMessage());
        } finally {
            em.close();
        }
    }

    @Override
    public List<Pay> searchByNet(double minNet, double maxNet) {
        try {
            em = HibernateUtil.getEntityManager();

            String query = "SELECT p FROM Pay WHERE p.net BETWEEN '" + minNet + "' AND '" + maxNet + "'";

            List<Pay> pays = List.of();

            pays = em.createQuery(query, Pay.class).getResultList();

            return pays;
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la recherche de pays par net : " + e.getMessage());
        } finally {
            em.close();
        }
    }

    @Override
    public List<Pay> searchByDate(Date minDate, Date maxDate) {
        try {
            em = HibernateUtil.getEntityManager();

            String query = "SELECT p FROM Pay WHERE p.date BETWEEN '" + minDate + "' AND '" + maxDate + "'";

            List<Pay> pays = List.of();

            pays = em.createQuery(query, Pay.class).getResultList();

            return pays;
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la recherche de pays par dates : " + e.getMessage());
        } finally {
            em.close();
        }
    }

    @Override
    public List<Pay> getAllPaySorted(PaySortingType sortingType) {
        try {
            em = HibernateUtil.getEntityManager();

            String query;

            List<Pay> pays = List.of();

            switch (sortingType) {
                case BY_BONUS:
                    query = "SELECT p FROM Pay ORDER BY p.bonus";
                    break;
                case BY_EMPLOYEE:
                    query = "SELECT p FROM Pay ORDER BY p.employeeId";
                    break;
                case BY_DEDUCTION:
                    query = "SELECT p FROM Pay ORDER BY p.deduction";
                    break;
                default:
                    query = "SELECT p FROM Pay ORDER BY p.date";
                    break;
            }

            pays = em.createQuery(query, Pay.class).getResultList();

            return pays;
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la recherche globale triée avec le critère '" + sortingType.name() + "' : " + e.getMessage());
        } finally {
            em.close();
        }
    }

    @Override
    public List<Pay> getAllPay() {
        return this.getAllPaySorted(PaySortingType.BY_DATE);
    }
}