package services.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import dao.*;
import dao.impl.*;
import models.Chat;
import models.User;
import services.DataService;

public class DataServiceImpl implements DataService {
	UserDao ud = new UserDaoImpl();
	ChatDao cd = new ChatDaoImpl();

	Logger logger = Logger.getLogger(DataServiceImpl.class.getName());

	@Override
	public User checkLogin(String login, String pwd) {
		logger.log(Level.INFO, login + "request to log in");
		User u = ud.checkLogin(login, pwd);

		if (u != null)
			logger.log(Level.INFO, login + "login successfully");
		else
			logger.log(Level.INFO, login + "login failed");
		return u;
	}

	@Override
	public boolean existLogin(String login) {
		User u = ud.findUserByLogin(login);
		return (u != null);
	}

	@Override
	public User findUser(String login) {
		User u = ud.findUserByLogin(login);
		return u;
	}

	@Override
	public int addUser(User u) {
		return ud.add(u);
	}

	@Override
	public User checkUserId(int uid) {
		return ud.findUserById(uid);
	}

	@Override
	public Chat findChat(String chatName) {
		return cd.findChatByName(chatName);
	}

	@Override
	public boolean existChat(String chatName) {
		Chat c = cd.findChatByName(chatName);
		return (c != null);
	}

	@Override
	public int addChat(Chat chat, int ownerId) {
		return cd.add(chat, ownerId);
	}

	@Override
	public List<Chat> findOwnChat(int ownerId) {
		return cd.findChatByOwner(ownerId);
	}

	@Override
	public boolean isUserInChat(Chat chat, int userId) {
		Set<Integer> users = cd.findUserIdsByChat(chat);
		if (users != null)
			return users.contains(userId);
		return false;
	}

	@Override
	public Set<Integer> findUserIdsOfChat(Chat chat) {
		return cd.findUserIdsByChat(chat);
	}

	@Override
	public List<Chat> findInvitedChat(int userId) {
		return cd.findInvitedChatByUserId(userId);
	}

	@Override
	public int addUsersInChat(int chatId, int[] userIds) {
		return cd.addUsersInChat(chatId, userIds);
	}

	@Override
	public List<User> findUsersNotInChat(String chatName) {
		Chat chat = cd.findChatByName(chatName);
		System.out.println("chat id" +chat.getId());
		if (chat != null)
			return ud.findUsersNotInChat(chat.getId());
		return null;
	}

	@Override
	public List<User> findUsersInChat(String chatName) {
		Chat chat = cd.findChatByName(chatName);
		if (chat != null)
			return ud.findUsersInChat(chat.getId());
		return null;
	}

	@Override
	public boolean deleteChat(String roomName) {
		return cd.deleteChat(roomName);
	}

	@Override
	public boolean ifUserOwnChat(int uId, String chatName) {
		Chat c = findChat(chatName);
		if (c != null)
			return c.getOwnerId() == uId;
		else
			return false;
	}

	@Override
	public User findOwner(Chat chat) {
		return findUserById(chat.getOwnerId());
	}
	
	@Override
	public User findUserById(int id) {
		User u = ud.findUserById(id);
		return u;
	}
}
