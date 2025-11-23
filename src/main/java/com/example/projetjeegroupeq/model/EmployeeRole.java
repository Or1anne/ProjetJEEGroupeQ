package com.example.projetjeegroupeq.model;

import com.example.projetjeegroupeq.model.embededId.EmployeeRoleId;
import jakarta.persistence.*;

@Entity
@Table(name = "employeeRole")
public class EmployeeRole {
    @EmbeddedId
    private EmployeeRoleId id;

    @ManyToOne
    @MapsId("idEmployee")
    @JoinColumn(name = "idEmployee")
    private Employee employee;

    @ManyToOne
    @MapsId("idRole")
    @JoinColumn(name = "idRole")
    private Role role;

    public EmployeeRole() {}

    public EmployeeRoleId getId() {
        return id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public Role getRole() {
        return role;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
