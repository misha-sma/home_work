package homework.ru.aston.data;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
	private final static String JDBC_DRIVER = "org.postgresql.Driver";

	private final static String USER;
	private final static String PASSWORD;

	private final static String URL;

	static {
		Properties props = new Properties();
		try (InputStream is = DBConnection.class.getResourceAsStream("/jdbc.properties")) {
			props.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		USER = props.getProperty("db.user");
		PASSWORD = props.getProperty("db.password");
		URL = "jdbc:postgresql://" + props.getProperty("db.host") + ":" + props.getProperty("db.port") + "/"
				+ props.getProperty("db.database");
	}

	public static Connection getDbConnection() throws SQLException {
		try {
			Class.forName(JDBC_DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return DriverManager.getConnection(URL, USER, PASSWORD);
	}

}
