package com.example.projetjeegroupeq.dao.testDAO;

import com.example.projetjeegroupeq.dao.implementation.*;
import com.example.projetjeegroupeq.model.*;

import java.util.List;

public class TestDAO {
    public static void main(String[] args) {
        Employee employee = new Employee();

        Department test = new Department();

        employee.setFirstName("TestFirst1");
        employee.setLastName("TestLast1");
        employee.setGrade("TestGrade1");
        employee.setPost("TestPost1");
        employee.setUsername("TestUsername4");
        employee.setPassword("TestPassowrd1");
        employee.setSalary(1.0);

        EmployeeDAO eDAO = new EmployeeDAO();

        eDAO.addEmployee(employee);

        List<Employee> testL = List.of();

        testL = eDAO.getAllEmployees();

        for (Employee e : testL) {
            System.out.println(e.getFirstName());
            System.out.println(e.getLastName());
            System.out.println(e.getUsername());
            System.out.println(e.getGrade());
            System.out.println(e.getPost());
            System.out.println(e.getSalary());
            System.out.println(e.getId());
            System.out.println();
        }
    }
}
