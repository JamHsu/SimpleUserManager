package com.manager.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.RandomStringUtils;

public class TokenUtil { 
	
	private static final String KEY = "#)$(@*FHWK&:)";
	
	public static Boolean verify(String sessionToken, String clientToken) {
		String reverseClientToken = new String(Base64.decodeBase64(clientToken));
		return sessionToken.equals(reverseClientToken);
	}
	
	public static String encodeToken(String token) {
		return Base64.encodeBase64String(token.getBytes());
	}
	
	public static String decodeToken(String encodeToken) {
		return new String(Base64.decodeBase64(encodeToken)); 
	}
	
	public static String createToken() {
		String randomString = RandomStringUtils.randomAlphanumeric(32);
		String token = randomString + KEY;
		return token;
	}
	
}
