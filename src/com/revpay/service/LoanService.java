package com.revpay.service;

import java.util.List;

import com.revpay.dao.WalletDAO;
import com.revpay.model.Wallet;
import com.revpay.dao.LoanDAO;
import com.revpay.dao.UserDAO;
import com.revpay.model.Loan;
import com.revpay.model.User;

/**
 * Service layer for loan-related business logic.
 */
public class LoanService {
	
	private final LoanDAO loanDAO = new LoanDAO();
    private final UserDAO userDAO = new UserDAO();
    private final WalletDAO walletDAO = new WalletDAO();
    private final NotificationService notificationService = new NotificationService();



    /**
     * Applies for a loan (BUSINESS users only).
     */
    public int applyLoan(int businessUserId, double amount, String purpose) throws Exception {
    	
    	
    	if (amount <= 0) {
            throw new IllegalArgumentException("Loan amount must be greater than zero");
        }
    	
    	
    	if (purpose == null || purpose.isBlank()) {
            throw new IllegalArgumentException("Loan purpose is required");
        }
    	
    	
    	// Validate user exists & is BUSINESS
    	User user = userDAO.getUserById(businessUserId);
    	
    	if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
    	
    	if (!"BUSINESS".equals(user.getAccountType())) {
            throw new IllegalArgumentException("Only business users can apply for loans");
        }
    	
    	
    	// Create Loan object
        Loan loan = new Loan();
        
        loan.setBusinessUserId(businessUserId);
        loan.setAmount(amount);
        loan.setPurpose(purpose);
        
        int loanId =  loanDAO.applyLoan(loan);
        
        // AUTO CREDIT WALLET
        Wallet wallet = walletDAO.getWalletByUserId(businessUserId);
        double newBalance = wallet.getBalance() + amount;
        walletDAO.updateBalance(businessUserId, newBalance);

        return loanId;
    
    }
    
    /**
     * View all loans for a business user.
     */
    public void viewLoans(int businessUserId) throws Exception {

        List<Loan> loans = loanDAO.getLoansByBusinessUser(businessUserId);

        if (loans.isEmpty()) {
            System.out.println("No loans found");
            return;
        }
        
        for (Loan loan : loans) {
            System.out.println(
                "Loan ID: " + loan.getLoanId() +
                ", Amount: Rs." + loan.getAmount() +
                ", Purpose: " + loan.getPurpose() +
                ", Status: " + loan.getStatus()
            );
        }
    }
    
    
    /**
     * Approve a pending loan and credit wallet.
     */
    public void approveLoan(int loanId) throws Exception {

        Loan loan = loanDAO.getLoanById(loanId);

        if (loan == null) {
            throw new IllegalArgumentException("Loan not found");
        }

        if (!"PENDING".equals(loan.getStatus())) {
            throw new IllegalStateException("Only pending loans can be approved");
        }
        
        Wallet wallet = walletDAO.getWalletByUserId(loan.getBusinessUserId());

        if (wallet == null) {
            throw new IllegalStateException("Business wallet not found");
        }

        walletDAO.updateBalance(
            loan.getBusinessUserId(),
            wallet.getBalance() + loan.getAmount()
        );

        loanDAO.updateLoanStatus(loanId, "APPROVED");
        
        notificationService.notifyAlert(
                loan.getBusinessUserId(),
                "Loan approved for Rs." + loan.getAmount()
            );
    }
    
    
    /**
     * Repay an approved loan.
     */
    public void repayLoan(int loanId) throws Exception {

        Loan loan = loanDAO.getLoanById(loanId);

        if (loan == null) {
            throw new IllegalArgumentException("Loan not found");
        }

        if (!"APPROVED".equals(loan.getStatus())) {
            throw new IllegalStateException("Only approved loans can be repaid");
        }

        Wallet wallet = walletDAO.getWalletByUserId(loan.getBusinessUserId());

        if (wallet == null) {
            throw new IllegalStateException("Business wallet not found");
        }

        if (wallet.getBalance() < loan.getAmount()) {
            throw new IllegalArgumentException("Insufficient wallet balance");
        }

        walletDAO.updateBalance(
            loan.getBusinessUserId(),
            wallet.getBalance() - loan.getAmount()
        );

        loanDAO.updateLoanStatus(loanId, "REPAID");
    }

}


