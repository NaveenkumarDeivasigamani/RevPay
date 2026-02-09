package com.revpay.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revpay.service.AnalyticsService;

public class AnalyticsController {

    private static final Logger logger = LogManager.getLogger(AnalyticsController.class);

    private final AnalyticsService analyticsService = new AnalyticsService();

    public void showBusinessAnalytics(int businessUserId) {

        try {
        	
            logger.info("Fetching analytics for businessUserId={}", businessUserId);
            analyticsService.showBusinessAnalytics(businessUserId);
            
            logger.info("Analytics displayed successfully");

        } catch (Exception e) {
        	
            logger.error("Analytics fetch failed", e);
            System.out.println("Unable to fetch analytics");
        
        }
    }
    
    public void showTopCustomers(int businessUserId) {

        try {
            logger.info("Fetching top customers for userId={}", businessUserId);

            analyticsService.showTopCustomers(businessUserId);

        } catch (Exception e) {
            logger.error("Failed to fetch top customers", e);
            System.out.println("Unable to fetch top customers");
        }
    }
}
