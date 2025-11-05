package com.example.projetjeegroupeq.model;
import jakarta.persistence.*;


@Entity
@Table(name = "pay")
public class Pay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Gère la génération et l'incrémentation automatiquement
    private int id;
    private int month;
    private int year;
    private double bonus;
    private double deductions;
    private double salary_net;
// verifier ou mettre le calcul

    // Constructeur par défaut, obligatoire pour Hibernate
    public Pay() {}

    // Constructeur pour ajouter des employees
    public Pay(int month, int year, double bonus, double deduction, double salary_net) {

        this.month = month;
        this.year = year;
        this.bonus = bonus;
        this.deduction = deduction;
        this.salary_net = salary_net;
    }

    public int getId() {}

    }

















































































































































































































































    }

