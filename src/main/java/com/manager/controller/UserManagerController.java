package com.manager.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.manager.util.ControllerUtils;

@RestController
@RequestMapping("/user")
public class UserManagerController {
	
	private Logger logger = Logger.getLogger(UserManagerController.class);
	
	@Autowired
	private UserCrudService service;
	
	@RequestMapping(value ="", method = RequestMethod.POST)
	public ResponseEntity<Object> createUser(@RequestBody User user) {
		try {
			Integer id = service.create(user);
			return ControllerUtils.createResponse(
					service.getData(id)
					, HttpStatus.OK);
		} catch (UserExistException e) {
			return ControllerUtils.createResponse(
					new Response(ResponseMsg.USER_IS_EXIST)
					, HttpStatus.OK); 
		} catch (Exception e) {
			String msg = "Create user occur exception:" + e.getMessage();
			logger.error(msg, e);
			return ControllerUtils.createResponse(
					new Response(msg)
					, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value ="", method = RequestMethod.GET)
	public ResponseEntity<Object> listUsers() {
		try {
			List<User> userList = service.listData();
			logger.debug("get user count:" + userList.size());
			return ControllerUtils.createResponse(
					userList
					, HttpStatus.OK);
		} catch (Exception e) {
			String msg = "list users failed:" + e.getMessage();
			logger.error(msg, e);
			return ControllerUtils.createResponse(
					new Response(msg)
					, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value ="/{id}", method = RequestMethod.GET)
	public ResponseEntity<Object> getUser(@PathVariable("id") Integer userId) {
		try {
			User user = service.getData(userId);
			logger.debug("get user:" + user);
			return ControllerUtils.createResponse(
					user
					, HttpStatus.OK);
		} catch (EmptyResultDataAccessException e) {
			return ControllerUtils.createResponse(
					new Response(ResponseMsg.USER_NOT_FOUND)
					, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			String msg = "get user " + userId + " failed:" + e.getMessage();
			logger.error(msg, e);
			return ControllerUtils.createResponse(
					new Response(msg)
					, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value="", method = RequestMethod.PUT)
	public ResponseEntity<Object> updateUser(@RequestBody User user) {
		try {
			service.update(user);
			return ControllerUtils.createResponse(
					new Response(ResponseMsg.UPDATE_USER_SUCCESS)
					, HttpStatus.OK);
		} catch (Exception e) {
			String msg = "Update user failed:" + e.getMessage();
			logger.error(msg, e);
			return ControllerUtils.createResponse(
					new Response(msg)
					, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value ="/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> deleteUser(@PathVariable("id") Integer userId) {
		try {
			service.delete(userId);
			return ControllerUtils.createResponse(
					new Response(ResponseMsg.DELETE_USER_SUCCESS)
					, HttpStatus.OK);
		} catch (Exception e) {
			String msg = "Delete user failed:" + e.getMessage();
			logger.error(msg, e);
			return ControllerUtils.createResponse(
					new Response(msg)
					, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
}
