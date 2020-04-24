package user.security;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

import db.DbHandler;

public class AuthHandler {

	private DbHandler db = new DbHandler();

	public Optional<ResultSet> isUserExist(String email) {
		Connection conn = null;
		Statement st = null;
		ResultSet res = null;

		db.loadDb();
		try {
			conn = db.getConn();
			st = conn.createStatement();
			res = st.executeQuery("select * from Utilisateurs where email = \"" + email + "\"");
			if (res.next())
				return Optional.of(res);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Optional.empty();
	}
}
