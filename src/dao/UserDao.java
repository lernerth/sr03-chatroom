package dao;

import models.User;

public interface UserDao {
	int add(User user);

	User checkLogin(String login, String pwd);

	User findUserByLogin(String login);

	User findUserById(String id);

}
