package com.manager.dao;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
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
		testUser.setUserName("testUser");
		testUser.setUserPassword("testUserPassword");
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
		assertEquals(id, user.getUserId().intValue());
		assertEquals("testUser", user.getUserName());
		assertEquals("testUserPassword", user.getUserPassword());
		
		// update user test
		User updateUser = new User();
		updateUser.setUserId(user.getUserId());
		updateUser.setUserName("updateUser");
		updateUser.setUserPassword("updatePassword");
		dao.updateUser(updateUser);
		
		user = dao.getUser(id);
		assertEquals("updateUser", user.getUserName());
		assertEquals("updatePassword", user.getUserPassword());
		
		// delete user test
		dao.deleteUser(user.getUserId());
		user = dao.getUser(id);
		
		assertNull(user);
	}

}
