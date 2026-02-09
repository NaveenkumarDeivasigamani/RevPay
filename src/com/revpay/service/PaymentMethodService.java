package com.revpay.service;

import java.time.LocalDate;


import com.revpay.dao.PaymentMethodDAO;
import com.revpay.model.PaymentMethod;
import com.revpay.util.EncryptionUtil;

/**
 * Service class for payment method
 * business logic and security validations.
 */
public class PaymentMethodService {
	
	private final PaymentMethodDAO paymentMethodDAO = new PaymentMethodDAO();
	
	public void addPaymentMethod(int userId, String cardNumber, String cardHolderName, int expiryMonth, int expiryYear, boolean makeDefault) throws Exception {
		
		if (cardNumber == null || cardNumber.length() != 16) {
            throw new IllegalArgumentException("Invalid card number");
        }
		
		if (expiryMonth < 1 || expiryMonth > 12) {
            throw new IllegalArgumentException("Invalid expiry month");
        }
		
		int currentYear = LocalDate.now().getYear();
		int currentMonth = LocalDate.now().getMonthValue();
		
		if (expiryYear < currentYear) {
            throw new IllegalArgumentException("Invalid expiry year. Card has already expired");
        }
		
		
		if (expiryYear == currentYear && expiryMonth < currentMonth) {
            throw new IllegalArgumentException("Invalid expiry  month. Card has already expired");
        }
		
		byte[] encryptedCardNumber = EncryptionUtil.encrypt(cardNumber);
		
		
		if (makeDefault) {
			paymentMethodDAO.clearDefault(userId);
		}
		
		PaymentMethod method = new PaymentMethod();
		
        method.setUserId(userId);
        method.setEncryptedCardNumber(encryptedCardNumber);
        method.setCardHolderName(cardHolderName);
        method.setExpiryMonth(expiryMonth);
        method.setExpiryYear(expiryYear);
        method.setDefault(makeDefault);
        
        paymentMethodDAO.addPaymentMethod(method);
		 
		 
	}
	
	public void removePaymentMethod(int paymentMethodId) throws Exception {
	    
		paymentMethodDAO.removePaymentMethod(paymentMethodId);
	
	}

}
