package com.example.projetjeegroupeq.model.embededId;

import jakarta.persistence.*;

import java.io.Serializable;

@Embeddable
public class EmployeeProjectId implements Serializable {
    private Integer idEmployee;
    private Integer idProject;

    public EmployeeProjectId() {}

    public Integer getIdEmployee() {
        return idEmployee;
    }

    public Integer getIdProject() {
        return idProject;
    }
}
