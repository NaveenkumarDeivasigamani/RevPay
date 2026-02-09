package com.revpay.model;

import java.time.LocalDateTime;

public class Loan {
	
	private int loanId;
    private int businessUserId;
    private double amount;
    private String purpose;
    private String status;
    private LocalDateTime createdAt;
    
    
	public int getLoanId() {
		return loanId;
	}
	public void setLoanId(int loanId) {
		this.loanId = loanId;
	}
	public int getBusinessUserId() {
		return businessUserId;
	}
	public void setBusinessUserId(int businessUserId) {
		this.businessUserId = businessUserId;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
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
