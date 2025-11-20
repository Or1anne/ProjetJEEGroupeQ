package com.example.projetjeegroupeq.model;

import jakarta.persistence.*;

import java.util.List;


@Entity
@Table(name = "project")
public class Project
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Gère la génération et l'incrémentation automatiquement
    @Column(name = "idProject")
    private int id;

    @Column(name = "name")
    private String name_project;

    @Enumerated(EnumType.STRING)
    private ProjectStatus status;

    @ManyToOne
    @JoinColumn(name = "idChefPro")
    private Employee ChefProj;

    @OneToMany(mappedBy = "project")
    private List<EmployeeProject> employees;

    public  Project() {}

    public Project(String name_project, ProjectStatus status, Employee chefProj) {
        this.name_project = name_project;
        this.status = status;
        this.ChefProj = chefProj;

    }

    public int getId()
    {
        return id;
    }

    public String getName_project() {
        return name_project;
    }

    public void setName_project(String name) {
        this.name_project = name;
    }

    public ProjectStatus getStatus() {
        return status;
    }

    public void setStatus(ProjectStatus status) {
        this.status = status;
    }

    public Employee getChefProj() {
        return ChefProj;
    }

    public void setChefProj(Employee ChefProj) {
        this.ChefProj = ChefProj;
    }

    public List<EmployeeProject> getEmployees() {
        return employees;
    }

    public void setEmployees(List<EmployeeProject> employees) {
        this.employees = employees;
    }
}
