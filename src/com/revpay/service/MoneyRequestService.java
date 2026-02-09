package com.revpay.service;

import java.util.List;

import com.revpay.dao.MoneyRequestDAO;
import com.revpay.model.MoneyRequest;

/**
 * Service class for money request
 * business logic.
 */

public class MoneyRequestService {
	
	private final MoneyRequestDAO requestDAO = new MoneyRequestDAO();
    private final TransactionService transactionService = new TransactionService();
    private final NotificationService notificationService = new NotificationService();

    /**
     * Create a money request
     */
    public void createRequest(int requesterUserId, int requestedUserId, double amount) throws Exception {

        if (requesterUserId == requestedUserId) {
            throw new IllegalArgumentException("Cannot request money from self");
        }

        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }

        requestDAO.createRequest(requesterUserId, requestedUserId, amount);

        notificationService.notifyRequest(
                requestedUserId,
                "Money request of Rs." + amount + " received from User ID " + requesterUserId
        );
    }

    /**
     * View incoming requests
     */
    public List<MoneyRequest> getIncomingRequests(int userId) throws Exception {
        return requestDAO.getRequestsForUser(userId);
    }
    
    
    /**
     * Accept money request (SECURE)
     */
    public void acceptRequest(int requestId, int currentUserId, String enteredPin) throws Exception {

        MoneyRequest request = requestDAO.getRequestById(requestId);

        if (request == null) {
            throw new IllegalArgumentException("Money request not found");
        }
        
        if (!"PENDING".equals(request.getStatus())) {
            throw new IllegalStateException("Request already processed");
        }

        if (request.getRequestedUserId() != currentUserId) {
            throw new IllegalArgumentException("Unauthorized request access");
        }
        
     // Transfer money (PIN verified inside TransactionService)
        transactionService.transfer(
                request.getRequesterUserId(),
                request.getRequestedUserId(),
                request.getAmount(),
                enteredPin
        );

        requestDAO.updateRequestStatus(requestId, "ACCEPTED");

        notificationService.notifyTransaction(
        		request.getRequesterUserId(),
                "Your money request was accepted. Rs." + request.getAmount() + " received"
        );
    }
    
    /**
     * Decline money request
     */
    public void declineRequest(int requestId) throws Exception {

        MoneyRequest request = requestDAO.getRequestById(requestId);

        if (request == null) {
            throw new IllegalArgumentException("Money request not found");
        }

        requestDAO.updateRequestStatus(requestId, "DECLINED");

        notificationService.notifyRequest(
                request.getRequesterUserId(),
                "Your money request was declined"
        );
    }
}