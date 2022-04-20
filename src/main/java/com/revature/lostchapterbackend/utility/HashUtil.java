package com.revature.lostchapterbackend.utility;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashUtil {
	
	// hexadecimal = hexArray
		private final static char[] hexArray = "0123456789ABCDEF".toCharArray();
		private final static BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		// convert bytes To String
		private static String bytesToStringHex(byte[] bytes) {
			char[] hexChars = new char[bytes.length * 2];
			for (int i = 0; i < bytes.length; i++) {
				int j = bytes[i] & 0xFF;
				hexChars[i * 2] = hexArray[j >>> 4];
				hexChars[i * 2 + 1] = hexArray[j & 0x0F];
			}

			return new String(hexChars);
		}

		// hash Password
		public static String hashPassword(String password, String algorithm) throws NoSuchAlgorithmException {
			MessageDigest digest = MessageDigest.getInstance(algorithm);
			digest.reset();
			byte[] hash = digest.digest(password.getBytes());

			return bytesToStringHex(hash);
		}

		// hash InputPassword
		public static String hashInputPassword(String inputPassword, String algorithm) throws NoSuchAlgorithmException {

			MessageDigest digest = MessageDigest.getInstance(algorithm);
			digest.reset();
			byte[] hash = digest.digest(inputPassword.getBytes());

			return bytesToStringHex(hash);
		}

		public static String hashPassword(String password) {
			return encoder.encode(password);
		}

		public static String hashInputPassword(String password) {
			return encoder.encode(password);
		}

		public static boolean passwordsMatch(String pass1Encoded, String pass2) {
			return encoder.matches(pass2, pass1Encoded);
		}
}
