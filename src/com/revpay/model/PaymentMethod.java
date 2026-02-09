package com.revpay.model;

public class PaymentMethod {
	
	private int paymentMethodId;
    private int userId;
    private byte[] encryptedCardNumber;
    private String cardHolderName;
    private int expiryMonth;
    private int expiryYear;
    private boolean isDefault;
	
    
    public int getPaymentMethodId() {
		return paymentMethodId;
	}
	public void setPaymentMethodId(int paymentMethodId) {
		this.paymentMethodId = paymentMethodId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public byte[] getEncryptedCardNumber() {
		return encryptedCardNumber;
	}
	public void setEncryptedCardNumber(byte[] encryptedCardNumber) {
		this.encryptedCardNumber = encryptedCardNumber;
	}
	public String getCardHolderName() {
		return cardHolderName;
	}
	public void setCardHolderName(String cardHolderName) {
		this.cardHolderName = cardHolderName;
	}
	public int getExpiryMonth() {
		return expiryMonth;
	}
	public void setExpiryMonth(int expiryMonth) {
		this.expiryMonth = expiryMonth;
	}
	public int getExpiryYear() {
		return expiryYear;
	}
	public void setExpiryYear(int expiryYear) {
		this.expiryYear = expiryYear;
	}
	public boolean isDefault() {
		return isDefault;
	}
	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}
    
    
    

}
