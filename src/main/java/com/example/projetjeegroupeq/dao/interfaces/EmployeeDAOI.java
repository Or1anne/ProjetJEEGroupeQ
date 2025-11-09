package com.example.projetjeegroupeq.dao;

import com.example.projetjeegroupeq.model.Employee;

import com.example.projetjeegroupeq.sortingType.EmployeeSortingType;

import java.util.List;

public interface EmployeeDAO {
    void addEmployee(Employee employee);
    void updateEmployee(int id, Employee employee);
    void deleteEmployee(int id);

    Employee searchEmployeeById(int id);
    List<Employee> getAllEmployees();
    // Pour la recherche par nom, prénom
    List<Employee> getAllEmployeesSorted(EmployeeSortingType sortingType);
    List<Employee> searchEmployeesByGrade(String gradeName);
    List<Employee> searchEmployeesByPost(String postName);
    List<Employee> searchEmployeesByDepartmentId(int departmentId);
}