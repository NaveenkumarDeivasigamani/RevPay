package com.revpay.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revpay.model.Notification;
import com.revpay.util.DBConnection;

/**
 * DAO class for notification database operations.
 */
public class NotificationDAO {
	
	public void createNotification(int userId, String message, String type) throws SQLException {
		
		String sql = "INSERT INTO notifications (user_id, message, type) VALUES (?, ?, ?)";
		
		try (Connection con = DBConnection.getConnection();
	             PreparedStatement ps = con.prepareStatement(sql)) {
			
			ps.setInt(1, userId);
			ps.setString(2, message);
			ps.setString(3, type);
			
			ps.executeUpdate();
		}
	}
	
	 public List<Notification> getNotificationsByUser(int userId) throws SQLException {
		 
		 String sql =
		            "SELECT notification_id, user_id, message, type, is_read " +
		            "FROM notifications WHERE user_id = ? ORDER BY created_at DESC";
		 
		 List<Notification> notifications = new ArrayList<>();
		 
		 try (Connection con = DBConnection.getConnection();
	             PreparedStatement ps = con.prepareStatement(sql)) {
			 
			 ps.setInt(1, userId);
	         ResultSet rs = ps.executeQuery();
	         
	         while (rs.next()) {
	        	 
	        	 Notification notification = new Notification();
	        	 
	        	 notification.setNotificationId(rs.getInt("notification_id"));
	             notification.setUserId(rs.getInt("user_id"));
	             notification.setMessage(rs.getString("message"));
	             notification.setType(rs.getString("type"));
	             notification.setRead(rs.getBoolean("is_read"));
	             
	             notifications.add(notification);
	         }
	     }
		 return notifications;
	 }
	 
	 
	 public void markAsRead(int notificationId) throws SQLException {
		 
		 String sql = "UPDATE notifications SET is_read = TRUE WHERE notification_id = ?";
		 
		 try (Connection con = DBConnection.getConnection();
	             PreparedStatement ps = con.prepareStatement(sql)) {
			 
			 
			 ps.setInt(1, notificationId);
	            ps.executeUpdate();
		 }

	 }


}
