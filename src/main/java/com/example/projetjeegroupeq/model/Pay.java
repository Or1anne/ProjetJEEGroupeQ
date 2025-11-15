package com.example.projetjeegroupeq.model;
import jakarta.persistence.*;

import java.sql.Date;


@Entity
@Table(name = "pay")
public class Pay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Gère la génération et l'incrémentation automatiquement
    private int id;
    private Date date;
    private double bonus;
    private double deductions;
    private double salary_net;
    @ManyToOne
    @JoinColumn(name = "idEmployee")
    private Employee employee;

// verifier ou mettre le calcul

    // Constructeur par défaut, obligatoire pour Hibernate
    public Pay() {}

    // Constructeur pour ajouter des employees
    public Pay(Date date, double bonus, double deductions, double salary_net) {

        this.date = date;
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
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
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
    public Employee getEmployee() { return employee;}
    public void setEmployee(Employee employee) {this.employee = employee;}

    }



















































































































































































































































