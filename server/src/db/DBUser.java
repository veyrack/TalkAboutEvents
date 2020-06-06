package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import members.servlets.SignIn_svlt;
import security.PwdHandler;
import user.User;

public class DBUser {

	private static final Logger logger = LogManager.getLogger(SignIn_svlt.class);

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
			// on recupère la pdp par défaut
//			String base64 = null;
//			try {
//
//				base64 = DatatypeConverter
//						.printBase64Binary(Files.readAllBytes(Paths.get("WebContent/WEB-INF/lib/default.png")));
//			} catch (IOException e) {
//				logger.error(e.getMessage());
//			}
//			addquery.setString(5, base64);
			addquery.setString(5, user.getPdp());
			addquery.setString(6, user.getBio());
			addquery.executeUpdate();
		}
	}

	public static Optional<ResultSet> getUser(String email) {
		DbHandler db = new DbHandler();
		db.loadDb();
		try {
			Connection conn = db.getConn();
			Statement st = conn.createStatement();
			ResultSet res = st.executeQuery("select * from Utilisateurs where email = \"" + email + "\"");
			if (res.next())
				return Optional.of(res);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Optional.empty();
	}

	public static Optional<ResultSet> getUserById(int id) {
		DbHandler db = new DbHandler();
		db.loadDb();
		try {
			Connection conn = db.getConn();
			Statement st = conn.createStatement();
			ResultSet res = st.executeQuery("select * from Utilisateurs where id = \"" + id + "\"");
			if (res.next())
				return Optional.of(res);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Optional.empty();
	}

}
