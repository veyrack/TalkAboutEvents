package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

import security.PwdHandler;

public class DBUser {

	public static void AddUser(String pseudo, String mdp, String bio, String pdp, String email) throws SQLException {
		DbHandler db = new DbHandler();
		db.loadDb();
		Connection conn = db.getConn();
		PwdHandler pwd = new PwdHandler();
		String salt = pwd.generateSalt();
		Optional<String> hashedPwdOpt = pwd.hashPwd(mdp, salt);
		String hashedPwd;
		if (hashedPwdOpt.isPresent()) {
			hashedPwd = hashedPwdOpt.get();
			PreparedStatement addquery = conn
					.prepareStatement("INSERT INTO Utilisateurs(pseudo,email,mdp,salt,pdp,bio) VALUES(?,?,?,?,?,?);");
			addquery.setString(1, pseudo);
			addquery.setString(2, email);
			addquery.setString(3, hashedPwd);
			addquery.setString(4, salt);
			addquery.setString(5, pdp);
			addquery.setString(6, bio);
			addquery.executeUpdate();
		}
	}

}
