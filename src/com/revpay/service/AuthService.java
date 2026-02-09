package com.revpay.service;

import com.revpay.dao.UserDAO;
import com.revpay.exception.DuplicateUserException;
import com.revpay.model.User;
import com.revpay.security.PasswordUtil;
import com.revpay.util.InputValidator;

public class AuthService {
	
	private final UserDAO userDAO = new UserDAO();
	
	public void register(User user) throws Exception {
		
		if (!InputValidator.isValidEmail(user.getEmail())) {
			throw new IllegalArgumentException("Invalid email format");
		}
		
		if (!InputValidator.isValidPhone(user.getPhone())) {
			throw new IllegalArgumentException("Invalid phone format");
		}
		
		if (!InputValidator.isValidPin(user.getRawTransactionPin())) {
			throw new IllegalArgumentException("Invalid PIN format");
		}
		
		if (user.getSecurityQuestion() == null || user.getSecurityAnswer() == null) {
	        throw new IllegalArgumentException("Security question and answer are required");
	    }
		
		
		if (userDAO.emailExits(user.getEmail())) {
			throw new DuplicateUserException("Email already registered");
		}
		
		if (userDAO.phoneExists(user.getPhone())) {
	        throw new DuplicateUserException("Phone already registered");
	    }
		
		
		String hashedPassword = PasswordUtil.hash(user.getRawPassword());
		
		String hashedTransactionPin = PasswordUtil.hash(user.getRawTransactionPin());
		
		String hashedSecurityAnswer = PasswordUtil.hash(user.getSecurityAnswer());

		
		
		int userId = userDAO.insertUser(user, hashedPassword, hashedTransactionPin, hashedSecurityAnswer);
		
		WalletService walletService = new WalletService();
		walletService.createWalletForUser(userId);
	}
	
	
	public User login(String login, String rawPassword) throws Exception {

	    User user = userDAO.findByEmailOrPhone(login);

	    if (user == null) {
	        throw new IllegalArgumentException("User not found");
	    }

	    boolean validPassword =
	            PasswordUtil.verify(rawPassword, user.getPasswordHash());

	    if (!validPassword) {
	        throw new IllegalArgumentException("Invalid password");
	    }

	    return user;
	}
	
	
	public String getSecurityQuestion(String login) throws Exception {

	    User user = userDAO.getSecurityDetailsByLogin(login);

	    if (user == null) {
	        throw new IllegalArgumentException("User not found");
	    }
	    return user.getSecurityQuestion();
	}
	
	
	public void resetPassword(String login, String answer, String newPassword) throws Exception {

	    User user = userDAO.getSecurityDetailsByLogin(login);

	    if (user == null) {
	        throw new IllegalArgumentException("User not found");
	    }

	    String storedAnswerHash = user.getPasswordHash();

	    if (!PasswordUtil.verify(answer, storedAnswerHash)) {
	        throw new IllegalArgumentException("Incorrect security answer");
	    }

	    String newHashedPassword = PasswordUtil.hash(newPassword);

	    userDAO.updatePassword(user.getUserId(), newHashedPassword);
	}
	
	
	public int registerAndReturnUserId(User user) throws Exception {

	    if (!InputValidator.isValidEmail(user.getEmail())) {
	        throw new IllegalArgumentException("Invalid email");
	    }

	    if (!InputValidator.isValidPhone(user.getPhone())) {
	        throw new IllegalArgumentException("Invalid phone");
	    }

	    if (!InputValidator.isValidPin(user.getRawTransactionPin())) {
	        throw new IllegalArgumentException("Invalid PIN");
	    }

	    if (user.getSecurityQuestion() == null || user.getSecurityAnswer() == null) {
	        throw new IllegalArgumentException("Security question required");
	    }

	    if (userDAO.emailExits(user.getEmail())) {
	        throw new DuplicateUserException("Email already registered");
	    }

	    if (userDAO.phoneExists(user.getPhone())) {
	        throw new DuplicateUserException("Phone already registered");
	    }

	    String hashedPassword = PasswordUtil.hash(user.getRawPassword());
	    String hashedPin = PasswordUtil.hash(user.getRawTransactionPin());
	    String hashedSecurityAnswer = PasswordUtil.hash(user.getSecurityAnswer());

	    return userDAO.insertUser(
	        user,
	        hashedPassword,
	        hashedPin,
	        hashedSecurityAnswer
	    );
	}
	
	
	public void changePassword(int userId, String currentPassword, String newPassword) throws Exception {

	    User user = userDAO.getUserByIdWithPassword(userId);

	    if (!PasswordUtil.verify(currentPassword, user.getPasswordHash())) {
	        throw new IllegalArgumentException("Current password incorrect");
	    }

	    String newHash = PasswordUtil.hash(newPassword);
	    userDAO.updatePassword(userId, newHash);
	}
	
	
	public void changeTransactionPin(int userId, String oldPin, String newPin) throws Exception {

	    if (!InputValidator.isValidPin(newPin)) {
	        throw new IllegalArgumentException("Invalid PIN format");
	    }

	    String storedPinHash = userDAO.getTransactionPinHash(userId);

	    if (!PasswordUtil.verify(oldPin, storedPinHash)) {
	        throw new IllegalArgumentException("Incorrect current transaction PIN");
	    }

	    String newHashedPin = PasswordUtil.hash(newPin);
	    userDAO.updateTransactionPin(userId, newHashedPin);
	}








}
