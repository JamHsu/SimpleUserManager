## SimpleUserManager
A  simple spring boot application to provide simple user manager web service
This service provide below function

- User CRUD API
- Token and login/logout

### Login API
login

	url: /login
	
	response body:{"token":"token", "status":"Login success.", "timestamp":1234567}

logout

	url: /logout

	response body:{"status":"Logout success.", "timestamp":1234567}

### User CRUD API
create user (POST method)

	 url: /user
	  
	 request body:{"name":"test", "password":"test"}
	 response body:{"id":"1", "name":"test", "password":"test"}

get user (GET method)	 

	url: /user/{id}
	
	response body:{"id":"1", "name":"test", "password":"test"}

list user (GET method)

	url: /user

	response body:{"id":"1", "name":"test", "password":"test"}

update user (PUT method)

	url: /user
	
	request body:{"name":"update", "password":"update"}
	response body:{"status":"Update user success.", "timestamp":"1234567"}

delete user (DELETE method)

	url: /user/{id}

	response body:{"status":"Delete user success.", "timestamp":"1234567"}



### Using framwork
[Spring Boot](http://projects.spring.io/spring-boot/)
building with Gradle.
	
	dependencies {
	  compile("org.springframework.boot:spring-boot-starter-web:1.3.2.RELEASE")
}

####Dependence DB schema
	CREATE TABLE `user` (
	  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
	  `name` char(32) NOT NULL DEFAULT '',
	  `password` char(64) NOT NULL DEFAULT '',
	  PRIMARY KEY (`id`)
	) ENGINE=InnoDB DEFAULT CHARSET=utf8;
