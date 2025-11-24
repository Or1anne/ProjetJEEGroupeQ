package com.example.projetjeegroupeq.dao.interfaces;

import com.example.projetjeegroupeq.model.Department;
import com.example.projetjeegroupeq.model.Employee;

import com.example.projetjeegroupeq.model.Project;
import com.example.projetjeegroupeq.dao.sortingType.EmployeeSortingType;

import java.util.List;

public interface EmployeeDAOI {
    void add(Employee employee);
    void update(Employee original, Employee employee);
    void delete(Employee employee);

    Employee searchById(int id);
    List<Employee> getAll();
    // Pour la recherche par nom, prénom
    List<Employee> getAllSorted(EmployeeSortingType sortingType);
    List<Employee> searchByGrade(String gradeName);
    List<Employee> searchByPost(String postName);
    List<Employee> searchByDepartmentId(Department department);
    List<Employee> searchByProjects(List<Project> projects);
}