package com.example.projetjeegroupeq.controller;

import com.example.projetjeegroupeq.dao.implementation.EmployeeDAO;
import com.example.projetjeegroupeq.dao.implementation.PayDAO;
import com.example.projetjeegroupeq.model.Employee;
import com.example.projetjeegroupeq.model.Pay;
import com.example.projetjeegroupeq.util.PayPdfGenerator;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@WebServlet("/pay")
public class PayServlet extends HttpServlet {

    private PayDAO payDAO;
    private EmployeeDAO employeeDAO;

    @Override
    public void init() {
        this.payDAO = new PayDAO();
        this.employeeDAO = new EmployeeDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            action = "list";
        }

        switch (action.toLowerCase()) {
            case "list" -> listPay(req, resp);
            case "add" -> handleAdd(req,resp);
            case "view" -> handleView(req, resp);
            case "delete" -> handleDelete(req, resp);
            case "pdf" -> generatePdf(req, resp);
            default -> listPay(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Pay payslip = new Pay();
        try {
            populatePay(req, resp, payslip);
            payDAO.add(payslip);
            resp.sendRedirect(req.getContextPath() + "/pay?action=list");
        } catch (ServletException e) {
            req.setAttribute("errorMessage", e.getMessage());
            req.setAttribute("payslip", payslip);
            req.getRequestDispatcher("/FormPay.jsp").forward(req, resp);
        }
    }

    private void listPay(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String employeeIdStr = req.getParameter("employeeId");
        List<Pay> pays;

        if (employeeIdStr != null && !employeeIdStr.isEmpty()) {
            // Cas : Historique d'un employé spécifique
            try {
                int id = Integer.parseInt(employeeIdStr);
                Employee employee = employeeDAO.searchById(id);

                if (employee != null) {
                    pays = payDAO.searchByEmployee(employee);
                    // On passe l'employé à la JSP pour afficher son nom dans le titre par exemple
                    req.setAttribute("currentEmployee", employee);
                } else {
                    pays = List.of(); // Employé non trouvé
                }
            } catch (NumberFormatException e) {
                pays = List.of();
            }
        } else {
            // Cas : Historique global (tous les employés)
            pays = payDAO.getAll();
        }

        req.setAttribute("pays", pays);
        req.getRequestDispatcher("ListPay.jsp").forward(req, resp);
    }

    private void handleAdd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String employeeIdStr = req.getParameter("employeeId");

        if (employeeIdStr != null && !employeeIdStr.isEmpty()) {
            try {
                int id = Integer.parseInt(employeeIdStr);
                Employee employee = employeeDAO.searchById(id);

                if (employee != null) {
                    req.setAttribute("prefillEmployee", employee);
                }

            } catch (NumberFormatException ignore) {}
        }

        req.getRequestDispatcher("/FormPay.jsp").forward(req, resp);

    }

    private void populatePay(HttpServletRequest req, HttpServletResponse resp, Pay payslip)
            throws ServletException, IOException {

        // 1. Récupérer employé (obligatoire)
        String employeeIdStr = extractRequiredParameter(req, "employeeId", "L'employé est obligatoire.");
        int employeeId;
        try {
            employeeId = Integer.parseInt(employeeIdStr);
        } catch (NumberFormatException e) {
            throw new ServletException("Identifiant d'employé invalide.");
        }

        Employee employee = employeeDAO.searchById(employeeId);
        if (employee == null) {
            throw new ServletException("Employé introuvable.");
        }
        payslip.setEmployee(employee);

        // Récupérer le mois (format "YYYY-MM") + convertir en Date SQL
        String month = extractRequiredParameter(req, "month", "Le mois est obligatoire.");
        java.sql.Date payDate = java.sql.Date.valueOf(month + "-31");
        payslip.setDate(payDate);

        // Salaire de base (obligatoire)
        double baseSalary = parseDoubleRequired(req, "baseSalary", "Le salaire de base est obligatoire.");

        // Prime (optionnel)
        double bonus = parseDoubleOptional(req.getParameter("bonus"));

        // Déductions (optionnel)
        double deductions = parseDoubleOptional(req.getParameter("deduction"));

        payslip.setBonus(bonus);
        payslip.setDeductions(deductions);

        // Calcul du net
        double net = baseSalary + bonus - deductions;
        payslip.setSalary_net(net);
    }



    private void handleView(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int payId;
        try {
            payId = parseId(req.getParameter("payId"), "Identifiant de paie manquant");
        } catch (IllegalArgumentException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
            return;
        }

        Pay pay = payDAO.searchById(payId);
        if (pay == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Paie introuvable");
            return;
        }

        req.setAttribute("pay", pay);
        req.setAttribute("employee", pay.getEmployee());
        req.setAttribute("fromEmployeeId", req.getParameter("employeeId"));
        req.getRequestDispatcher("/ViewPay.jsp").forward(req, resp);
    }

    private void handleDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            // On récupère l'ID de la fiche de paie à supprimer
            int payId = parseId(req.getParameter("payId"), "Identifiant de paie manquant pour la suppression");

            // On récupère la fiche de paie réelle
            Pay payToDelete = payDAO.searchById(payId);

            // On supprime seulement si elle existe
            if (payToDelete != null) {
                payDAO.delete(payToDelete);
            }

            // Redirection vers la liste des fiches de paie
            resp.sendRedirect(req.getContextPath() + "/pay?action=list");
        } catch (IllegalArgumentException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        }
    }


    private String trimToNull(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }

    private int parseId(String idParam, String errorMessage) {
        String trimmed = trimToNull(idParam);
        if (trimmed == null) {
            throw new IllegalArgumentException(errorMessage);
        }
        try {
            return Integer.parseInt(trimmed);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Identifiant invalide.");
        }
    }
    private String extractRequiredParameter(HttpServletRequest req, String name, String errorMessage)
            throws ServletException {
        String value = trimToNull(req.getParameter(name));
        if (value == null) {
            throw new ServletException(errorMessage);
        }
        return value;
    }

    private double parseDoubleRequired(HttpServletRequest req, String param, String errorMessage)
            throws ServletException {
        String value = extractRequiredParameter(req, param, errorMessage);
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            throw new ServletException("Valeur invalide pour " + param);
        }
    }

    private double parseDoubleOptional(String value) {
        try {
            return value == null || value.isBlank() ? 0.0 : Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }


    private void generatePdf(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            int payId = Integer.parseInt(req.getParameter("payId"));
            Pay pay = payDAO.searchById(payId);

            if (pay == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Fiche de paie introuvable");
                return;
            }

            // Configurer la réponse PDF
            resp.setContentType("application/pdf");
            resp.setHeader("Content-Disposition", "attachment; filename=fiche_paie_" + payId + ".pdf");

            // Déléguer la génération à PayPdfGenerator
            OutputStream out = resp.getOutputStream();
            PayPdfGenerator.generatePayPdf(pay, out);
            out.close();

        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Identifiant de paie invalide");
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(500, "Erreur lors de la génération du PDF : " + e.getMessage());
        }
    }


}