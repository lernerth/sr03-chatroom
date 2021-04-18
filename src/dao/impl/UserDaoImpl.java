package dao.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import dao.UserDao;
import models.User;
import tools.DB;

public class UserDaoImpl implements UserDao {
	@Override
	public int add(User user) {
		String sql = "INSERT INTO user VALUES(null, ?, ?, ?, ?, ?, ?)";
		int rows = 0;
		try {
			Connection c = DB.getConnection();
			PreparedStatement st = c.prepareStatement(sql);
			st.setString(1, user.getLogin());
			st.setString(2, user.getFname());
			st.setString(3, user.getLname());
			st.setString(4, user.getEmail());
			st.setString(5, user.getGender());
			st.setString(6, user.getPwd());

			rows = st.executeUpdate();
			st.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return rows;
	}

	@Override
	public User checkLogin(String login, String pwd) {
		String sql = "SELECT * FROM user WHERE login=? and pwd=?";
		User u = null;
		try {
			Connection c = DB.getConnection();
			PreparedStatement st = c.prepareStatement(sql);
			st.setString(1, login);
			st.setString(2, pwd);

			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				u = new User();
				u.setId(rs.getInt("id"));
				u.setLogin(rs.getString("login"));
				u.setPwd(rs.getString("pwd"));
				u.setEmail(rs.getString("email"));
				u.setFname(rs.getString("fname"));
				u.setLname(rs.getString("lname"));
				u.setGender(rs.getString("gender"));
			}
			rs.close();
			st.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return u;
	}

	@Override
	public User findUserByLogin(String login) {
		String sql = "SELECT * FROM user WHERE login=?";
		User u = null;
		try {
			Connection c = DB.getConnection();
			PreparedStatement st = c.prepareStatement(sql);
			st.setString(1, login);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				u = new User();
				u.setId(rs.getInt("id"));
				u.setLogin(rs.getString("login"));
				u.setPwd(rs.getString("pwd"));
				u.setEmail(rs.getString("email"));
				u.setFname(rs.getString("fname"));
				u.setLname(rs.getString("lname"));
				u.setGender(rs.getString("gender"));
			}
			rs.close();
			st.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return u;
	}

	@Override
	public User findUserById(String id) {
		String sql = "SELECT * FROM user WHERE id=?";
		User u = null;
		try {
			Connection c = DB.getConnection();
			PreparedStatement st = c.prepareStatement(sql);
			st.setString(1, id);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				u = new User();
				u.setId(rs.getInt("id"));
				u.setLogin(rs.getString("login"));
				u.setPwd(rs.getString("pwd"));
				u.setEmail(rs.getString("email"));
				u.setFname(rs.getString("fname"));
				u.setLname(rs.getString("lname"));
				u.setGender(rs.getString("gender"));
			}
			rs.close();
			st.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return u;
	}

	@Override
	public List<User> findUsersNotInChat(int chatId) {
		String sql = "SELECT * FROM user WHERE id NOT IN" + "(SELECT user_id FROM chat_user WHERE chat_id=?)";
		List<User> users = new ArrayList<>();
		User u = null;
		try {
			Connection c = DB.getConnection();
			PreparedStatement st = c.prepareStatement(sql);
			st.setInt(1, chatId);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				u = new User();
				u.setId(rs.getInt("id"));
				u.setLogin(rs.getString("login"));
				u.setPwd(rs.getString("pwd"));
				u.setEmail(rs.getString("email"));
				u.setFname(rs.getString("fname"));
				u.setLname(rs.getString("lname"));
				u.setGender(rs.getString("gender"));
				users.add(u);
			}
			rs.close();
			st.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return users;
	}

	@Override
	public List<User> findUsersInChat(int chatId) {
		String sql = "SELECT * FROM user WHERE id IN" + "(SELECT user_id FROM chat_user WHERE chat_id=?)";
		List<User> users = new ArrayList<>();
		User u = null;
		try {
			Connection c = DB.getConnection();
			PreparedStatement st = c.prepareStatement(sql);
			st.setInt(1, chatId);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				u = new User();
				u.setId(rs.getInt("id"));
				u.setLogin(rs.getString("login"));
				u.setPwd(rs.getString("pwd"));
				u.setEmail(rs.getString("email"));
				u.setFname(rs.getString("fname"));
				u.setLname(rs.getString("lname"));
				u.setGender(rs.getString("gender"));
				users.add(u);
			}
			rs.close();
			st.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return users;
	}
}
