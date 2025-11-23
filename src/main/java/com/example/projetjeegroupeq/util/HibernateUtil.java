package com.example.projetjeegroupeq.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.HashMap;
import java.util.Properties;

public class HibernateUtil {
    private static EntityManagerFactory emf;

    static {
        emf = Persistence.createEntityManagerFactory("HIBERNATE");
    }

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}









