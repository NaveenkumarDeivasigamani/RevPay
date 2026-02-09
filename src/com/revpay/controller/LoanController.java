package com.revpay.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revpay.service.LoanService;

/**
 * Controller for loan-related operations.
 */
public class LoanController {
	
	private static final Logger logger =  LogManager.getLogger(LoanController.class);

    private final LoanService loanService = new LoanService();

    
    /**
     * Applies for a business loan.
     */
    public void applyLoan(int businessUserId, double amount, String purpose) {

		try {
			
			logger.info("Loan application started: userId={}, amount={}", businessUserId, amount);
			
			int loanId = loanService.applyLoan(businessUserId, amount, purpose);
			
			System.out.println("Loan application submitted successfully");
            System.out.println("Loan ID: " + loanId);

            logger.info("Loan application successful: loanId={}", loanId);
		
		} catch (IllegalArgumentException e) {
			
			logger.warn("Loan application validation failed: {}", e.getMessage());
            System.out.println("Validation Failed" + e.getMessage());

        } catch (Exception e) {
        	
            logger.error("Loan application failed", e);
            System.out.println("Loan application failed. Please try again.");
        
        }
    }
    
    
    /**
     * View all loans for a business user.
     */
    public void viewLoans(int businessUserId) {

        try {
            logger.info("Fetching loans for businessUserId={}", businessUserId);

            loanService.viewLoans(businessUserId);

            logger.info("Loans fetched successfully for businessUserId={}", businessUserId);

        } catch (IllegalArgumentException e) {
            // Business validation issue
            logger.warn("Loan fetch validation failed for userId={}: {}", 
                        businessUserId, e.getMessage());
            System.out.println(e.getMessage());

        } catch (Exception e) {
            // System / DB error
            logger.error("Failed to fetch loans for userId={}", businessUserId, e);
            System.out.println("Failed to fetch loans. Please try again.");
        }
    }
    
    /**
     * Repay an approved loan.
     */
    public void repayLoan(int loanId) {

        try {
            logger.info("Processing loan repayment for loanId={}", loanId);

            loanService.repayLoan(loanId);

            System.out.println("Loan repaid successfully");
            logger.info("Loan repayment successful for loanId={}", loanId);

        } catch (IllegalArgumentException | IllegalStateException e) {
            logger.warn("Loan repayment failed: {}", e.getMessage());
            System.out.println("Loan repayment failed: " + e.getMessage());

        } catch (Exception e) {
            logger.error("Loan repayment error", e);
            System.out.println("Loan repayment failed. Please try again.");
        }
    }
    
    /**
     * Approve a pending loan (future admin / system use).
     */
    public void approveLoan(int loanId) {

        try {
            logger.info("Approving loanId={}", loanId);

            loanService.approveLoan(loanId);
            
            System.out.println("Loan approved and wallet credited");
            logger.info("Loan approved successfully: loanId={}", loanId);

        } catch (IllegalArgumentException | IllegalStateException e) {
            logger.warn("Loan approval failed: {}", e.getMessage());
            System.out.println("Loan approval failed: " + e.getMessage());

        } catch (Exception e) {
            logger.error("Loan approval error", e);
            System.out.println("Loan approval failed. Please try again.");
        }
    }

}
		
			