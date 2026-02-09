package com.revpay.service;

import java.util.List;

import com.revpay.dao.NotificationDAO;
import com.revpay.model.Notification;


/**
 * Service class responsible for
 * creating in-app notifications.
 */
public class NotificationService {
	
	public final NotificationDAO notificationDAO = new NotificationDAO();
	
	
    // ================= CREATE NOTIFICATIONS =================

	public void notifyTransaction(int userId, String message) throws Exception {
		
		notificationDAO.createNotification(userId, message, "TRANSACTION");
	}
	
	
	public void notifyRequest(int userId, String message) throws Exception {

		notificationDAO.createNotification(userId, message, "REQUEST");
	}
	
	
	public void notifyAlert(int userId, String message) throws Exception {

		notificationDAO.createNotification(userId, message, "ALERT");
	}
	
    // ================= VIEW NOTIFICATIONS =================

    public List<Notification> getNotifications(int userId) throws Exception {
        return notificationDAO.getNotificationsByUser(userId);
    }
    
    // ================= UPDATE NOTIFICATION =================

    public void markAsRead(int notificationId) throws Exception {
        notificationDAO.markAsRead(notificationId);
    }

}
