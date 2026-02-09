package com.revpay.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revpay.model.User;
import com.revpay.service.AuthService;

public class AuthController {
	
	private static final Logger logger = LogManager.getLogger(AuthController.class);
	
	private final AuthService authService = new AuthService();
	
	public void register(User user) {
		
		try {
			logger.info("User registration started for email: {}", user.getEmail());
			
			authService.register(user);
			
			logger.info("User registration successful for email: {}", user.getEmail());
			System.out.println("User registered successfully!");
		
		} catch (Exception e) {
			logger.error("User registration failed!, e");
			System.out.println("Registration failed: " + e.getMessage());
		}
	}
	
	
	public void registerPersonal(String name, String email, String phone, String password, String pin, String securityQuestion,String securityAnswer) {

	    try {
	        
	    	logger.info("Personal registration started for email: {}", email);

	        User user = new User();
	        user.setFullName(name);
	        user.setEmail(email);
	        user.setPhone(phone);
	        user.setRawPassword(password);
	        user.setTransactionPin(pin);
	        user.setAccountType("PERSONAL");
	        user.setSecurityQuestion(securityQuestion);
	        user.setSecurityAnswer(securityAnswer);

	        // Reuse core registration logic
	        authService.register(user);

	        logger.info("Personal registration successful for email: {}", email);
	        System.out.println("Personal account registered successfully!");

	    } catch (Exception e) {
	        
	    	logger.error("Personal registration failed", e);
	        System.out.println("Registration failed: " + e.getMessage());
	    }
	}
	
	
	public User login(String login, String password) {

	    try {
	        logger.info("Login attempt for {}", login);

	        User user = authService.login(login, password);

	        logger.info("Login successful for userId={}", user.getUserId());
	        System.out.println("Login successful!");

	        return user;

	    } catch (Exception e) {
	        logger.error("Login failed", e);
	        System.out.println("Login failed: " + e.getMessage());
	        return null;
	    }
	}
	
	
	public String getSecurityQuestion(String login) {

	    try {
	        return authService.getSecurityQuestion(login);
	    } catch (Exception e) {
	        System.out.println("Error: " + e.getMessage());
	        return null;
	    }
	}
	
	
	public void forgotPassword(String login, String answer, String newPassword) {

	    try {
	        authService.resetPassword(login, answer, newPassword);
	        System.out.println("Password reset successful");
	    } catch (Exception e) {
	        System.out.println("Password reset failed: " + e.getMessage());
	    }
	}
	
	
	public int registerBusinessUser(
	        String name,
	        String email,
	        String phone,
	        String password,
	        String pin,
	        String securityQuestion,
	        String securityAnswer) {

	    try {
	        User user = new User();

	        user.setFullName(name);
	        user.setEmail(email);
	        user.setPhone(phone);
	        user.setRawPassword(password);
	        user.setTransactionPin(pin);
	        user.setAccountType("BUSINESS");
	        user.setSecurityQuestion(securityQuestion);
	        user.setSecurityAnswer(securityAnswer);

	        return authService.registerAndReturnUserId(user);

	    } catch (Exception e) {
	        System.out.println("Business registration failed: " + e.getMessage());
	        return -1;
	    }
	}
	
	
	public void changePassword(int userId, String currentPassword, String newPassword) {

	    try {
	        authService.changePassword(userId, currentPassword, newPassword);
	        System.out.println("Password changed successfully");
	    } catch (Exception e) {
	        System.out.println("Password change failed: " + e.getMessage());
	    }
	}
	
	public void changeTransactionPin(int userId, String oldPin, String newPin) {

	    try {
	        logger.info("Transaction PIN change requested for userId={}", userId);

	        authService.changeTransactionPin(userId, oldPin, newPin);

	        logger.info("Transaction PIN changed successfully for userId={}", userId);
	        System.out.println("Transaction PIN changed successfully");

	    } catch (Exception e) {
	        logger.error("Transaction PIN change failed for userId={}", userId, e);
	        System.out.println("Transaction PIN change failed: " + e.getMessage());
	    }
	}










}
