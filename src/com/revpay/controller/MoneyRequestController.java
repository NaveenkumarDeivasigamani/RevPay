package com.revpay.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revpay.model.MoneyRequest;
import com.revpay.service.MoneyRequestService;

/**
 * Controller class for money request operations.
 */
public class MoneyRequestController {
	
	private static final Logger logger = LogManager.getLogger(MoneyRequestController.class);
	
	private final MoneyRequestService requestService = new MoneyRequestService();
	
	/**
     * Creates a money request.
     */
	public void createRequest(int requesterUserId, int requestedUserId, double amount) {

        try {
            logger.info("Creating money request: requester={}, requested={}, amount={}",
                    requesterUserId, requestedUserId, amount);

            requestService.createRequest(requesterUserId, requestedUserId, amount);

            System.out.println("Money request created successfully");

        } catch (Exception e) {
            logger.error("Failed to create money request", e);
            System.out.println("Request creation failed: " + e.getMessage());
        }
	}
	
	
	/**
     * Views incoming money requests.
     */
    public void viewIncomingRequests(int userId) {

        try {
            logger.info("Fetching incoming requests for userId={}", userId);

            List<MoneyRequest> requests = requestService.getIncomingRequests(userId);

            if (requests.isEmpty()) {
                System.out.println("No incoming requests");
                return;
            }

            for (MoneyRequest request : requests) {
                System.out.println(
                        "Request ID: " + request.getRequestId() +
                        ", From User: " + request.getRequesterUserId() +
                        ", Amount: Rs." + request.getAmount() +
                        ", Status: " + request.getStatus()
                );
            }
        } catch (Exception e) {
            logger.error("Failed to fetch requests", e);
            System.out.println("Failed to fetch requests");
        }
    }
	
	 
    /**
     * Accepts a money request.
     */
    public void acceptRequest(int requestId, int receiverUserId, String transactionPin) {

        try {
            logger.info("Accepting money request requestId={}", requestId);

            requestService.acceptRequest(requestId, receiverUserId, transactionPin);

            System.out.println("Money request accepted and amount transferred");

        } catch (Exception e) {
            logger.error("Failed to accept request", e.getMessage());
            System.out.println("Accept request failed: " + e.getMessage());
        }
    }
	
	
    /**
     * Declines a money request.
     */
    public void declineRequest(int requestId) {

        try {
            logger.info("Declining money request requestId={}", requestId);

            requestService.declineRequest(requestId);

            System.out.println("Money request declined");

        } catch (Exception e) {
            logger.error("Failed to decline request", e.getMessage());
            System.out.println("Decline request failed");
        }
    }
}
