package com.manager.bean;

import java.sql.Timestamp;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.manager.util.TokenUtil;

public class Response {
	
	private Object data = null;
	private String status = null;
	private String token = null;
	private Timestamp timeStamp;
	
	protected Response(){
		this.timeStamp = new Timestamp(new Date().getTime());
	}
	
	public Response(Object data) {
		this();
		this.data = data;
	}
	
	public Response(String status) {
		this();
		this.status = status;
	}

	@JsonInclude(Include.NON_NULL)
	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	@JsonInclude(Include.NON_NULL)
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
		this.token = TokenUtil.encodeToken(token);
		return this;
	}
	
}
