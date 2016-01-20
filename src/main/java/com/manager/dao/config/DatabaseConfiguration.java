package com.manager.dao.config;

import java.io.FileInputStream;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

@ComponentScan
@Configuration
public class DatabaseConfiguration {
	
	@Bean
    public DataSource getDataSource() {
		SimpleDriverDataSource dataSource = null;
        Properties props = new Properties();
        FileInputStream fis = null;
        try {
            fis = new FileInputStream("src/main/resources/db.properties");
            props.load(fis);
 
            String dbUrl = props.getProperty("DB_URL");
            String dbName = props.getProperty("DB_NAME");
            String dbUri = dbUrl + dbName;
            String userName = props.getProperty("DB_USERNAME");
            String password = props.getProperty("DB_PASSWORD");
 
            dataSource = new SimpleDriverDataSource();  
            dataSource.setDriverClass(com.mysql.jdbc.Driver.class);
            dataSource.setUrl(dbUri);
            dataSource.setUsername(userName);  
            dataSource.setPassword(password);  
        } catch (Exception e) {
        	throw new RuntimeException("Get connection failed.", e);
        }
        return dataSource;
    }
 
    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(getDataSource());
    }

}
