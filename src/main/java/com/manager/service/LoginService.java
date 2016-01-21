package com.manager.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manager.bean.User;
import com.manager.dao.interfaces.UserDao;
import com.manager.service.abs.BaseService;

@Service
public class LoginService extends BaseService {
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private UserDao userDao;
	
	public Boolean login(String userName, String userPassword) {
		logger.debug("user login:" + userName);
		User user = userDao.getUser(userName);
		Boolean result = user.getName().equals(userName) 
				&& user.getPassword().equals(userPassword);
		logger.debug("User login result:" + result);
		return result;
	}
	
}
