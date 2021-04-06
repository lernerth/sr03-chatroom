package services;

import models.User;

public interface LoginService {
	User checkLogin(String login, String pwd);
	boolean existLogin(String login);
	User findUser(String login);
	int addUser(User u);
}
