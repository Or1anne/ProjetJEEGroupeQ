package com.example.projetjeegroupeq.dao.implementation;

import com.example.projetjeegroupeq.dao.interfaces.PayDAOI;
import com.example.projetjeegroupeq.model.Pay;
import com.example.projetjeegroupeq.sortingType.PaySortingType;
import jakarta.persistence.EntityManager;

import com.example.projetjeegroupeq.util.HibernateUtil;

import java.util.Date;
import java.util.List;

public class PayDAO implements PayDAOI {
    public PayDAO() {};

    @Override
    public void addPay(Pay pay) {
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

    @Override
    public void updatePay(int id, Pay pay) {
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();

            em.getTransaction().begin();

            Pay payFound = em.find(Pay.class, id);

            if (payFound == null) {
                System.err.println("Aucune paye trouvée avec l'id : " + id);
                em.getTransaction().rollback();
                return;
            }

            payFound.setDate(pay.getDate());
            payFound.setBonus(pay.getBonus());
            payFound.setDeductions(pay.getDeductions());
            payFound.setSalary_net(pay.getSalary_net());
            payFound.setEmployee(pay.getEmployee());

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

    @Override
    public void deletePay(int id) {
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();

            em.getTransaction().begin();

            Pay payFound = em.find(Pay.class, id);
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

    @Override
    public Pay searchPayById(int id) {
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

    @Override
    public List<Pay> getAllPaySorted(PaySortingType sortingType) {
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
                    query = "SELECT p FROM Pay p ORDER BY p.employee.id";
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

    @Override
    public List<Pay> getAllPay() {
        return this.getAllPaySorted(PaySortingType.BY_DATE);
    }
}