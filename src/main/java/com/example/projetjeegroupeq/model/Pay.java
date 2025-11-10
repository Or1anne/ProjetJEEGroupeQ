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
    //TODO mettre foreing key employee
// verifier ou mettre le calcul

    // Constructeur par défaut, obligatoire pour Hibernate
    public Pay() {}

    // Constructeur pour ajouter des employees
    public Pay(int month, int year, double bonus, double deductions, double salary_net) {

        this.month = month;
        this.year = year;
        this.bonus = bonus;
        this.deductions = deductions;
        this.salary_net = salary_net;

    }

    public int getId() {
        return 0;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getMonth() {
        return month;
    }
    public void setMonth(int month) {
        this.month = month;
    }
    public int getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = year;
    }
    public double getBonus() {
        return bonus;
    }
    public void setBonus(double bonus) {
        this.bonus = bonus;
    }
    public double getDeductions() {
        return deductions;
    }
    public void setDeductions(double deductions) {
        this.deductions = deductions;
    }
    public double getSalary_net() {
        return salary_net;
    }
    public void setSalary_net(double salary_net) {
        this.salary_net = salary_net;
    }

    }



















































































































































































































































