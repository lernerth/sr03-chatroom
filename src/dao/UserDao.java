package dao;

import java.util.List;

import models.User;

public interface UserDao {
	int add(User user);

	User checkLogin(String login, String pwd);

	User findUserByLogin(String login);

	User findUserById(int id);

	List<User> findUsersNotInChat(int chatId);

	List<User> findUsersInChat(int chatId);
}
