package com.manager.dao;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public abstract class BaseDao {
	
	protected Logger logger;
	
	@Autowired
	protected JdbcTemplate jdbcTemplate;
	
	protected BaseDao(){
		logger = Logger.getLogger(this.getClass());
	}
	
	protected JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	
}
