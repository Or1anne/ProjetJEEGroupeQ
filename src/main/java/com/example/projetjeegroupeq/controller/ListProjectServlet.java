package com.example.projetjeegroupeq.controller;

import java.io.*;

import com.example.projetjeegroupeq.dao.sortingType.ProjectSortingType;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import com.example.projetjeegroupeq.dao.implementation.ProjectDAO;

//TODO permettre de donner les résulats triés en fonction de critères énumérés

@WebServlet(name = "ListProjectServlet", value = "/listProjects")
public class ListProjectServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        ProjectDAO daoProject = new ProjectDAO();

        request.setAttribute("projectsList", daoProject.getAll());

        request.getRequestDispatcher("/ListProject.jsp").forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        ProjectDAO daoProject = new ProjectDAO();

        ProjectSortingType sortingType = (ProjectSortingType) request.getAttribute("sortingType");

        if (sortingType == null) {
            request.setAttribute("projectsList", daoProject.getAll());

            request.getRequestDispatcher("/ListProject.jsp").forward(request, response);
            return;
        }

        request.setAttribute("projectsList", daoProject.getAllSorted(sortingType));
    }
}