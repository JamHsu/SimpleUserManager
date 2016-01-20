package com.manager.handler;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.RandomStringUtils;

public class TokenHandler { 
	
	private static final String KEY = "#)$(@*FHWK&:)";
	
	public Boolean verify(String sessionToken, String clientToken) {
		String reverseClientToken = new String(Base64.decodeBase64(clientToken));
		return sessionToken.equals(reverseClientToken);
	}
	
	public String encodeToken(String token) {
		return Base64.encodeBase64String(token.getBytes());
	}
	
	public String createToken() {
		String randomString = RandomStringUtils.randomAlphanumeric(32);
		String token = randomString + KEY;
		return token;
	}
	
}
