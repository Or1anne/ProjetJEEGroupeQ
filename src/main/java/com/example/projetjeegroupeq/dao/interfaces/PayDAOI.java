package com.example.projetjeegroupeq.dao;

import com.example.projetjeegroupeq.model.Pay;

import com.example.projetjeegroupeq.sortingType.PaySortingType;

import java.util.Date;
import java.util.List;

//TODO voir comment chercher par dates

public interface PayDAO {
    public void addPay(Pay pay);
    public void updatePay(int id, Pay pay);
    public void deletePay(int id);

    public Pay searchPayById(int id);
    public List<Pay> searchByBonus(double minBonus, double maxBonus);
    public List<Pay> searchByDeduction(double minDeduction, double maxDeduction);
    public List<Pay> searchByNet(double minNet, double maxNet);
    public List<Pay> searchByDate(Date minDate, Date maxDate);

    public List<Pay> getAllPay();
    public List<Pay> getAllPaySorted(PaySortingType sortingType);
}
