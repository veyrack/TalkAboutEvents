package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbHandler {

	private Connection conn;

	public void loadDb() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.err.println("com.mysql.cj.jdbc.Driver missing");
		}
		try {
			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/talkaboutevents?zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=UTC",
					"talkaboutevents", "talkaboutevents");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Connection getConn() throws SQLException {
		return this.conn;
	}

}
