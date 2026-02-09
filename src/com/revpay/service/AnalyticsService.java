package com.revpay.service;

import java.sql.ResultSet;

import com.revpay.dao.AnalyticsDAO;


/**
 * Service layer for analytics.
 */
public class AnalyticsService {

    private final AnalyticsDAO analyticsDAO = new AnalyticsDAO();

    public void showBusinessAnalytics(int businessUserId) throws Exception {

        double revenue = analyticsDAO.getTotalRevenue(businessUserId);

        double outstandingInvoices = analyticsDAO.getOutstandingInvoiceAmount(businessUserId);

        int approvedLoans = analyticsDAO.getLoanCountByStatus(businessUserId, "APPROVED");

        int repaidLoans = analyticsDAO.getLoanCountByStatus(businessUserId, "REPAID");

        int transactionCount = analyticsDAO.getTransactionCount(businessUserId);

        System.out.println("BUSINESS ANALYTICS");
        System.out.println("--------------------");
        
        System.out.println("Total Revenue       : Rs." + revenue);
        System.out.println("Outstanding Invoices: Rs." + outstandingInvoices);
        System.out.println("Approved Loans      : " + approvedLoans);
        System.out.println("Repaid Loans        : " + repaidLoans);
        System.out.println("Transactions Count  : " + transactionCount);
    }
    
    public void showTopCustomers(int businessUserId) throws Exception {

        ResultSet rs = analyticsDAO.getTopCustomers(businessUserId);

        System.out.println("\nTOP CUSTOMERS");
        System.out.println("-------------");

        boolean hasData = false;

        while (rs.next()) {
            hasData = true;
            System.out.println(
                "Customer ID: " + rs.getInt("customer_id") +
                ", Total Spent: Rs." + rs.getDouble("total_spent")
            );
        }

        if (!hasData) {
            System.out.println("No customer transactions found");
        }
    }

}
