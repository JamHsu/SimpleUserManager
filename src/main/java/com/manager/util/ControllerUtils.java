package com.manager.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

public class ControllerUtils {
	
	public static ResponseEntity<Object> createResponse(
			Object data, HttpStatus httpStatus) {
		return new ResponseEntity<Object>(data, httpStatus);		
	}
	
	public static ResponseEntity<Object> createResponse(
			Object data, MultiValueMap<String, String> headers, HttpStatus httpStatus) {
		return new ResponseEntity<Object>(data, headers, httpStatus);
	}
	
}
