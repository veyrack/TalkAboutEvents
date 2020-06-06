package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

import security.PwdHandler;
import user.User;

public class DBUser {

	public static void AddUser(User user) throws SQLException {
		DbHandler db = new DbHandler();
		db.loadDb();
		Connection conn = db.getConn();
		PwdHandler pwd = new PwdHandler();
		String salt = pwd.generateSalt();
		Optional<String> hashedPwdOpt = pwd.hashPwd(user.getMdp(), salt);
		String hashedPwd;
		if (hashedPwdOpt.isPresent()) {
			hashedPwd = hashedPwdOpt.get();
			PreparedStatement addquery = conn
					.prepareStatement("INSERT INTO Utilisateurs(pseudo,email,mdp,salt,pdp,bio) VALUES(?,?,?,?,?,?);");
			addquery.setString(1, user.getPseudo());
			addquery.setString(2, user.getEmail());
			addquery.setString(3, hashedPwd);
			addquery.setString(4, salt);
			addquery.setString(5, user.getPdp());
			addquery.setString(6, user.getBio());
			addquery.executeUpdate();
		}
	}

	public static Optional<ResultSet> getUser(String email) {
		Connection conn = null;
		Statement st = null;
		ResultSet res = null;
		DbHandler db = new DbHandler();

		db.loadDb();
		try {
			conn = db.getConn();
			st = conn.createStatement();
			res = st.executeQuery("select * from Utilisateurs where email = \"" + email + "\"");
			if (res.next())
				return Optional.of(res);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Optional.empty();
	}

}
