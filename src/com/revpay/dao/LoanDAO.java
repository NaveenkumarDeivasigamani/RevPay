package com.revpay.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revpay.model.Loan;
import com.revpay.util.DBConnection;


/**
 * DAO class for loan database operations.
 */
public class LoanDAO {

    /**
     * Applies for a new loan.
     * Status defaults to PENDING.
     */
    public int applyLoan(Loan loan) throws SQLException {

        String sql = "INSERT INTO loans (business_user_id, amount, purpose, status) " +
        						"VALUES (?, ?, ?, 'APPROVED')";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, loan.getBusinessUserId());
            ps.setDouble(2, loan.getAmount());
            ps.setString(3, loan.getPurpose());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            
            if (rs.next()) {
                return rs.getInt(1); // loan_id
            }
        }

        throw new IllegalStateException("Loan application failed");
    }

    
    /**
     * Fetches all loans for a business user.
     */
    public List<Loan> getLoansByBusinessUser(int businessUserId) throws SQLException {

        String sql = "SELECT * FROM loans WHERE business_user_id = ?";

        List<Loan> loans = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, businessUserId);
            
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
            	
                Loan loan = new Loan();
                
                loan.setLoanId(rs.getInt("loan_id"));
                loan.setBusinessUserId(rs.getInt("business_user_id"));
                loan.setAmount(rs.getDouble("amount"));
                loan.setPurpose(rs.getString("purpose"));
                loan.setStatus(rs.getString("status"));
                loan.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());

                loans.add(loan);
            }
        }

        return loans;
    }

    
    /**
     * Updates loan status (APPROVED / REJECTED).
     */
    public void updateLoanStatus(int loanId, String status) throws SQLException {

        String sql = "UPDATE loans SET status = ? WHERE loan_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setInt(2, loanId);
            
            ps.executeUpdate();
        }
    }
    
    
    public Loan getLoanById(int loanId) throws SQLException {

        String sql = "SELECT * FROM loans WHERE loan_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, loanId);
            
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
            	
                Loan loan = new Loan();
                
                loan.setLoanId(rs.getInt("loan_id"));
                loan.setBusinessUserId(rs.getInt("business_user_id"));
                loan.setAmount(rs.getDouble("amount"));
                loan.setPurpose(rs.getString("purpose"));
                loan.setStatus(rs.getString("status"));
                loan.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                
                return loan;
            }
        }
        return null;
    }

}
