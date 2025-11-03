package com.example.projetjeegroupeq.dao;

import com.example.projetjeegroupeq.model.Employee;

import java.util.List;

public interface EmployeeDAO {
    void addEmployee(Employee employee);
    void updateEmployee(Employee employee);
    void deleteEmployee(int id);
    Employee searchEmployeeById(int id);
    List<Employee> getAllEmployees();
    // Pour la recherche par nom, prénom
    List<Employee> searchAllEmployees(String searchTerm);
    List<Employee> searchEmployeesByGrade(String grade);
    List<Employee> searchEmployeesByPost(String post);
}
