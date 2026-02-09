package com.revpay.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revpay.model.Invoice;
import com.revpay.util.DBConnection;

/**
 * DAO class for invoice database operations.
 */
public class InvoiceDAO {
	
	
	/**
     * Creates a new invoice.
     * Returns generated invoice_id.
     */
	public int createInvoice(Invoice invoice) throws SQLException {

        String sql =
            "INSERT INTO invoices " +
            "(business_user_id, customer_user_id, amount, description, status) " +
            "VALUES (?, ?, ?, ?, 'UNPAID')";
        
        try (Connection con = DBConnection.getConnection();
                PreparedStatement ps =
                    con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

               ps.setInt(1, invoice.getBusinessUserId());
               ps.setInt(2, invoice.getCustomerUserId());
               ps.setDouble(3, invoice.getAmount());
               ps.setString(4, invoice.getDescription());

               ps.executeUpdate();
               
               ResultSet rs = ps.getGeneratedKeys();
               
               if (rs.next()) {
                   return rs.getInt(1);
               }
        }
        
        throw new IllegalStateException("Invoice creation failed");
        
	}
	
	
	/**
     * Fetches all invoices for a business user.
     */
    public List<Invoice> getInvoicesByBusinessUser(int businessUserId) throws SQLException {

        String sql = "SELECT * FROM invoices WHERE business_user_id = ?";

        List<Invoice> invoices = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, businessUserId);
            
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
            	
                Invoice invoice = new Invoice();
                
                invoice.setInvoiceId(rs.getInt("invoice_id"));
                invoice.setBusinessUserId(rs.getInt("business_user_id"));
                invoice.setCustomerUserId(rs.getInt("customer_user_id"));
                invoice.setAmount(rs.getDouble("amount"));
                invoice.setDescription(rs.getString("description"));
                invoice.setStatus(rs.getString("status"));
                invoice.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());

                invoices.add(invoice);
            }
        }
        
        return invoices;
    }
    
    
    /**
     * Updates invoice status (PAID / UNPAID).
     */
    public void updateInvoiceStatus(int invoiceId, String status) throws SQLException {

        String sql = "UPDATE invoices SET status = ? WHERE invoice_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setInt(2, invoiceId);
            ps.executeUpdate();
        }
    }
    
    
    /**
     * Fetch invoice by invoice_id.
     */
    public Invoice getInvoiceById(int invoiceId) throws SQLException {

        String sql = "SELECT * FROM invoices WHERE invoice_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, invoiceId);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
            	
                Invoice invoice = new Invoice();
                
                invoice.setInvoiceId(rs.getInt("invoice_id"));
                invoice.setBusinessUserId(rs.getInt("business_user_id"));
                invoice.setCustomerUserId(rs.getInt("customer_user_id"));
                invoice.setAmount(rs.getDouble("amount"));
                invoice.setDescription(rs.getString("description"));
                invoice.setStatus(rs.getString("status"));
                invoice.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                
                return invoice;
            }
        }
        return null;
    }
}
        

