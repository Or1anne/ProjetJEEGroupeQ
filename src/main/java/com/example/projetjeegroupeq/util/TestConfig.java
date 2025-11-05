package com.example.projetjeegroupeq.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class TestConfig {
    public static void main(String[] args) {
        Properties props = new Properties();

        try (FileInputStream fis = new FileInputStream("src/main/resources/local-config.properties")) {
            props.load(fis);
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement du fichier de configuration : " + e.getMessage());
            return;
        }

        System.out.println("✅ Fichier chargé avec succès !");
        System.out.println("db.user      = " + props.getProperty("db.user"));
        System.out.println("db.password  = " + props.getProperty("db.password"));
        System.out.println("db.url       = " + props.getProperty("db.url"));
    }
}