package com.example.projetjeegroupeq.dao.interfaces;

import com.example.projetjeegroupeq.model.Employee;
import com.example.projetjeegroupeq.model.Pay;

import com.example.projetjeegroupeq.dao.sortingType.PaySortingType;

import java.util.Date;
import java.util.List;

//TODO voir comment chercher par dates

public interface PayDAOI {
    public void add(Pay pay);
    public void update(Pay original, Pay update);
    public void delete(Pay pay);

    public Pay searchById(int id);
    public List<Pay> searchByBonus(double minBonus, double maxBonus);
    public List<Pay> searchByDeduction(double minDeduction, double maxDeduction);
    public List<Pay> searchByNet(double minNet, double maxNet);
    public List<Pay> searchByDate(Date minDate, Date maxDate);
    public List<Pay> searchByEmployee(Employee employee);

    public List<Pay> getAll();
    public List<Pay> getAllSorted(PaySortingType sortingType);
}
