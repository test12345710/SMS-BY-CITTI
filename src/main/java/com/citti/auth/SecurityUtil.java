package com.citti.auth;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import static com.citti.util.Constants.ENTRY_CODE_CHARS;
import static com.citti.util.Constants.ENTRY_CODE_LENGTH;


public class SecurityUtil {

	public static String generateSalt() {
		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[16]; // 16 bytes = 128 bits
		random.nextBytes(salt);
		return Base64.getEncoder().encodeToString(salt);
	}

	public static String generateEntryCode() {
		SecureRandom random = new SecureRandom();
		StringBuilder sb = new StringBuilder(ENTRY_CODE_LENGTH);

		for (int i = 0; i < ENTRY_CODE_LENGTH; i++) {
			sb.append(ENTRY_CODE_CHARS.charAt(random.nextInt(ENTRY_CODE_CHARS.length())));
		}
		return sb.toString();
	}

	public static String hash(String input) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] hashedBytes = md.digest(input.getBytes());
			return Base64.getEncoder().encodeToString(hashedBytes);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("SHA-256 algorithm not found");
		}
	}

	public static boolean verifyHash(String input, String storedHash, String salt) {
		String computedHash = hash(input + salt);
		return computedHash.equals(storedHash);
	}

}