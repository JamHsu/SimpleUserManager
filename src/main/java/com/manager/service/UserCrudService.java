package com.manager.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.manager.bean.User;
import com.manager.dao.interfaces.UserDao;
import com.manager.exception.UserExistException;
import com.manager.service.abs.BaseService;
import com.manager.service.interfaces.CrudService;

@Service
public class UserCrudService extends BaseService implements CrudService<User> {
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private UserDao userDao;

	@Override
	public int create(User user) throws UserExistException {
		logger.debug("Create user:" + user);
		
		if(isUserNameExist(user)) {
			logger.info("User already exist:" + user.getName());
			throw new UserExistException();
		}
		return userDao.createUser(user);
	}

	@Override
	public List<User> listData() {
		logger.debug("List users");
		return userDao.listUsers();
	}

	@Override
	public User getData(Integer userId) {
		logger.debug("Find user, id:" + userId);
		return userDao.getUser(userId);
	}

	@Override
	public void update(User user) {
		logger.debug("Update user:" + user);
		userDao.updateUser(user);
	}

	@Override
	public void delete(Integer userId) {
		logger.debug("Delete user, id:" + userId);
		userDao.deleteUser(userId);	
	}
	
	private Boolean isUserNameExist(User user) {
		try {
			return userDao.getUser(user.getName()) != null;
		} catch (EmptyResultDataAccessException e) {
			return Boolean.FALSE;
		}
	}
	
}
