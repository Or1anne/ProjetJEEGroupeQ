package com.example.projetjeegroupeq.dao.interfaces;

import com.example.projetjeegroupeq.dao.sortingType.RoleSortingType;
import com.example.projetjeegroupeq.model.Employee;
import com.example.projetjeegroupeq.model.Role;

import java.util.List;

public interface RoleDAOI {
    public void add(Role role);
    public void update(Role original, Role update);
    public void delete(Role role);

    public Role searchById(int id);
    public Role searchByName(String name);
    public List<Role> searchByEmployee(List<Employee> employees);

    public List<Role> getAll();
    public List<Role> getAllSorted(RoleSortingType sortingType);
}
