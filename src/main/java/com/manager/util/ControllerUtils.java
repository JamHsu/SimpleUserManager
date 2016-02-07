package com.manager.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.manager.bean.Response;

public class ControllerUtils {
	
	public static ResponseEntity<Response> createRespone(
			Response response, HttpStatus httpStatus) {
		
		return new ResponseEntity<Response>(response, httpStatus);
		
	}
	
}
