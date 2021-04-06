package test;

import java.io.BufferedInputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.*;

import dao.Dao;
import dao.impl.DaoImpl;
import models.User;

public class TestDB {
	public static void main(String[] args) throws Exception {
		Dao sd = new DaoImpl();
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
