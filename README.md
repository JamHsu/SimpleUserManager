## SimpleUserManager
A spring boot application to provide simple user manager web service
This serviceprovide below function

- User CRUD API
- Token and login/logout



####Depend DB schema
	CREATE TABLE `user` (
	  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
	  `name` char(32) NOT NULL DEFAULT '',
	  `password` char(64) NOT NULL DEFAULT '',
	  PRIMARY KEY (`id`)
	) ENGINE=InnoDB DEFAULT CHARSET=utf8;
