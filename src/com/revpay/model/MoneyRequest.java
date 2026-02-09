package com.revpay.model;

import java.time.LocalDateTime;

public class MoneyRequest {
	
	private int requestId;
    private int requesterUserId;
    private int requestedUserId;
    private double amount;
    private String status;
    private LocalDateTime createdAt;
    
    
	public int getRequestId() {
		return requestId;
	}
	public void setRequestId(int requestId) {
		this.requestId = requestId;
	}
	public int getRequesterUserId() {
		return requesterUserId;
	}
	public void setRequesterUserId(int requesterUserId) {
		this.requesterUserId = requesterUserId;
	}
	public int getRequestedUserId() {
		return requestedUserId;
	}
	public void setRequestedUserId(int requestedUserId) {
		this.requestedUserId = requestedUserId;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
    
    

}
