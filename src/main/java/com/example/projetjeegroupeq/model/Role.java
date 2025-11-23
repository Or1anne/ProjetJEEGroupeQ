package com.example.projetjeegroupeq.model;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name="role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idRole")
    private int idRole;

    @Column(name = "roleName", unique = true)
    private String roleName;

    @OneToMany(mappedBy = "role")
    private List<EmployeeRole> employeeRoles;

    public Role() {}
    public Role(String roleName) {
        this.roleName = roleName;
    }

    public int getIdRole() {
        return idRole;
    }

    public String getRoleName() {
        return roleName;
    }
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public List<EmployeeRole> getEmployeeRoles() {
        return employeeRoles;
    }

    public void setEmployeeRoles(List<EmployeeRole> employeeRoles) {
        this.employeeRoles = employeeRoles;
    }
}
