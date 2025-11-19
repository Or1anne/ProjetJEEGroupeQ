package com.example.projetjeegroupeq.model;
import java.util.Set;
import java.util.HashSet;

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

    @ManyToOne
    @JoinColumn(name = "idEmployee")
    private Project project;

    @ManyToMany(mappedBy = "roles")
    private Set<Employee> employees = new HashSet<>();

    public Role() {}
    public Role(String roleName) {
        this.roleName = roleName;
    }

    public int getIdRole() {
        return idRole;
    }
    public void setIdRole(Integer idRole) {}

    public String getRoleName() {
        return roleName;
    }
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

}
