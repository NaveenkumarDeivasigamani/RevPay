package com.revpay.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revpay.model.User;
import com.revpay.util.DBConnection;

public class UserDAO {
	
	public boolean emailExits(String email) throws SQLException {
		
		String sql = "SELECT 1 FROM users WHERE email = ?";
		
		try (Connection con = DBConnection.getConnection();
				PreparedStatement ps = con.prepareStatement(sql)) {
			
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			
			return rs.next();
		}
	}
	
	public boolean phoneExists(String phone) throws SQLException {

	    String sql = "SELECT 1 FROM users WHERE phone = ?";

	    try (Connection con = DBConnection.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {

	        ps.setString(1, phone);
	        ResultSet rs = ps.executeQuery();
	        return rs.next();
	    }
	}

	
	public int insertUser(User user, String hashedPassword, String hashedTransactionPin, String hashedSecurityAnswer) throws SQLException {
		
		String sql = "INSERT INTO users (full_name, email, phone, password_hash, transaction_pin, security_question, security_answer_hash, account_type) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		
		try (Connection con = DBConnection.getConnection();
				PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
					
					ps.setString(1, user.getFullName());
					ps.setString(2, user.getEmail());
					ps.setString(3, user.getPhone());
					ps.setString(4, hashedPassword);
					ps.setString(5, hashedTransactionPin);
					ps.setString(6, user.getSecurityQuestion());
				    ps.setString(7, hashedSecurityAnswer);
				    ps.setString(8, user.getAccountType());
					
					ps.executeUpdate();
					
					ResultSet rs = ps.getGeneratedKeys();
					if (rs.next()) {
						return rs.getInt(1);
					}
		}
		throw new IllegalStateException("Failed to create user");
	}
	
	/**
	 * Registers a business user in the system.
	 * Returns generated user_id.
	 */
	public int registerBusinessUser(String fullName, String email, String phone, String passwordHash, String pinHash, String businessName, String businessType, String taxId, String businessAddress) throws SQLException {
		
		String sql =
		        "INSERT INTO users " +
		        "(full_name, email, phone, password_hash, transaction_pin, account_type, business_name, business_type, tax_id, business_address) " +
		        "VALUES (?, ?, ?, ?, ?, 'BUSINESS', ?, ?, ?, ?)";
		
		
		try (Connection con = DBConnection.getConnection();
		         PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
					
			 		ps.setString(1, fullName);
			 		ps.setString(2, email);
			        ps.setString(3, phone);
			        ps.setString(4, passwordHash);
			        ps.setString(5, pinHash);
			        ps.setString(6, businessName);
			        ps.setString(7, businessType);
			        ps.setString(8, taxId);
			        ps.setString(9, businessAddress);
			        
			        ps.executeUpdate();

			        ResultSet rs = ps.getGeneratedKeys();
			        
			        if (rs.next()) {
			        	return rs.getInt(1);
			        }
		}
		
		throw new IllegalStateException("Business user registration failed");
	}
	
	
	public void updateBusinessDetails(
	        int userId,
	        String businessName,
	        String businessType,
	        String taxId,
	        String businessAddress) throws SQLException {

	    String sql =
	        "UPDATE users SET " +
	        "account_type = 'BUSINESS', business_name = ?, business_type = ?, tax_id = ?, business_address = ?" +
	        "WHERE user_id = ?";

	    try (Connection con = DBConnection.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {

	        ps.setString(1, businessName);
	        ps.setString(2, businessType);
	        ps.setString(3, taxId);
	        ps.setString(4, businessAddress);
	        ps.setInt(5, userId);

	        ps.executeUpdate();
	    }
	}

	
	public User getUserById(int userId) throws SQLException {

	    String sql = "SELECT user_id, account_type FROM users WHERE user_id = ?";

	    try (Connection con = DBConnection.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {

	        ps.setInt(1, userId);
	        ResultSet rs = ps.executeQuery();

	        if (rs.next()) {
	        	
	            User user = new User();
	            
	            user.setUserId(rs.getInt("user_id"));
	            user.setAccountType(rs.getString("account_type"));
	            
	            return user;
	        }
	    }
	    return null;
	}
	
	
	public User findByEmailOrPhone(String login) throws SQLException {
	    String sql =
	        "SELECT user_id, email, phone, password_hash, account_type " +
	        "FROM users WHERE email = ? OR phone = ?";

	    try (Connection con = DBConnection.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {

	        ps.setString(1, login);
	        ps.setString(2, login);

	        ResultSet rs = ps.executeQuery();

	        if (rs.next()) {
	            User user = new User();
	            user.setUserId(rs.getInt("user_id"));
	            user.setEmail(rs.getString("email"));
	            user.setPhone(rs.getString("phone"));
	            user.setPasswordHash(rs.getString("password_hash"));
	            user.setAccountType(rs.getString("account_type"));
	            return user;
	        }
	    }
	    return null;
	}
	
	
	public User getSecurityDetailsByLogin(String login) throws SQLException {

	    String sql =
	        "SELECT user_id, security_question, security_answer_hash " +
	        "FROM users WHERE email = ? OR phone = ?";

	    try (Connection con = DBConnection.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {

	        ps.setString(1, login);
	        ps.setString(2, login);

	        ResultSet rs = ps.executeQuery();

	        if (rs.next()) {
	            User user = new User();
	            user.setUserId(rs.getInt("user_id"));
	            user.setSecurityQuestion(rs.getString("security_question"));

	            // temporarily store hashed answer in passwordHash field
	            user.setPasswordHash(rs.getString("security_answer_hash"));
	            return user;
	        }
	    }
	    return null;
	}

	
	public User getUserByIdWithPassword(int userId) throws SQLException {

	    String sql = "SELECT user_id, password_hash FROM users WHERE user_id = ?";

	    try (Connection con = DBConnection.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {

	        ps.setInt(1, userId);
	        ResultSet rs = ps.executeQuery();

	        if (rs.next()) {
	            User user = new User();
	            user.setUserId(userId);
	            user.setPasswordHash(rs.getString("password_hash"));
	            return user;
	        }
	    }
	    throw new IllegalArgumentException("User not found");
	}

	public void updatePassword(int userId, String newHash) throws SQLException {

	    String sql = "UPDATE users SET password_hash = ? WHERE user_id = ?";

	    try (Connection con = DBConnection.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {

	        ps.setString(1, newHash);
	        ps.setInt(2, userId);
	        ps.executeUpdate();
	    }
	}
	
	
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

	    throw new IllegalArgumentException("Transaction PIN not found");
	}

	
	
	public void updateTransactionPin(int userId, String hashedPin) throws SQLException {

	    String sql = "UPDATE users SET transaction_pin = ? WHERE user_id = ?";

	    try (Connection con = DBConnection.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {

	        ps.setString(1, hashedPin);
	        ps.setInt(2, userId);

	        ps.executeUpdate();
	    }
	}
	
	







}
							
			
			
	