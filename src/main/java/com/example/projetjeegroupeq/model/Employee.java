package com.example.projetjeegroupeq.model;


import jakarta.persistence.*;

// TODO mettre à jour à l'aide du fichier sql pour que ça corresponds

@Entity
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Gère la génération et l'incrémentation automatiquement
    private int id;

    private String lastName;
    private String firstName;
    private String grade;
    private String post;
    private Double salary;
    private String username;
    private String password;
    private String department;


    // Constructeur par défaut, obligatoire pour Hibernate
    public Employee() {}

    // Constructeur pour ajouter des employees
    public  Employee(String lastName, String firstName, String grade, String post) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.grade = grade;
        this.post = post;
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

}
