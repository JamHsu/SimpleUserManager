package com.manager.dao.interfaces;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;

import com.manager.bean.User;

public interface UserDao {
	
	public int createUser(User user);
	
	public List<User> listUsers();
	
	public User getUser(Integer userId);
	
	public User getUser(String userName);
	
	public void updateUser(User user);
	
	public void deleteUser(Integer userId);

	
}
