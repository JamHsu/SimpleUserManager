package com.manager.bean;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class User implements Serializable{
	
	private static final long serialVersionUID = 1777497490800529561L;
	private Integer id;
	private String name;
	private String password;
	
	public User(){}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer userId) {
		this.id = userId;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String userName) {
		this.name = userName;
	}
	
	@JsonIgnore
	public String getPassword() {
		return password;
	}
	
	@JsonProperty
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public String toString() {
		return "User ["
				+ "Id=" + id
				+ ", password=" + password
				+ ", Name=" + name
				+ "]";
	}
	
}
