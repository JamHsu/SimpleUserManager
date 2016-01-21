package com.manager.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.manager.bean.User;
import com.manager.dao.interfaces.UserDao;

@Repository
public class UserDaoImp extends BaseDao implements UserDao {

	private String USER_TABLE = "user";
	
	@Override
	public int createUser(final User user) {
		final String sql = "INSERT INTO user (name, password) VALUES (?,?)";

		KeyHolder keyHolder = new GeneratedKeyHolder();
		getJdbcTemplate().update(new PreparedStatementCreator() {
	        public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
	            PreparedStatement pst =
	                con.prepareStatement(sql, new String[]{"id"});
	            pst.setString(1, user.getName());
	            pst.setString(2, user.getPassword());
	            return pst;
	        }
	    },
	    keyHolder);
		return keyHolder.getKey().intValue();
	}

	@Override
	public List<User> listUsers() {

		String sql = "SELECT"
					+ " id,"
					+ " name,"
					+ " password"
					+ " FROM " + USER_TABLE;
		
		logger.debug("Get All kpis group SQL:"+ sql);
		List<User> kpiGroupInfos = getJdbcTemplate().query(sql, createUserRowMapper());
		return kpiGroupInfos;

	}

	@Override
	public User getUser(Integer userId) { 
		String sql = "SELECT"
				+ " id,"
				+ " name,"
				+ " password"
				+ " FROM " + USER_TABLE
				+ " WHERE id = ?";
		
		User user = (User) getJdbcTemplate().queryForObject(
					sql, new Object[]{userId}, createUserRowMapper());
		return user;		
	}
	
	@Override
	public User getUser(String userName) {
		String sql = "SELECT"
				+ " id,"
				+ " name,"
				+ " password"
				+ " FROM " + USER_TABLE
				+ " WHERE name = ?";
		
		User user = (User) getJdbcTemplate().queryForObject(
					sql, new Object[]{userName}, createUserRowMapper());
		return user;
	}

	@Override
	public void updateUser(User user) {
		
		String sql = "UPDATE "
				+ USER_TABLE
				+ " SET name=?, password=?"
				+ " WHERE id = ?";
		getJdbcTemplate().update(sql, new Object[]{
				user.getName(), user.getPassword(), user.getId()});
	}

	@Override
	public void deleteUser(Integer userId) {
		String sql = "DELETE"
				+ " FROM " + USER_TABLE
				+ " WHERE id = ?";
		
		getJdbcTemplate().update(sql, new Object[]{userId});
	}
	
	private RowMapper<User> createUserRowMapper() {
		return new RowMapper<User>() {

			@Override
			public User mapRow(ResultSet dbResult, int rowNum) throws SQLException {
				User user = new User();
				user.setId(dbResult.getInt("id"));
				user.setName(dbResult.getString("name"));
				user.setPassword(dbResult.getString("password"));
				return user;
			}
			
		};
	}

}
