package com.example.projetjeegroupeq.model;

import java.util.List;

import jakarta.persistence.*;

// TODO mettre à jour à l'aide du fichier sql pour que ça corresponds

@Entity
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Gère la génération et l'incrémentation automatiquement
    @Column(name = "idEmployee")
    private int id;

    @Column (name = "lastName")
    private String lastName;

    @Column (name = "firstName")
    private String firstName;

    @Enumerated(EnumType.STRING)
    private Grade grade;

    @Column (name = "post")
    private String post;

    @Column (name = "salary")
    private Double salary;

    @Column (name = "username")
    private String username;

    @Column (name = "password")
    private String password;

    @ManyToOne
    @JoinColumn(name = "idDepartment")
    private Department department;

    @OneToMany(mappedBy = "ChefProj")
    private List<Project> projectsManaged;

    @OneToMany(mappedBy = "employee", fetch = FetchType.EAGER)
    private List<EmployeeProject> projects;

    @OneToMany(mappedBy = "employee", fetch = FetchType.EAGER)
    private List<EmployeeRole> roles;

    // Constructeur par défaut, obligatoire pour Hibernate
    public Employee() {}

    // Constructeur pour ajouter des employees
    public  Employee(String lastName, String firstName, Grade grade, String post) {
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

    public List<Project> getProjectsManaged() {
        return projectsManaged;
    }

    public void setProjectsManaged(List<Project> projectsManaged) {
        this.projectsManaged = projectsManaged;
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

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
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

    /**
     * Vérifie si cet employé a un rôle spécifique.
     *
     * @param roleName Le nom du rôle à vérifier (ex: "ADMIN", "RH", "EMPLOYE")
     * @return true si l'employé a le rôle, false sinon
     */
    public boolean hasRole(String roleName) {
        if (roleName == null || this.roles == null || this.roles.isEmpty()) {
            return false;
        }

        for (EmployeeRole employeeRole : this.roles) {
            Role role = employeeRole.getRole();
            if (role != null && roleName.equalsIgnoreCase(role.getRoleName())) {
                return true;
            }
        }

        return false;
    }

    /**
     * Vérifie si cet employé a au moins un des rôles spécifiés.
     *
     * @param roleNames Les noms des rôles à vérifier
     * @return true si l'employé a au moins un des rôles, false sinon
     */
    public boolean hasAnyRole(String... roleNames) {
        if (roleNames == null || roleNames.length == 0) {
            return false;
        }

        for (String roleName : roleNames) {
            if (hasRole(roleName)) {
                return true;
            }
        }

        return false;
    }
}
