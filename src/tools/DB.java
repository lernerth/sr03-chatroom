package tools;

import java.io.*;
import java.net.URL;
import java.sql.*;
import java.util.Properties;

public class DB {
	private static Connection singleton;

	private DB() throws IOException, ClassNotFoundException, SQLException {
		Properties props = new Properties();
		URL urlFichierProp = DB.class.getResource("fichierProp.properties");
		BufferedInputStream bis = null;
		try {
			bis = new BufferedInputStream(urlFichierProp.openStream());
			props.load(bis);
			String driver = props.getProperty("driver");
			String url = props.getProperty("url");
			String utilisateur = props.getProperty("utilisateur");
			String mdp = props.getProperty("mdp");

			Class.forName(driver);
			singleton = DriverManager.getConnection(url, utilisateur, mdp);
		} finally {
			if (bis != null)
				bis.close();
		}
	}

	public static Connection getConnection() throws IOException, ClassNotFoundException, SQLException {
		if (singleton == null)
			new DB();
		return singleton;

	}

}
