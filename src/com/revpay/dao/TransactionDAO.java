package com.revpay.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revpay.model.Transaction;
import com.revpay.util.DBConnection;

public class TransactionDAO {
	
	/**
     * Transfers money between two users and records transaction.
     * Uses DB transaction to ensure atomicity.
     */
	public void transferMoney(int senderUserId, int receiverUserId, double amount) throws SQLException {
		
		Connection con = null;
		
		String debitSql =
	            "UPDATE wallet SET balance = balance - ? WHERE user_id = ?";
		
		String creditSql =
	            "UPDATE wallet SET balance = balance + ? WHERE user_id = ?";
		
		String transactionSql =
	            "INSERT INTO transactions " +
	            "(sender_user_id, receiver_user_id, amount, transaction_type, status) " +
	            "VALUES (?, ?, ?, 'TRANSFER', 'SUCCESS')";
		
		try {
			
			con = DBConnection.getConnection();
            con.setAutoCommit(false);
            
            try (PreparedStatement ps = con.prepareStatement(debitSql)) {
                ps.setDouble(1, amount);
                ps.setInt(2, senderUserId);
                ps.executeUpdate();
            }
            
            try (PreparedStatement ps = con.prepareStatement(creditSql)) {
                ps.setDouble(1, amount);
                ps.setInt(2, receiverUserId);
                ps.executeUpdate();
            }
            
            try (PreparedStatement ps = con.prepareStatement(transactionSql)) {
                ps.setInt(1, senderUserId);
                ps.setInt(2, receiverUserId);
                ps.setDouble(3, amount);
                ps.executeUpdate();
            }
            
            con.commit();
            
		} catch (Exception e) {
			if (con != null ) {
				con.rollback();
			}
			throw e;
		} finally {
			if (con != null) {
				con.setAutoCommit(true);
				con.close();
			}
		}
	
	}
	
	 /**
     * Fetches all transactions related to a user
     * (sent or received).
     */
    public List<Transaction> getTransactionsByUser(int userId) throws SQLException {

        String sql =
                "SELECT transaction_id, sender_user_id, receiver_user_id, amount, transaction_type, status, created_at " +
                "FROM transactions " +
                "WHERE sender_user_id = ? OR receiver_user_id = ? " +
                "ORDER BY created_at DESC";

        List<Transaction> transactions = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setInt(2, userId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Transaction tx = new Transaction();
                tx.setTransactionId(rs.getInt("transaction_id"));
                tx.setSenderUserId(rs.getInt("sender_user_id"));
                tx.setReceiverUserId(rs.getInt("receiver_user_id"));
                tx.setAmount(rs.getDouble("amount"));
                tx.setTransactionType(rs.getString("transaction_type"));
                tx.setStatus(rs.getString("status"));
                tx.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());

                transactions.add(tx);
            }
        }

        return transactions;
    }
}

