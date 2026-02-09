package com.revpay.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revpay.model.Wallet;
import com.revpay.service.WalletService;

/**
 * Controller class for wallet-related
 * user interactions.
 */
public class WalletController {
	
	private static final Logger logger = 
			LogManager.getLogger(WalletController.class);
	
	private final WalletService walletService = new WalletService();
	
	
	/**
     * Displays wallet balance.
     */
	public void viewBalance(int userId) {
		
		try {
			logger.info("Fetching wallet balance for userId {}", userId);
			
			Wallet wallet = walletService.getWallet(userId);
			
			if (wallet != null) {
				System.out.println("Current Balance: Rs." + wallet.getBalance());
			} else {
				System.out.println("Wallet not found");
			}
		} catch (Exception e) {
			logger.error("Failed to fetch wallet balance", e);
			System.out.println("Error fetching wallet balance");
		}
	}
	
	
	/**
	 * Adds money using user's default payment method
	 * (Used by personal users)
	 */
	public void addMoneyViaPaymentMethod(int userId, double amount) {

	    try {
	        logger.info("Adding money via payment method. userId={}, amount={}", userId, amount);

	        walletService.addMoneyViaPaymentMethod(userId, amount);

	        System.out.println("Money added successfully via payment method");

	    } catch (Exception e) {
	        logger.error("Failed to add money via payment method", e);
	        System.out.println("Add money failed: " + e.getMessage());
	    }
	}
	
	
	 /**
     * Adds money to wallet.
     */
//	public void addMoney(int userId, double amount) {
//		try {
//			logger.info("Adding money to wallet. userId={}, amount={}", userId, amount);
//			
//			walletService.addMoney(userId, amount);
//			
//			System.out.println("Money added successfully");
//		} catch (Exception e) {
//			logger.error("Failed to add money", e);
//            System.out.println("Add money failed: " + e.getMessage());
//
//		}
//	}
	
	
	
	 /**
     * Withdraws money from wallet.
     */
	public void withdrawMoney(int userId, double amount) {
		
		try {
            logger.info("Withdrawing money from wallet. userId={}, amount={}", userId, amount);
            
            walletService.withdrawMoney(userId, amount);
            
            System.out.println("Money withdrawn successfully");
		} catch (Exception e) {
            logger.error("Failed to withdraw money", e);
            
            System.out.println("Withdraw failed: " + e.getMessage());

		}
	}
	
	public void viewWallet(int userId) {
	    try {
	        Wallet wallet = walletService.getWallet(userId);
	        System.out.println("Wallet Balance: " + wallet.getBalance());
	        System.out.println("Status: " + wallet.getStatus());
	    } catch (Exception e) {
	        System.out.println("Unable to fetch wallet details");
	    }
	}


}
