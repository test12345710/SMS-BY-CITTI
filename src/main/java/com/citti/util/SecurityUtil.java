package com.citti.util;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;


public class SecurityUtil {

	public static String generateSalt() {
		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[16]; // 16 bytes = 128 bits
		random.nextBytes(salt);
		return Base64.getEncoder().encodeToString(salt);
	}

	public static String generateEntryCode() {
		String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		int length = 4;

		SecureRandom random = new SecureRandom();
		StringBuilder sb = new StringBuilder(length);

		for (int i = 0; i < length; i++) {
			sb.append(chars.charAt(random.nextInt(chars.length())));
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

	// Verify that input + salt matches stored hash
	public static boolean verifyHash(String input, String storedHash, String salt) {
		String computedHash = hash(input + salt);
		return computedHash.equals(storedHash);
	}

}