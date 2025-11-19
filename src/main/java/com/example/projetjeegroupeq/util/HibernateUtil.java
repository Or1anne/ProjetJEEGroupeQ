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
        Properties props = new Properties();
        try (InputStream in = HibernateUtil.class.getClassLoader().getResourceAsStream("local-config.properties")) {
            if (in == null) {
                throw new RuntimeException("Erreur lors du chargement de local-config.properties : fichier introuvable dans le classpath.");
            }
            props.load(in);
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors du chargement de local-config.properties", e);
        }

        // Convertis en Map<String, Object>
        Map<String, Object> configOverrides = new HashMap<>();
        for (String name : props.stringPropertyNames()) {
            configOverrides.put(name, props.getProperty(name));
        }

        // Initialise l’EntityManagerFactory avec les overrides
        emf = Persistence.createEntityManagerFactory("HIBERNATE", configOverrides);
    }

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}









