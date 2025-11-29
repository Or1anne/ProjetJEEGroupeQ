package com.example.projetjeegroupeq.controller;

import com.example.projetjeegroupeq.dao.implementation.ReportDAO;
import com.example.projetjeegroupeq.model.Department;
import com.example.projetjeegroupeq.model.Grade;
import com.example.projetjeegroupeq.model.Project;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;

@WebServlet(name = "reportServlet", value = "/report")
public class ReportServlet extends HttpServlet {

    private final ReportDAO reportDAO = new ReportDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Map<Department, Long> employeesByDept = reportDAO.getEmployeeCountByDepartment();
        Map<Project, Long> employeesByProject = reportDAO.getEmployeeCountByProject();
        Map<Grade, Long> employeesByGrade = reportDAO.getEmployeeCountByGrade();

        req.setAttribute("employeesByDept", employeesByDept);
        req.setAttribute("employeesByProject", employeesByProject);
        req.setAttribute("employeesByGrade", employeesByGrade);

        req.getRequestDispatcher("/Report.jsp").forward(req, resp);
    }
}

