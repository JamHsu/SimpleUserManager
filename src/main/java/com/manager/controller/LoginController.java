package com.manager.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.manager.bean.Response;
import com.manager.constant.ParameterConstant;
import com.manager.handler.TokenHandler;
import com.manager.service.LoginService;

@RestController
@RequestMapping("/auth")
public class LoginController {
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private LoginService service;
	
	@RequestMapping(value ="/login", method = RequestMethod.POST)
	public Object login(HttpServletRequest request) {
		Map<String,String[]> params = request.getParameterMap();
		try {
			if(params.containsKey(ParameterConstant.USER_NAME)
					&& params.containsKey(ParameterConstant.USER_PASSWORD)) {
			
				String userName = params.get(ParameterConstant.USER_NAME)[0];
				String userPassword = params.get(ParameterConstant.USER_NAME)[0];
				
				if(service.login(userName, userPassword)) {
					String token = new TokenHandler().createToken();
					HttpSession session = request.getSession();
					session.setAttribute(ParameterConstant.TOKEN, token);
					return new Response("Login success.").appendToken(token);
				} else {
					return new Response("Login failed, user name or password incorrect.");
				}
			} else {
				return new Response("Can not get user name or password"); 
			}
		} catch (Exception e) {
			logger.error("Login failed", e);
			return new Response("Login failed:" + e.getMessage());
		}

	}
	
	@RequestMapping(value ="/logout", method = RequestMethod.POST)
	public Object logout(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if(session != null) {
			session.setAttribute(ParameterConstant.TOKEN, null);
		}
		return new Response("Logout success.");
	}
	
}
