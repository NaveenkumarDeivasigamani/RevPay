package com.revpay.security;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Utility class for password and PIN hashing.
 * we use bcrypt for secure hashing
 *
 */

public class PasswordUtil {
	
	// hash a plain value (pass or pin)
	public static String hash(String rawValue) {
		return BCrypt.hashpw(rawValue, BCrypt.gensalt());
	}
	
	// verify raw value against hashed value
	public static boolean verify(String rawValue, String hashedValue) {
		return BCrypt.checkpw(rawValue, hashedValue);
	}
}
