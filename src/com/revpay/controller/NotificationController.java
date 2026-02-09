package com.revpay.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revpay.model.Notification;
import com.revpay.service.NotificationService;

public class NotificationController {

    private static final Logger logger = LogManager.getLogger(NotificationController.class);

    private final NotificationService notificationService = new NotificationService();

    /**
     * View all notifications for a user
     */
    public void viewNotifications(int userId) {

        try {
            List<Notification> notifications = notificationService.getNotifications(userId);

            if (notifications.isEmpty()) {
                System.out.println("No notifications found");
                return;
            }

            for (Notification n : notifications) {
            	
            	 if (n.isRead()) {
            		 System.out.print("[READ] ");
            	 } else {
            	     System.out.print("[UNREAD] ");
            	 }

            	 System.out.print("[" + n.getType() + "] ");
            	 System.out.println(n.getMessage());
            	 
            	 if (!n.isRead()) {
                     notificationService.markAsRead(n.getNotificationId());
                 }
            }

        } catch (Exception e) {
            logger.error("Failed to fetch notifications", e);
            System.out.println("Unable to fetch notifications");
        }
    }
    
    public void sendInvoiceReminder(int customerUserId, int businessUserId) {

        try {
            String message =
                "You have a pending invoice from Business User ID: " + businessUserId;

            notificationService.notifyAlert(customerUserId, message);

            System.out.println("Invoice reminder sent successfully");

        } catch (Exception e) {
            logger.error("Failed to send invoice reminder", e);
            System.out.println("Failed to send invoice reminder");
        }
    }

}
