package com.example.projetjeegroupeq.model;

import java.util.List;
import java.util.HashSet;
import jakarta.persistence.*;

// TODO mettre à jour à l'aide du fichier sql pour que ça corresponds

@Entity
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Gère la génération et l'incrémentation automatiquement
    @Column(name = "idEmployee")
    private int id;

    private String lastName;
    private String firstName;
    private String grade;
    private String post;
    private Double salary;
    private String username;
    private String password;

    @ManyToOne
    @JoinColumn(name = "idDepartment")
    private Department department;

    @OneToMany(mappedBy = "employee")
    private List<EmployeeProject> projects;

    @OneToMany(mappedBy = "employee")
    private List<EmployeeRole> roles;

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

    public String getUsername() {return this.username;}

    public void setUsername(String username) {this.username = username;}

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public void setEmployeeRoles(List<EmployeeRole> roles) {
        this.roles = roles;
    }

    public List<EmployeeRole> getEmployeeRoles() {
        return roles;
    }

    public void setProjects(List<EmployeeProject> projects) {
        this.projects = projects;
    }

    public List<EmployeeProject> getProjects() {
        return projects;
    }
}
