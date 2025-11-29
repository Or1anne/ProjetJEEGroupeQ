package com.example.projetjeegroupeq.model.embededId;

import jakarta.persistence.*;

import java.io.Serializable;

@Embeddable
public class EmployeeRoleId implements Serializable {
    private Integer idEmployee;
    private Integer idRole;

    public EmployeeRoleId() {}

    public EmployeeRoleId(Integer idEmployee, Integer idRole) {
        this.idEmployee = idEmployee;
        this.idRole = idRole;
    }

    public Integer getIdEmployee() {
        return idEmployee;
    }

    public Integer getIdRole() {
        return idRole;
    }
}
