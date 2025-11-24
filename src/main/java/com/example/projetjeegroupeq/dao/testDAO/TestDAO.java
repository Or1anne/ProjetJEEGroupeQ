package com.example.projetjeegroupeq.dao.testDAO;

import com.example.projetjeegroupeq.dao.implementation.*;
import com.example.projetjeegroupeq.model.*;
import com.example.projetjeegroupeq.dao.sortingType.EmployeeSortingType;

import java.util.List;

public class TestDAO {
    public static void main(String[] args) {
        Employee employee = new Employee();

        Department test = new Department();

        employee.setFirstName("TestFirst1");
        employee.setLastName("TestLast1");
        employee.setGrade("TestGrade1");
        employee.setPost("TestPost1");
        employee.setUsername("TestUsername1");
        employee.setPassword("TestPassowrd1");
        employee.setSalary(100.0);

        Employee employee2 = new Employee();
        employee2.setFirstName("TestFirst2");
        employee2.setLastName("TestLast2");
        employee2.setGrade("TestGrade2");
        employee2.setPost("TestPost2");
        employee2.setUsername("TestUsername2");
        employee2.setPassword("TestPassowrd2");
        employee2.setSalary(2.0);

        EmployeeDAO eDAO = new EmployeeDAO();

        List<Employee> testL = List.of();
        testL = eDAO.getAll();

        eDAO.delete(employee);
        eDAO.delete(employee2);

        for (Employee e : testL) {
            eDAO.delete(e);
        }

        eDAO.add(employee);
        eDAO.add(employee2);

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

        testL = eDAO.getAllSorted(EmployeeSortingType.BY_SALARY);

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
