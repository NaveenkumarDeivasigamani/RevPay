package com.revpay.model;


/**
 * Notification POJO
 * Represents an in-app notification.
 */
public class Notification {
	
	private int notificationId;
    private int userId;
    private String message;
    private String type;     // TRANSACTION, REQUEST, ALERT
    private boolean isRead;
    
    
    
	public int getNotificationId() {
		return notificationId;
	}
	public void setNotificationId(int notificationId) {
		this.notificationId = notificationId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public boolean isRead() {
		return isRead;
	}
	public void setRead(boolean isRead) {
		this.isRead = isRead;
	}
    
    

}
