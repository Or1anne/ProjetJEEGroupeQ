package com.example.projetjeegroupeq.dao.interfaces;

import com.example.projetjeegroupeq.model.Employee;
import com.example.projetjeegroupeq.model.Project;
import com.example.projetjeegroupeq.model.ProjectStatus;
import com.example.projetjeegroupeq.dao.sortingType.ProjectSortingType;

import java.util.List;

//TODO Gérer la recherche par membres du projet

public interface ProjectDAOI {
    public void add(Project project);
    public void update(Project original, Project update);
    public void delete(Project project);

    public Project searchById(int id);
    public List<Project> searchByStatus(ProjectStatus status);
    public Project searchByName(String name);
    public List<Project> searchByChef(Employee chef);
    public List<Project> searchByMembers(List<Employee> members);

    public List<Project> getAll();
    public List<Project> getAllSorted(ProjectSortingType sortingType);
}
