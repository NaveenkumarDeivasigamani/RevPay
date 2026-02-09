package com.revpay.model;

import java.time.LocalDateTime;

public class Transaction {
	
	private int transactionId;
    private int senderUserId;
    private int receiverUserId;
    private double amount;
    private String transactionType; 
    private String status;
    private LocalDateTime createdAt;
    
    
	public int getTransactionId() {
		return transactionId;
	}
	
	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}
	
	public int getSenderUserId() {
		return senderUserId;
	}
	public void setSenderUserId(int senderUserId) {
		this.senderUserId = senderUserId;
	}
	public int getReceiverUserId() {
		return receiverUserId;
	}
	public void setReceiverUserId(int receiverUserId) {
		this.receiverUserId = receiverUserId;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
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
