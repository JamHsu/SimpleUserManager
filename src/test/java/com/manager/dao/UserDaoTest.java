package com.manager.dao;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.manager.Application;
import com.manager.bean.User;
import com.manager.dao.interfaces.UserDao;

@SpringApplicationConfiguration(classes = Application.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class UserDaoTest {
	
	@Autowired
	private UserDao dao;
	private User testUser;

	@Before
	public void setUp() throws Exception {
		testUser = new User();
		testUser.setName("testUser");
		testUser.setPassword("testUserPassword");
	}

	@After
	public void tearDown() throws Exception {
		// do nothing
	}

	@Test
	public void testCrudUser() {
		// test create user
		int id = dao.createUser(testUser);
		
		// test get user and verify create is success
		User user = dao.getUser(id);
		assertEquals(id, user.getId().intValue());
		assertEquals("testUser", user.getName());
		assertEquals("testUserPassword", user.getPassword());
		
		// update user test
		User updateUser = new User();
		updateUser.setId(user.getId());
		updateUser.setName("updateUser");
		updateUser.setPassword("updatePassword");
		dao.updateUser(updateUser);
		
		user = dao.getUser(id);
		assertEquals("updateUser", user.getName());
		assertEquals("updatePassword", user.getPassword());
		
		// delete user test
		dao.deleteUser(user.getId());
		try {
			dao.getUser(id);
		} catch (EmptyResultDataAccessException e) {
			assertEquals("Incorrect result size: expected 1, actual 0",  e.getMessage());
		}
	
	}

}
