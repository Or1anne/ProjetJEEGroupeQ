package com.example.projetjeegroupeq.dao;

import com.example.projetjeegroupeq.model.Employee;
import java.util.List;

public class EmployeeDAOImpl implements EmployeeDAO {

    //TODO Actuellement toutes les méthodes sont vides, il faut les remplir

    @Override
    public void addEmployee(Employee employee) {

    }

    @Override
    public void updateEmployee(Employee employee) {

    }

    @Override
    public void deleteEmployee(int id) {

    }

    @Override
    public Employee searchEmployeeById(int id) {
        return null;
    }

    @Override
    public List<Employee> getAllEmployees() {
        return List.of();
    }

    @Override
    public List<Employee> searchAllEmployees(String searchTerm) {
        return List.of();
    }

    @Override
    public List<Employee> searchEmployeesByGrade(String grade) {
        return List.of();
    }

    @Override
    public List<Employee> searchEmployeesByPost(String post) {
        return List.of();
    }
}
