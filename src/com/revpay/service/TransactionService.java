package com.revpay.service;

import com.revpay.dao.TransactionDAO;
import com.revpay.dao.WalletDAO;
import com.revpay.model.Wallet;
import com.revpay.security.PasswordUtil;

public class TransactionService {
	
	private final TransactionDAO transactionDAO = new TransactionDAO();
	private final WalletDAO walletDAO = new WalletDAO();
	private final NotificationService notificationService = new NotificationService();

	
	
	/**
	 * user-initiated transfers
	 */
	public void transfer(int senderUserId, int receiverUserId, double amount, String enteredPin) throws Exception {
		
		if (senderUserId == receiverUserId) {
			throw new IllegalArgumentException("Sender and receiver cannot be same");
		}
		
		if (amount <= 0) {
			throw new IllegalArgumentException("Amount must be greater than zero");
		}
		
		String actualPinHash = walletDAO.getTransactionPinHash(senderUserId);

		if (!PasswordUtil.verify(enteredPin, actualPinHash)) {
		    throw new IllegalArgumentException("Invalid transaction PIN");
		}
		
		
		Wallet senderWallet = walletDAO.getWalletByUserId(senderUserId);
		
		if (senderWallet == null) {
            throw new IllegalStateException("Sender wallet not found");
        }
		
		if (senderWallet.getBalance() < amount) {
            throw new IllegalArgumentException("Insufficient wallet balance");
        }
		
		transactionDAO.transferMoney(senderUserId, receiverUserId, amount);
		
		notificationService.notifyTransaction(
	            senderUserId,
	            "You sent Rs." + amount + " to User ID " + receiverUserId
	        );
		
		notificationService.notifyTransaction(
	            receiverUserId,
	            "You received Rs." + amount + " from User ID " + senderUserId
	        );
	}
	
	
	/**
	 * System-driven transfer (used for invoice payment, refunds, etc.)
	 */
	public void systemTransfer(int senderUserId, int receiverUserId, double amount) throws Exception {

	    if (senderUserId == receiverUserId) {
	        throw new IllegalArgumentException("Sender and receiver cannot be same");
	    }

	    if (amount <= 0) {
	        throw new IllegalArgumentException("Amount must be greater than zero");
	    }

	    Wallet senderWallet = walletDAO.getWalletByUserId(senderUserId);

	    if (senderWallet == null) {
	        throw new IllegalStateException("Sender wallet not found");
	    }

	    if (senderWallet.getBalance() < amount) {
	        throw new IllegalArgumentException("Insufficient wallet balance");
	    }

	    transactionDAO.transferMoney(senderUserId, receiverUserId, amount);
	    
	    notificationService.notifyTransaction(
	            receiverUserId,
	            "Invoice payment of Rs." + amount + " received"
	        );
	}
	
	
	/**
     * View transactions
     */
	public void viewTransactions(int userId) throws Exception {
	    transactionDAO.getTransactionsByUser(userId)
	        .forEach(t -> System.out.println(
	            "Txn ID: " + t.getTransactionId() +
	            ", From: " + t.getSenderUserId() +
	            ", To: " + t.getReceiverUserId() +
	            ", Amount: Rs." + t.getAmount() +
	            ", Status: " + t.getStatus()
	        ));
	}



}
