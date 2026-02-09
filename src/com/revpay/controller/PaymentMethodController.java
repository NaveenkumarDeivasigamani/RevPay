package com.revpay.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revpay.service.PaymentMethodService;

public class PaymentMethodController {
	
	private static final Logger logger = LogManager.getLogger(PaymentMethodController.class);
	
	private final PaymentMethodService paymentMethodService = new PaymentMethodService();
	
	
	public void addPaymentMethod(int userId, String cardNumber, String cardHolderName, int expiryMonth, int expiryYear, boolean makeDefault) {
		
		try {
			
			logger.info("Adding payment method for userId={}, default={}", userId, makeDefault);
			
			paymentMethodService.addPaymentMethod(userId, cardNumber, cardHolderName, expiryMonth, expiryYear, makeDefault);
		
            System.out.println("Payment method added successfully");

		} catch (Exception e) {
			
			logger.error("Failed to add payment method", e);
            System.out.println("Failed to add payment method: " + e.getMessage());

		}
	}
	
	public void removePaymentMethod(int paymentMethodId) {

	    try {
	        logger.info("Removing payment method with id={}",paymentMethodId);
	        
	        paymentMethodService.removePaymentMethod(paymentMethodId);

	        System.out.println("Payment method removed successfully");

	    } catch (Exception e) {
	    	
	    	logger.error("Failed to remove payment method", e);
	    	
	        System.out.println("Failed to remove payment method");
	    }
	}
}
