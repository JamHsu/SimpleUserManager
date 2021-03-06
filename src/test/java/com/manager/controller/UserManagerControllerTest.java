package com.manager.controller;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.manager.Application;
import com.manager.bean.Response;
import com.manager.bean.User;
import com.manager.constant.ResponseMsg;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class UserManagerControllerTest {

	
	@Autowired
    private WebApplicationContext context;
	
	private MockMvc mockMvc;
	
	@Autowired
	private UserManagerController userController;
	
	@Before
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
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
		Object responseData = userController.createUser(newUser).getBody();
		assertTrue(responseData instanceof User);
		Integer userId = ((User) responseData).getId();
		
		// get user
		User getUser = testGetUser(testUserName, testUserPassword, userId);
		
		// update User
		final String updateUserName = "updateUser";
		final String updateUserPassword = "updatePassworkd";
		User updateUser = new User();
		updateUser.setId(getUser.getId());
		updateUser.setName(updateUserName);
		updateUser.setPassword(updateUserPassword);
		responseData = (Response) userController.updateUser(updateUser).getBody();
		assertTrue(responseData instanceof Response);
		assertEquals(ResponseMsg.UPDATE_USER_SUCCESS, ((Response) responseData).getStatus());
		testGetUser(updateUserName, updateUserPassword, updateUser.getId());
		
		// delete
		responseData = (Response) userController.deleteUser(updateUser.getId()).getBody();
		assertTrue(responseData instanceof Response);
		assertEquals(ResponseMsg.DELETE_USER_SUCCESS, ((Response) responseData).getStatus());
	}

	private User testGetUser(final String expectUserName,
			final String expectUserPassword, Integer userId) {
		
		Object responseData = userController.getUser(userId).getBody();
		assertTrue(responseData instanceof User);
		User getUser = (User) responseData;
		assertEquals(expectUserName, getUser.getName());
		assertEquals(expectUserPassword, getUser.getPassword());
		return getUser;
	}
	
	@Test
	public void testMockCrudUser() throws Exception {
		
		// create
		String createUserJson = "{"
				+ "\"name\":\"mockUserTest\","
				+ "\"password\":\"mockUserTest\""
				+ "}";
		
		MvcResult result = mockMvc.perform(
				post("/user")
				.content(createUserJson)
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andReturn();
		
		// read
		String responseStr = result.getResponse().getContentAsString();
		User userData = new ObjectMapper().readValue(responseStr, User.class);
		String tempJson = new Gson().toJson(userData);
		User responseUser = new ObjectMapper().readValue(
				tempJson, User.class);
		tesMockGetUser(responseUser, "\"name\":\"mockUserTest\"");
		
		// update
		String updateUserJson = "{"
						+ "\"id\":\""+responseUser.getId()+"\","
						+ "\"name\":\"mockUserTestUpdate\","
						+ "\"password\":\"mockUserTestUpdate\""
						+ "}";
		mockMvc.perform(
				put("/user")
				.content(updateUserJson)
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(content().string(containsString(ResponseMsg.UPDATE_USER_SUCCESS)));
		
		tesMockGetUser(responseUser, "\"name\":\"mockUserTestUpdate\"");
		
		// delete user
		mockMvc.perform(
				delete("/user/" + responseUser.getId())
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(content().string(containsString(ResponseMsg.DELETE_USER_SUCCESS)));
		
	}

	private void tesMockGetUser(User responseUser, String expectContainsString) throws Exception {
		mockMvc.perform(
				get("/user/" + responseUser.getId())
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(content().string(containsString(expectContainsString)));
	}

}
