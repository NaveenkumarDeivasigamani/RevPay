package com.revpay.service;

import com.revpay.dao.UserDAO;

/**
 * Service layer for business account registration.
 */
public class BusinessAccountService {
	
	 private final UserDAO userDAO = new UserDAO();
	 private final WalletService walletService = new WalletService();
	
	 
	 public void addBusinessDetails(
		        int userId,
		        String businessName,
		        String businessType,
		        String taxId,
		        String businessAddress) throws Exception {

		    if (businessName == null || businessName.isBlank()) {
		        throw new IllegalArgumentException("Business name required");
		    }

		    userDAO.updateBusinessDetails(
		        userId, businessName, businessType, taxId, businessAddress
		    );

		    walletService.createWalletForUser(userId);
		}

	

}
