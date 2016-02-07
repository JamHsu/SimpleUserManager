package com.manager.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.manager.bean.Response;
import com.manager.bean.User;
import com.manager.constant.ParameterConstant;
import com.manager.service.LoginService;
import com.manager.util.ControllerUtils;
import com.manager.util.TokenUtil;

@RestController
@RequestMapping("/auth")
public class LoginController {
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private LoginService service;
	
	@RequestMapping(value ="/login", method = RequestMethod.POST)
	public ResponseEntity<Response> login(HttpServletRequest request,
			@RequestBody User user) {
		try {
			if(user.getName() != null
					&& user.getPassword() != null) {
				String userName = user.getName();
				String userPassword = user.getPassword();
				
				if(service.login(userName, userPassword)) {
					String token = TokenUtil.createToken();
					HttpSession session = request.getSession();
					session.setAttribute(ParameterConstant.TOKEN, token);
					Response response = new Response("Login success.")
									.appendToken(token);
					return ControllerUtils.createRespone(response, HttpStatus.OK);
				} else {
					return ControllerUtils.createRespone(
							new Response("Login failed, user name or password incorrect.")
							, HttpStatus.UNAUTHORIZED);
				}
				
			} else {
				return ControllerUtils.createRespone(
						new Response("Can not get user name or password")
						, HttpStatus.UNPROCESSABLE_ENTITY); 
			}
		} catch (Exception e) {
			logger.error("Login failed", e);
			return ControllerUtils.createRespone(
					new Response("Login failed:" + e.getMessage())
					, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	@RequestMapping(value ="/logout", method = RequestMethod.POST)
	public ResponseEntity<Response> logout(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if(session != null) {
			session.setAttribute(ParameterConstant.TOKEN, null);
		}
		return ControllerUtils.createRespone(
				new Response("Logout success.")
				, HttpStatus.OK);
	}
	
}
