package com.manager.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.manager.Application;
import com.manager.bean.Response;
import com.manager.bean.User;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class UserManagerControllerTest {

	@Autowired
	private UserManagerController userController;
	
	@Before
	public void setUp() throws Exception {
		assertNotNull(userController);
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testCrudUser() {
		
		final String testUserName = "testUser";
		final String testUserPassword = "testPassworkd";
		
		// create
		User newUser = new User();
		newUser.setName(testUserName);
		newUser.setPassword(testUserPassword);
		Object response = userController.createUser(newUser);
		assertTrue(response instanceof User);
		Integer userId = ((User) response).getId();
		
		// get user
		User getUser = testGetUser(testUserName, testUserPassword, userId);
		
		// update User
		final String updateUserName = "updateUser";
		final String updateUserPassword = "updatePassworkd";
		User updateUser = new User();
		updateUser.setId(getUser.getId());
		updateUser.setName(updateUserName);
		updateUser.setPassword(updateUserPassword);
		userController.updateUser(updateUser);
		testGetUser(updateUserName, updateUserPassword, updateUser.getId());
		
		// delete
		response = userController.deleteUser(updateUser.getId());
		assertTrue(response instanceof Response);
		assertEquals("Delete user success", ((Response) response).getStatus());
	}

	private User testGetUser(final String expectUserName,
			final String expectUserPassword, Integer userId) {
		
		Object response = userController.getUser(userId);
		assertTrue(response instanceof User);
		User getUser = (User) response;
		assertEquals(expectUserName, getUser.getName());
		assertEquals(expectUserPassword, getUser.getPassword());
		return getUser;
	}

}
