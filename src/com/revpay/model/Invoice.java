package com.revpay.model;

import java.time.LocalDateTime;

public class Invoice {
	
	private int invoiceId;
    private int businessUserId;
    private int customerUserId;
    private double amount;
    private String description;
    private String status;
    private LocalDateTime createdAt;
    
    
	public int getInvoiceId() {
		return invoiceId;
	}
	public void setInvoiceId(int invoiceId) {
		this.invoiceId = invoiceId;
	}
	public int getBusinessUserId() {
		return businessUserId;
	}
	public void setBusinessUserId(int businessUserId) {
		this.businessUserId = businessUserId;
	}
	public int getCustomerUserId() {
		return customerUserId;
	}
	public void setCustomerUserId(int customerUserId) {
		this.customerUserId = customerUserId;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
