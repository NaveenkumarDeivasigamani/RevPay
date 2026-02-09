package com.revpay.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revpay.model.Wallet;
import com.revpay.util.DBConnection;

/**
 * DAO class responsible for wallet-related database operations.
 */

public class WalletDAO {
	
	
	/**
     * Creates a wallet for a given user with zero balance.
     */
	
	public void createWallet(int userId) throws SQLException {
		
		String sql = "INSERT INTO wallet (user_id, balance, status) VALUES (?, 0.00, 'ACTIVE')";
		
		try (Connection con = DBConnection.getConnection();
				PreparedStatement ps = con.prepareStatement(sql)) {
			
			ps.setInt(1, userId);
			ps.executeUpdate();
		}
	}
	
	/**
     * Fetches wallet details using user ID.
     */
	
	public Wallet getWalletByUserId(int userId) throws SQLException {
		
		String sql = "SELECT wallet_id, user_id, balance, status FROM wallet WHERE user_id = ?";
		
		try (Connection con = DBConnection.getConnection();
				PreparedStatement ps = con.prepareStatement(sql)) {
			
			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				Wallet wallet = new Wallet();
				
				wallet.setWalletId(rs.getInt("wallet_id"));
				wallet.setUserId(rs.getInt("user_id"));
				wallet.setBalance(rs.getDouble("balance"));
				wallet.setStatus(rs.getString("status"));
				
				return wallet;
			}
		}
		
		return null;
		
	}
	
	/**
     * Updates wallet balance for a given user.
     */
	
	public void updateBalance(int userId, double newBalance) throws SQLException {
		
		String sql = "UPDATE wallet set balance = ? WHERE user_id = ?";
		
		try (Connection con = DBConnection.getConnection();
				PreparedStatement ps = con.prepareStatement(sql)) {
			
			ps.setDouble(1, newBalance);
			ps.setInt(2, userId);
			
			ps.executeUpdate();
		}
	}
	
	
	/**
	 * Fetches hashed transaction PIN for a given user.
	 */
	public String getTransactionPinHash(int userId) throws SQLException {

	    String sql = "SELECT transaction_pin FROM users WHERE user_id = ?";

	    try (Connection con = DBConnection.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {

	        ps.setInt(1, userId);
	        ResultSet rs = ps.executeQuery();

	        if (rs.next()) {
	            return rs.getString("transaction_pin");
	        }
	    }

	    throw new IllegalStateException("Transaction PIN not found for user");
	}


}
