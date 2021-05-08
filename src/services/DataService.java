package services;

import java.util.List;
import java.util.Set;

import models.Chat;
import models.User;

public interface DataService {
	User checkLogin(String login, String pwd);

	boolean existLogin(String login);

	User findUser(String login);

	User findUserById(int id);
	
	User checkUserId(int uid);

	int addUser(User u);

	int addChat(Chat chat, int ownerId);

	Chat findChat(String chatName);

	List<Chat> findOwnChat(int ownerId);

	boolean existChat(String chatName);

	boolean isUserInChat(Chat chat, int userId);

	Set<Integer> findUserIdsOfChat(Chat chat);

	List<Chat> findInvitedChat(int userId);

	int addUsersInChat(int chatId, int[] userIds);

	List<User> findUsersNotInChat(String chatName);

	List<User> findUsersInChat(String chatName);

	boolean deleteChat(String roomName);

	boolean ifUserOwnChat(int uId, String roomName);
	
	User findOwner(Chat chat);

}
