package com.example.projetjeegroupeq.dao;

import com.example.projetjeegroupeq.model.Project;
import com.example.projetjeegroupeq.sortingType.ProjectSortingType;

import java.util.Date;
import java.util.List;

public interface ProjectDAO {
    public void addProject(Project project);
    public void updateProject(int id, Project project);
    public void deleteProject(int id);

    public Project searchProjectById(int id);
    public List<Project> searchByStatus(Status);
    public List<Project> searchByDeduction(double minDeduction, double maxDeduction);
    public List<Project> searchByNet(double minNet, double maxNet);
    public List<Project> searchByDate(Date minDate, Date maxDate);

    public List<Project> getAllProject();
    public List<Project> getAllProjectSorted(ProjectSortingType sortingType);
}
