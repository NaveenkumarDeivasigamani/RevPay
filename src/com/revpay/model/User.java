package com.revpay.model;

/**
 * User POJO class
 * This class holds user-related data
 * and is used to transfer data between layers.
 */

public class User {
	
	private int userId;
	private String fullName;
	private String email;
	private String phone;
	private String rawPassword;   		 //plain password from user
	private String rawTransactionPin;   //plain PIN from user
	private String accountType;
	private String passwordHash;
	private String securityQuestion;
	private String securityAnswer;   // RAW answer (never stored)

	
	// Getters and Setters
	
	
	public int getUserId() {
	    return userId;
	}

	public String getSecurityQuestion() {
		return securityQuestion;
	}

	public void setSecurityQuestion(String securityQuestion) {
		this.securityQuestion = securityQuestion;
	}

	public String getSecurityAnswer() {
		return securityAnswer;
	}

	public void setSecurityAnswer(String securityAnswer) {
		this.securityAnswer = securityAnswer;
	}

	public void setUserId(int userId) {
	    this.userId = userId;
	}
	
	public String getFullName() {
		return fullName;
	}
	
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRawPassword() {
		return rawPassword;
	}

	public void setRawPassword(String rawPassword) {
		this.rawPassword = rawPassword;
	}

	public String getRawTransactionPin() {
		return rawTransactionPin;
	}

	public void setTransactionPin(String rawTransactionPin) {
		this.rawTransactionPin = rawTransactionPin;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	
	public String getPasswordHash() {
	    return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
	    this.passwordHash = passwordHash;
	}


}
