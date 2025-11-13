package com.example.projetjeegroupeq.dao.interfaces;

import com.example.projetjeegroupeq.model.Department;
import com.example.projetjeegroupeq.model.Employee;
import com.example.projetjeegroupeq.model.Department;
import com.example.projetjeegroupeq.sortingType.DepartmentSortingType;

import java.util.List;

public interface DepartmentDAOI {
    public void addDepartment(Department department);
    public void updateDepartment(int id, Department department);
    public void deleteDepartment(int id);

    public Department searchDepartmentById(int id);
    public Department searchByName(String name);
    public List<Department> searchByChef(Employee chef);

    public List<Department> getAllDepartment();
    public List<Department> getAllDepartmentSorted(DepartmentSortingType sortingType);
}
