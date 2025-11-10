package com.example.projetjeegroupeq.model;


import jakarta.persistence.*;



@Entity
@Table(name = "project")
public class Project
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Gère la génération et l'incrémentation automatiquement
    private int id;

    private String name_project;

    @Enumerated(EnumType.STRING)
    private ProjectStatus status;

    @ManyToOne
    @JoinColumn(name = "idChefPro")
    private Employee ChefProj;

    public  Project()
    {}
    public Project(String name_project, ProjectStatus status, Employee chefProj)
    {
        this.name_project = name_project;
        this.status = status;
        this.ChefProj = chefProj;

    }
    public int getId()
    {
        return id;
    }
    public void setId(int id)
    {
        this.id = id;
    }
    public String getName_project()
    {
        return name_project;
    }
    public void setName_project(String name)
    {
        this.name_project = name;
    }
    public ProjectStatus getStatus()
    {
        return status;
    }
    public void setStatus(ProjectStatus status)
    {
        this.status = status;
    }
    public Employee getChefProj()
    {
        return ChefProj;
    }
    // TODO revoir cette partie là
    public void setChefProj(Employee ChefProj) {
        this.ChefProj = ChefProj;
    }

}
