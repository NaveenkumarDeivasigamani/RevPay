package com.revpay.util;

import java.util.regex.Pattern;

/**
 * Utility class for input validation using REGEX
 * Centralizes validation logic to keep services clean
 */

public class InputValidator {
	
	// Validation pattern for email, phone, pin
	private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
	
	private static final Pattern PHONE_PATTERN = Pattern.compile("^[6-9][0-9]{9}$");
	
	private static final Pattern PIN_PATTERN = Pattern.compile("^[0-9]{4}$");
	
	
	public static boolean isValidEmail(String email) {
		return EMAIL_PATTERN.matcher(email).matches();
	}
		
	public static boolean isValidPhone(String phone) {
		return PHONE_PATTERN.matcher(phone).matches();
	}
	
	public static boolean isValidPin(String pin) {
		return PIN_PATTERN.matcher(pin).matches();
	
				
	}
}
