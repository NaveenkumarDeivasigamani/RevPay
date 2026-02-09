package com.revpay.service;

import java.util.List;

import com.revpay.dao.InvoiceDAO;
import com.revpay.model.Invoice;

/**
 * Service layer for invoice operations.
 */
public class InvoiceService {
	
	private final InvoiceDAO invoiceDAO = new InvoiceDAO();
    private final TransactionService transactionService = new TransactionService();
    private final NotificationService notificationService = new NotificationService();

    
	/**
     * Creates a new invoice after validation.
     */
    public int createInvoice(int businessUserId, int customerUserId, double amount, String description) throws Exception {
    	
    	
    	if (amount <= 0) {
            throw new IllegalArgumentException("Invoice amount must be greater than zero");
        }

        if (businessUserId <= 0 || customerUserId <= 0) {
            throw new IllegalArgumentException("Invalid user information");
        }
        
        if (businessUserId == customerUserId) {
            throw new IllegalArgumentException("Business and customer cannot be same user");
        }
        
        Invoice invoice = new Invoice();
        
        invoice.setBusinessUserId(businessUserId);
        invoice.setCustomerUserId(customerUserId);
        invoice.setAmount(amount);
        invoice.setDescription(description);

        return invoiceDAO.createInvoice(invoice);
    }
    

    public void viewInvoices(int businessUserId) throws Exception {

        List<Invoice> invoices = invoiceDAO.getInvoicesByBusinessUser(businessUserId);

        if (invoices.isEmpty()) {
            System.out.println("No invoices found");
            return;
        }

        for (Invoice invoice : invoices) {
            System.out.println(
                "Invoice ID: " + invoice.getInvoiceId() +
                ", Customer ID: " + invoice.getCustomerUserId() +
                ", Amount: Rs." + invoice.getAmount() +
                ", Status: " + invoice.getStatus()
            );
        }
    }

    public void payInvoice(int invoiceId) throws Exception {

        Invoice invoice = invoiceDAO.getInvoiceById(invoiceId);

        if (invoice == null) {
            throw new IllegalArgumentException("Invoice not found");
        }

        if (!"UNPAID".equals(invoice.getStatus())) {
            throw new IllegalStateException("Invoice already paid");
        }

      // CUSTOMER to BUSINESS (system transfer, no PIN)
        transactionService.systemTransfer(
            invoice.getCustomerUserId(),
            invoice.getBusinessUserId(),
            invoice.getAmount()
        );

        invoiceDAO.updateInvoiceStatus(invoiceId, "PAID");
        
        notificationService.notifyTransaction(
                invoice.getBusinessUserId(),
                "Invoice payment of Rs." + invoice.getAmount() + " received"
            );
    }    
    

}
