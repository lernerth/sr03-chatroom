package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import dao.ChatDao;
import models.Chat;
import models.User;
import tools.DB;

public class ChatDaoImpl implements ChatDao {
	@Override
	public int add(Chat chat, int ownerId) {
		String sql = "INSERT INTO chat VALUES(null, ?, ?)";
		String sql1 = "INSERT INTO chat_user VALUES(null, ?, ?)";
		int rows = 0;
		int rows1 = 0;
		int chatId = -1;
		try {
			Connection c = DB.getConnection();
			c.setAutoCommit(false);

			PreparedStatement st = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			PreparedStatement st1 = c.prepareStatement(sql1);

			st.setString(1, chat.getName());
			st.setInt(2, chat.getOwnerId());
			rows = st.executeUpdate();
			ResultSet rs = st.getGeneratedKeys();

			if (rs.next()) {
				chatId = rs.getInt(1);
				st1.setInt(1, chatId);
				st1.setInt(2, ownerId);
				rows1 = st1.executeUpdate();
			}

			// Rollback toutes les insertions lors qu'une des deux ¨¦chou¨¦e
			if (rows <= 0 || rows1 <= 0) {
				c.rollback();
				rows = rows <= 0 ? rows : rows1;
			}
			st.close();
			st1.close();

			c.commit();
			c.setAutoCommit(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return rows;
	}

	@Override
	public Chat findChatByName(String name) {
		String sql = "SELECT * FROM chat WHERE name=?";
		Chat chat = null;
		try {
			Connection c = DB.getConnection();
			PreparedStatement st = c.prepareStatement(sql);
			st.setString(1, name);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				chat = new Chat();
				chat.setId(rs.getInt("id"));
				chat.setName(rs.getString("name"));
				chat.setOwnerId(rs.getInt("owner_id"));

			}
			rs.close();
			st.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return chat;
	}

	@Override
	public List<Chat> findChatByOwner(int ownerId) {
		List<Chat> chatList = new ArrayList<>();
		String sql = "SELECT * FROM chat WHERE owner_id=?";
		Chat chat = null;
		try {
			Connection c = DB.getConnection();
			PreparedStatement st = c.prepareStatement(sql);
			st.setInt(1, ownerId);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				chat = new Chat();
				chat.setId(rs.getInt("id"));
				chat.setName(rs.getString("name"));
				chat.setOwnerId(rs.getInt("owner_id"));

				chatList.add(chat);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return chatList;
	}

	@Override
	public Set<Integer> findUserIdsByChat(Chat chat) {
		Set<Integer> userSet = new HashSet<>();
		String sql = "SELECT user_id FROM chat_user WHERE chat_id=?";

		try {
			Connection c = DB.getConnection();
			PreparedStatement st = c.prepareStatement(sql);
			st.setInt(1, chat.getId());
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				userSet.add(rs.getInt("user_id"));
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return userSet;
	}

	@Override
	public Set<Integer> findChatIdsByUser(int userId) {
		Set<Integer> chatSet = new HashSet<>();
		String sql = "SELECT chat_id FROM chat_user WHERE user_id=?";

		try {
			Connection c = DB.getConnection();
			PreparedStatement st = c.prepareStatement(sql);
			st.setInt(1, userId);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				chatSet.add(rs.getInt("chat_id"));
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return chatSet;
	}

	@Override
	public List<Chat> findInvitedChatByUserId(int userId) {
		List<Chat> chatList = new ArrayList<>();
		String sql = "SELECT * FROM chat WHERE owner_id<>? AND id IN"
				+ "(SELECT chat_id from chat_user where user_id=?)";
		Chat chat = null;
		try {
			Connection c = DB.getConnection();
			PreparedStatement st = c.prepareStatement(sql);
			st.setInt(1, userId);
			st.setInt(2, userId);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				chat = new Chat();
				chat.setId(rs.getInt("id"));
				chat.setName(rs.getString("name"));
				chat.setOwnerId(rs.getInt("owner_id"));

				chatList.add(chat);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return chatList;
	}

	@Override
	public int addUsersInChat(int chatId, int[] userIds) {
		String sql = "INSERT INTO chat_user VALUES(null, ?, ?)";
		int[] rows = new int[userIds.length];
		int res = 1;

		try {
			Connection c = DB.getConnection();
			c.setAutoCommit(false);
			PreparedStatement st = c.prepareStatement(sql);

			for (int i = 0; i < userIds.length; i++) {
				st.setInt(1, chatId);
				st.setInt(2, userIds[i]);
				st.addBatch();

			}
			rows = st.executeBatch();
			for (int r : rows)
				if (r <= 0) {
					res = r;
					c.rollback();
					break;
				}

			st.close();
			c.commit();
			c.setAutoCommit(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return res;
	}

}
