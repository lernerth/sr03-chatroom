package dao;

import java.util.*;

import models.*;

public interface ChatDao {
	int add(Chat chat, int ownerId);

	Chat findChatByName(String name);

	List<Chat> findChatByOwner(int ownerId);

	Set<Integer> findUserIdsByChat(Chat chat);

	boolean deleteChat(String roomName);

}
