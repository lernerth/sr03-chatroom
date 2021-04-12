package services.impl;

import java.util.logging.Level;
import java.util.logging.Logger;

import dao.Dao;
import dao.impl.DaoImpl;
import models.User;
import services.LoginService;

public class LoginServiceImpl implements LoginService {
	Dao sd = new DaoImpl();
	Logger logger = Logger.getLogger(LoginServiceImpl.class.getName());

	@Override
	public User checkLogin(String login, String pwd) {
		logger.log(Level.INFO, login + "request to log in");
		User u = sd.checkLogin(login, pwd);

		if (u != null)
			logger.log(Level.INFO, login + "login successfully");
		else
			logger.log(Level.INFO, login + "login failed");
		return u;
	}

	@Override
	public boolean existLogin(String login) {
		User u = sd.findUserByLogin(login);
		return (u != null);
	}

	@Override
	public User findUser(String login) {
		User u = sd.findUserByLogin(login);
		return u;
	}

	@Override
	public int addUser(User u) {
		return sd.add(u);
	}

	@Override
	public User checkUserId(String uid) {
		return sd.findUserById(uid);
	}

}
