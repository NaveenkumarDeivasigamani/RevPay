package com.revpay.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revpay.model.PaymentMethod;
import com.revpay.util.DBConnection;


/**
 * DAO class for payment method database operations.
 */
public class PaymentMethodDAO {
	
    public void addPaymentMethod(PaymentMethod method) throws SQLException {
    	
    	String sql =
                "INSERT INTO payment_methods " +
                "(user_id, card_number, card_holder_name, expiry_month, expiry_year, is_default) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
    	
    	try (Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
    		
    		ps.setInt(1, method.getUserId());
            ps.setBytes(2, method.getEncryptedCardNumber());
            ps.setString(3, method.getCardHolderName());
            ps.setInt(4, method.getExpiryMonth());
            ps.setInt(5, method.getExpiryYear());
            ps.setBoolean(6, method.isDefault());
            
            ps.executeUpdate();

    	}
    }
    	
        public List<PaymentMethod> getPaymentMethodsByUser(int userId) throws SQLException {
        	
            String sql =
                    "SELECT payment_method_id, user_id, card_number, card_holder_name, " +
                    "expiry_month, expiry_year, is_default " +
                    "FROM payment_methods WHERE user_id = ?";
            
            List<PaymentMethod> methods = new ArrayList<>();
            
            try (Connection con = DBConnection.getConnection();
                    PreparedStatement ps = con.prepareStatement(sql)) {
            	
            	ps.setInt(1, userId);
                ResultSet rs = ps.executeQuery();
                
                while (rs.next()) {
                	
                    PaymentMethod method = new PaymentMethod();
                    
                    method.setPaymentMethodId(rs.getInt("payment_method_id"));
                    method.setUserId(rs.getInt("user_id"));
                    method.setEncryptedCardNumber(rs.getBytes("card_number"));
                    method.setCardHolderName(rs.getString("card_holder_name"));
                    method.setExpiryMonth(rs.getInt("expiry_month"));
                    method.setExpiryYear(rs.getInt("expiry_year"));
                    method.setDefault(rs.getBoolean("is_default"));

                    methods.add(method);
                }
            }
            return methods;
        }
        
        
        public void clearDefault(int userId) throws SQLException {

            String sql =
                "UPDATE payment_methods SET is_default = FALSE WHERE user_id = ?";

            try (Connection con = DBConnection.getConnection();
                    PreparedStatement ps = con.prepareStatement(sql)) {

                   ps.setInt(1, userId);
                   ps.executeUpdate();
            }
        }
        
        
        public void removePaymentMethod(int paymentMethodId) throws SQLException {

            String sql = "DELETE FROM payment_methods WHERE payment_method_id = ?";

            try (Connection con = DBConnection.getConnection();
                 PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setInt(1, paymentMethodId);
                ps.executeUpdate();
            }
        }
}
