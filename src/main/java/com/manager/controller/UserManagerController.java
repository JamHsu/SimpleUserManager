package com.manager.controller;

import java.util.List;

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
import com.manager.constant.ResponseMsg;
import com.manager.exception.UserExistException;
import com.manager.service.UserCrudService;

@RestController
@RequestMapping("/user")
public class UserManagerController {
	
	private Logger logger = Logger.getLogger(UserManagerController.class);
	
	@Autowired
	private UserCrudService service;
	
	@RequestMapping(value ="", method = RequestMethod.POST)
	public Response createUser(@RequestBody User user) {
		try {
			Integer id = service.create(user);
			return new Response(service.getData(id));
		} catch (UserExistException e) {
			return new Response(ResponseMsg.USER_IS_EXIST); 
		} catch (Exception e) {
			String msg = "Create user occur exception:" + e.getMessage();
			logger.error(msg, e);
			return new Response(msg);
		}
	}
	
	@RequestMapping(value ="", method = RequestMethod.GET)
	public Response listUsers() {
		try {
			List<User> userList = service.listData();
			logger.debug("get user count:" + userList.size());
			return new Response(userList);
		} catch (Exception e) {
			String msg = "list users failed:" + e.getMessage();
			logger.error(msg, e);
			return new Response(msg);
		}
	}
	
	@RequestMapping(value ="/{id}", method = RequestMethod.GET)
	public Response getUser(@PathVariable("id") Integer userId) {
		try {
			User user = service.getData(userId);
			logger.debug("get user:" + user);
			return new Response(user);
		} catch (EmptyResultDataAccessException e) {
			return new Response(ResponseMsg.USER_NOT_FOUND);
		} catch (Exception e) {
			String msg = "get user " + userId + " failed:" + e.getMessage();
			logger.error(msg, e);
			return new Response(msg);
		}
	}
	
	@RequestMapping(value="", method = RequestMethod.PUT)
	public Response updateUser(@RequestBody User user) {
		try {
			service.update(user);
			return new Response(ResponseMsg.UPDATE_USER_SUCCESS);
		} catch (Exception e) {
			String msg = "Update user failed:" + e.getMessage();
			logger.error(msg, e);
			return new Response(msg);
		}
	}
	
	@RequestMapping(value ="/{id}", method = RequestMethod.DELETE)
	public Response deleteUser(@PathVariable("id") Integer userId) {
		try {
			service.delete(userId);
			return new Response(ResponseMsg.DELETE_USER_SUCCESS);
		} catch (Exception e) {
			String msg = "Delete user failed:" + e.getMessage();
			logger.error(msg, e);
			return new Response(msg);
		}
		
	}
	
}
