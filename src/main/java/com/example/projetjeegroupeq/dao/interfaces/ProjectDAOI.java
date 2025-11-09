package com.example.projetjeegroupeq.dao.interfaces;

import com.example.projetjeegroupeq.model.Employee;
import com.example.projetjeegroupeq.model.Project;
import com.example.projetjeegroupeq.sortingType.EmployeeSortingType;
import com.example.projetjeegroupeq.sortingType.ProjectSortingType;

import java.util.List;

//TODO Gérer la recherche par membres du projet

public interface ProjectDAOI {
    public void addProject(Project project);
    public void updateProject(int id, Project project);
    public void deleteProject(int id);

    public Project searchProjectById(int id);
    public List<Project> searchByStatus(String status);
    public Project searchByName(String name);
    public List<Project> searchByChef(Employee chef);
    // public List<Project> searchByMember(Employee member);
    // public List<Project> searchByMembers(List<Employee> members);

    public List<Project> getAllProject();
    public List<Project> getAllProjectSorted(ProjectSortingType sortingType);
}
