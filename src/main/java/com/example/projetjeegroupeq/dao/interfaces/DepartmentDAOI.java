package com.example.projetjeegroupeq.dao.interfaces;

import com.example.projetjeegroupeq.model.Department;
import com.example.projetjeegroupeq.model.Employee;
import com.example.projetjeegroupeq.dao.sortingType.DepartmentSortingType;

import java.util.List;

public interface DepartmentDAOI {
    public void add(Department department);
    public void update(Department original, Department update);
    public void delete(Department department);

    public Department searchById(int id);
    public Department searchByName(String name);
    public List<Department> searchByChef(Employee chef);
    public List<Department> searchByMembers(List<Employee> members);

    public List<Department> getAll();
    public List<Department> getAllSorted(DepartmentSortingType sortingType);
}