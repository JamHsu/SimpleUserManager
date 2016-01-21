package com.manager.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.manager.bean.Response;
import com.manager.bean.User;
import com.manager.exception.UserExistException;
import com.manager.service.UserCrudService;

@RestController
@RequestMapping("/user")
public class UserManagerController {
	
	private Logger logger = Logger.getLogger(UserManagerController.class);
	
	@Autowired
	private UserCrudService service;
	
	@RequestMapping(value ="", method = RequestMethod.POST)
	public Object createUser(@RequestBody User user) {
		try {
			Integer id = service.create(user);
			return service.getData(id);
		} catch (UserExistException e) {
			return new Response("User is exist."); 
		} catch (Exception e) {
			String msg = "Create user occur exception:" + e.getMessage();
			logger.error(msg, e);
			return new Response(msg);
		}
	}
	
	@RequestMapping(value ="", method = RequestMethod.GET)
	public Object listUsers() {
		try {
			return service.listData();
		} catch (Exception e) {
			String msg = "list users failed:" + e.getMessage();
			logger.error(msg, e);
			return new Response(msg);
		}
	}
	
	@RequestMapping(value ="/{id}", method = RequestMethod.GET)
	public Object getUser(@PathVariable("id") Integer userId) {
		try {
			return service.getData(userId);
		} catch (EmptyResultDataAccessException e) {
			return new Response("User not found.");
		} catch (Exception e) {
			String msg = "get user " + userId + " failed:" + e.getMessage();
			logger.error(msg, e);
			return new Response(msg);
		}
	}
	
	@RequestMapping(value="", method = RequestMethod.PUT)
	public Object updateUser(@RequestBody User user) {
		try {
			service.update(user);
			return new Response("Update user success");
		} catch (Exception e) {
			String msg = "Update user failed:" + e.getMessage();
			logger.error(msg, e);
			return new Response(msg);
		}
	}
	
	@RequestMapping(value ="/{id}", method = RequestMethod.DELETE)
	public Object deleteUser(@PathVariable("id") Integer userId) {
		try {
			service.delete(userId);
			return new Response("Delete user success");
		} catch (Exception e) {
			String msg = "Delete user failed:" + e.getMessage();
			logger.error(msg, e);
			return new Response(msg);
		}
		
	}
	
}
