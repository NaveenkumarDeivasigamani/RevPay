package com.revpay.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revpay.model.MoneyRequest;
import com.revpay.util.DBConnection;

public class MoneyRequestDAO {
	
	public void createRequest(int requesterUserId, int requestedUserId, double amount) throws SQLException {
		
		String sql = "INSERT INTO money_requests (requester_user_id, requested_user_id, amount, status) VALUES (?, ?, ?, 'PENDING')";
		
		try (Connection con = DBConnection.getConnection();
				PreparedStatement ps = con.prepareStatement(sql)) {
			
			ps.setInt(1, requesterUserId);
			ps.setInt(2, requestedUserId);
			ps.setDouble(3, amount);
			
			ps.executeUpdate();
		}
	}
		
		public List<MoneyRequest> getRequestsForUser(int userId) throws SQLException {
			
			String sql ="SELECT request_id, requester_user_id, requested_user_id, amount, status FROM money_requests WHERE requested_user_id = ?";
			
			List<MoneyRequest> requests = new ArrayList<>();
			
			try (Connection con = DBConnection.getConnection();
					PreparedStatement ps = con.prepareStatement(sql)) {
				
				ps.setInt(1, userId);
				ResultSet rs = ps.executeQuery();
				
				while (rs.next()) {
					
					MoneyRequest request = new MoneyRequest();
					
	                request.setRequestId(rs.getInt("request_id"));
	                request.setRequesterUserId(rs.getInt("requester_user_id"));
	                request.setRequestedUserId(rs.getInt("requested_user_id"));
	                request.setAmount(rs.getDouble("amount"));
	                request.setStatus(rs.getString("status"));
	                
	                requests.add(request);
				}
				
			}
			return requests;
			
		}
		
		public MoneyRequest getRequestById(int requestId) throws SQLException {

		    String sql = "SELECT request_id, requester_user_id, requested_user_id, amount, status " +
		                 "FROM money_requests WHERE request_id = ?";

		    try (Connection con = DBConnection.getConnection();
		         PreparedStatement ps = con.prepareStatement(sql)) {

		        ps.setInt(1, requestId);
		        ResultSet rs = ps.executeQuery();

		        if (rs.next()) {
		            MoneyRequest request = new MoneyRequest();
		            request.setRequestId(rs.getInt("request_id"));
		            request.setRequesterUserId(rs.getInt("requester_user_id"));
		            request.setRequestedUserId(rs.getInt("requested_user_id"));
		            request.setAmount(rs.getDouble("amount"));
		            request.setStatus(rs.getString("status"));
		            return request;
		        }
		    }
		    return null;
		}

		
		public void updateRequestStatus(int requestId, String newStatus) throws SQLException {
			
			String sql = "UPDATE money_requests SET status = ? WHERE request_id = ?";
			
			try (Connection con = DBConnection.getConnection();
					PreparedStatement ps = con.prepareStatement(sql)) {
				
				ps.setString(1, newStatus);
	            ps.setInt(2, requestId);
				
				ps.executeUpdate();
			}
		}
}
