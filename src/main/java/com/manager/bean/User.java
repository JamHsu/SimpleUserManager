package com.manager.bean;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class User implements Serializable{
	
	private static final long serialVersionUID = 1777497490800529561L;
	private Integer userId;
	private String userName;
	private String password;
	
	public User(){}
	
	public Integer getUserId() {
		return userId;
	}
	
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@JsonIgnore
	public String getUserPassword() {
		return password;
	}
	
	public void setUserPassword(String password) {
		this.password = password;
	}
	
	@Override
	public String toString() {
		return "User ["
				+ "userId=" + userId
				+ ", password=" + password
				+ ", userName=" + userName
				+ "]";
	}
	
}
