package com.example.projetjeegroupeq.model;

import jakarta.persistence.*;

@Entity
@Table(name = "department")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String departmentName;

    // TODO transformer en @JoinColomn clée étrangere
    @ManyToOne
    @JoinColumn()
    private Employee chefDepartment;

    public Department() {}
    public Department(String departmentName) {
        this.departmentName = departmentName;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getDepartmentName() {
        return departmentName;
    }
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
    public void setChefDepartment(Employee chefDepartment) {
        this.chefDepartment = chefDepartment;
    }
    public Employee getChefDepartment() {
        return chefDepartment;
    }
}
