package com.revpay.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revpay.util.DBConnection;


/**
 * DAO for analytics and reporting queries.
 */
public class AnalyticsDAO {

    
	/**
     * Total revenue received by a business user.
     */
    public double getTotalRevenue(int businessUserId) throws SQLException {

        String sql =
            "SELECT COALESCE(SUM(amount), 0) AS total FROM transactions WHERE receiver_user_id = ? AND status = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, businessUserId);
            ps.setString(2, "SUCCESS");
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
            	
                return rs.getDouble("total");
            }
        }
        return 0;
    }

    
    /**
     * Total outstanding invoice amount.
     */
    public double getOutstandingInvoiceAmount(int businessUserId) throws SQLException {

        String sql = "SELECT COALESCE(SUM(amount), 0) AS total FROM invoices WHERE business_user_id = ? AND status = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, businessUserId);
            ps.setString(2, "UNPAID");
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getDouble("total");
            }
        }
        return 0;
    }

    
    /**
     * Count loans by status.
     */
    public int getLoanCountByStatus(int businessUserId, String status) throws SQLException {

        String sql = "SELECT COUNT(*) AS count FROM loans WHERE business_user_id = ? AND status = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, businessUserId);
            ps.setString(2, status);
            
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("count");
            }
        }
        return 0;
    }

    
    /**
     * Total number of successful transactions (sent + received).
     */
    public int getTransactionCount(int businessUserId) throws SQLException {

        String sql = "SELECT COUNT(*) AS count FROM transactions WHERE (sender_user_id = ? OR receiver_user_id = ?) AND status = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, businessUserId);
            ps.setInt(2, businessUserId);
            ps.setString(3, "SUCCESS");
            
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("count");
            }
        }
        return 0;
    }
    
    
    /**
     * Fetch top customers by total amount paid to this business.
     */
    public ResultSet getTopCustomers(int businessUserId) throws SQLException {

        String sql =
            "SELECT sender_user_id AS customer_id, " +
            "       SUM(amount) AS total_spent " +
            "FROM transactions " +
            "WHERE receiver_user_id = ? AND status = 'SUCCESS' " +
            "GROUP BY sender_user_id " +
            "ORDER BY total_spent DESC " +
            "LIMIT 5";

        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, businessUserId);

        return ps.executeQuery();
    }

}
