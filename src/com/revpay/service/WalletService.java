package com.revpay.service;

import com.revpay.dao.WalletDAO;
import com.revpay.model.Wallet;

public class WalletService {
	
	private final WalletDAO walletDAO = new WalletDAO();
    private final NotificationService notificationService = new NotificationService();

	
	/**
     * Creates wallet for a newly registered user.
     */
	public void createWalletForUser(int userId) throws Exception {
		walletDAO.createWallet(userId);
	}
	
	
	/**
     * Returns wallet details for a user.
     */
	public Wallet getWallet(int userId) throws Exception {
		return walletDAO.getWalletByUserId(userId);
	}
	
	
	/**
	 * Adds money via user's payment method
	 */
	public void addMoneyViaPaymentMethod(int userId, double amount) throws Exception {

	    if (amount <= 0) {
	        throw new IllegalArgumentException("Amount must be greater than zero");
	    }

	    Wallet wallet = walletDAO.getWalletByUserId(userId);

	    if (wallet == null) {
	        throw new IllegalStateException("Wallet not found");
	    }

	    // payment method existence already validated before calling this
	    double newBalance = wallet.getBalance() + amount;
	    walletDAO.updateBalance(userId, newBalance);
	    
	    notificationService.notifyTransaction(
	            userId,
	            "Rs." + amount + " added to your wallet via payment method"
	        );
	}

	
	/**
     * Adds money to wallet.
     */
//	public void addMoney(int userId, double amount) throws Exception {
//		
//		if (amount <= 0) {
//			throw new IllegalArgumentException("Amount must be greater than zero");
//		}
//		
//		Wallet wallet = walletDAO.getWalletByUserId(userId);
//		
//		if (wallet == null) {
//			throw new IllegalStateException("Wallet not found");
//		}
//		
//		double newBalance = wallet.getBalance() + amount;
//		walletDAO.updateBalance(userId, newBalance);
//		
//		notificationService.notifyTransaction(
//	            userId,
//	            "Rs." + amount + " added to your wallet"
//	        );
//	}
	
	
	
	/**
     * Withdraws money from wallet.
     */
	public void withdrawMoney(int userId, double amount) throws Exception {
		
		if (amount <= 0) {
			throw new IllegalArgumentException("Amount must be greater than zero");
		}
		
		
		Wallet wallet = walletDAO.getWalletByUserId(userId);
		
		if (wallet == null) {
            throw new IllegalStateException("Wallet not found");
        }
		
		
		if (wallet.getBalance() < amount) {
            throw new IllegalArgumentException("Insufficient wallet balance");
        }
		
		
		double newBalance = wallet.getBalance() - amount;
        walletDAO.updateBalance(userId, newBalance);
        
        notificationService.notifyTransaction(
                userId,
                "Rs." + amount + " withdrawn from your wallet"
            );
		
	}
	
	
}
