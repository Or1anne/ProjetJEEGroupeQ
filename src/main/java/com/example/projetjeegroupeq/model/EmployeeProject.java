package com.example.projetjeegroupeq.model;

import com.example.projetjeegroupeq.model.embededId.EmployeeProjectId;
import com.example.projetjeegroupeq.model.embededId.EmployeeRoleId;
import jakarta.persistence.*;

@Entity
@Table(name = "employeeProject")
public class EmployeeProject {
    @EmbeddedId
    private EmployeeProjectId id;

    @ManyToOne
    @MapsId("idEmployee")
    @JoinColumn(name = "idEmployee")
    private Employee employee;

    @ManyToOne
    @MapsId("idProject")
    @JoinColumn(name = "idProject")
    private Project project;

    public EmployeeProject() {}

    public EmployeeProjectId getId() {
        return id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public Project getProject() {
        return project;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
