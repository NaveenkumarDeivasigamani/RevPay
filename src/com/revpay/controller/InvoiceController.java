package com.revpay.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revpay.service.InvoiceService;


/**
 * Controller for invoice-related operations.
 */
public class InvoiceController {
	
	private static final Logger logger = LogManager.getLogger(InvoiceController.class);

    private final InvoiceService invoiceService = new InvoiceService();
    
    /**
     * Creates a new invoice.
     */
    public void createInvoice(int businessUserId, int customerUserId, double amount, String description) {
    	
    	try {
    	
            logger.info("Creating invoice: businessUserId={}, customerUserId={}, amount={}",businessUserId, customerUserId, amount);

            int invoiceId = invoiceService.createInvoice(businessUserId, customerUserId, amount, description);
            
            
            System.out.println("Invoice created successfully");
            System.out.println("Invoice ID: " + invoiceId);

            logger.info("Invoice created successfully with invoiceId={}", invoiceId);

        } catch (IllegalArgumentException e) {
        	
        	// Business validation errors
            logger.warn("Invoice creation validation failed: {}", e.getMessage());
            System.out.println("Validation Failed" + e.getMessage());
        
        } catch (Exception e) {
            
        	// Unexpected system-level errors
            logger.error("Invoice creation failed", e);
            System.out.println("Invoice creation failed. Please try again.");
        
        }
    }
    
    
    /**
     * View all invoices for a business user.
     */
    public void viewInvoices(int businessUserId) {
        try {
            logger.info("Fetching invoices for businessUserId={}", businessUserId);
            invoiceService.viewInvoices(businessUserId);

        } catch (Exception e) {
            logger.error("Failed to fetch invoices", e);
            System.out.println("Failed to fetch invoices: " + e.getMessage());
        }
    }

    /**
     * Pay an invoice (system-driven payment).
     */
    public void payInvoice(int invoiceId) {
        try {
            logger.info("Paying invoice invoiceId={}", invoiceId);

            invoiceService.payInvoice(invoiceId);

            System.out.println("Invoice paid successfully");
            logger.info("Invoice payment successful for invoiceId={}", invoiceId);

        } catch (IllegalArgumentException | IllegalStateException e) {
            logger.warn("Invoice payment failed: {}", e.getMessage());
            System.out.println("Invoice payment failed: " + e.getMessage());

        } catch (Exception e) {
            logger.error("Invoice payment failed", e);
            System.out.println("Invoice payment failed. Please try again.");
        }
    }


}
