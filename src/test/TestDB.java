package test;

import java.io.BufferedInputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.*;

import dao.UserDao;
import dao.impl.UserDaoImpl;
import models.User;

public class TestDB {
	public static void main(String[] args) throws Exception {
		UserDao sd = new UserDaoImpl();
//		User user = new User();
//		user.setLogin("test");
//		user.setFname("test");
//		user.setLname("test");
//		user.setGender("male");
//		user.setPwd("test");
//		user.setEmail("test@test");
//		
//		sd.add(user);
	}
}
