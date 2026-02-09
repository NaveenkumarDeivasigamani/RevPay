package com.revpay.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revpay.service.TransactionService;

public class TransactionController {
	
	private static final Logger logger = LogManager.getLogger(TransactionController.class);
	
	private final TransactionService transactionService = new TransactionService();
	
	public void transferMoney(int senderUserId, int receiverUserId, double amount, String enteredPin) {
		
		try {
			logger.info("Initializing transfer: sender={}, receiver={}, amount={}", senderUserId, receiverUserId, amount);
		
			transactionService.transfer(senderUserId, receiverUserId, amount, enteredPin);
			
			System.out.println("Money transfer successful");
		
		} catch (Exception e) {
			logger.error("Money transfer failed", e);
			System.out.println("Transfer failed: " + e.getMessage());
		}
	}
	
	public void viewTransactions(int userId) {

	    try {
	        transactionService.viewTransactions(userId);
	    } catch (Exception e) {
	        System.out.println("Failed to fetch transactions");
	    }
	}


}
