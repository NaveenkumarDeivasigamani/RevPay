package com.revpay.controller;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revpay.service.BusinessAccountService;

public class BusinessAccountController {
	
	private static final Logger logger = LogManager.getLogger(BusinessAccountController.class);

    private final BusinessAccountService service = new BusinessAccountService();
    
    
    public void addBusinessDetails(
            int userId,
            String businessName,
            String businessType,
            String taxId,
            String businessAddress) {

        try {
        	
        	logger.info(
                    "Starting business setup for userId={}, businessName={}",
                    userId, businessName
                );
        	
            service.addBusinessDetails(
                userId, businessName, businessType, taxId, businessAddress
            );
            
            logger.info(
                    "Business account setup completed successfully for userId={}",
                    userId
                );
            System.out.println("Business account setup completed");

        } catch (Exception e) {
        	
        	logger.error(
                    "Business account setup failed for userId={}",
                    userId,e
                );
        	
            System.out.println("Business setup failed: " + e.getMessage());
        }
    }

    
}
