package com.manager.bean;

import java.sql.Timestamp;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.manager.handler.TokenHandler;

public class Response {
	
	private String status;
	private String token;
	private Timestamp timeStamp;
	
	public Response(String status) {
		this.status = status;
		this.timeStamp = new Timestamp(new Date().getTime());
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Timestamp getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Timestamp timeStamp) {
		this.timeStamp = timeStamp;
	}

	@JsonInclude(Include.NON_NULL)
	public String getToken() {
		return token;
	}

	public Response appendToken(String token) {
		this.token = new TokenHandler().encodeToken(token);
		return this;
	}
	
}
