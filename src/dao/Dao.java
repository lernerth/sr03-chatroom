package dao;

import models.User;

public interface Dao {
	int add(User user);

	User checkLogin(String login, String pwd);

	User findUserByLogin(String login);
	
	User findUserById(String id);

//	void delete(String login);
//	User findUserByLogin(String login);
//	User findUserByEmail(String email);
//	User[] findAll();
}
