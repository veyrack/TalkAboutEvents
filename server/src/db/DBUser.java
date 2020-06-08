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
import db.DBEntertainment;

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
	
	public static void deleteUser(User user) throws SQLException {
		DbHandler db = new DbHandler();
		db.loadDb();
		Connection conn = db.getConn();
		Statement st = conn.createStatement();
		st.executeQuery("DELETE FROM Utilisateurs WHERE id = "+ user.getId() + "");
	}
	
	public static void participateTo(Integer idUser, String idEvent) throws SQLException {
		DbHandler db = new DbHandler();
		db.loadDb();
		Connection conn = db.getConn();
		PreparedStatement addquery = conn
				.prepareStatement("INSERT INTO Participate(idUser,idEvent) VALUES(?,?);");
		addquery.setInt(1, idUser);
		addquery.setString(2, idEvent);
		addquery.executeUpdate();
	}
	
	public static void unparticipateTo(Integer idUser, String idEvent) throws SQLException {
		DbHandler db = new DbHandler();
		db.loadDb();
		Connection conn = db.getConn();
		Statement st = conn.createStatement();
		st.executeQuery("DELETE FROM Participate WHERE idUser = "+ idUser + " AND idEvent = \""+idEvent+"\"");
	}
	
	public static void suscribeTo(Integer idUser, String idEnt) throws SQLException {
		DbHandler db = new DbHandler();
		db.loadDb();
		Connection conn = db.getConn();
		PreparedStatement addquery = conn
				.prepareStatement("INSERT INTO Abonnement(idUser,idEnter) VALUES(?,?);");
		addquery.setInt(1, idUser);
		addquery.setString(2, idEnt);
		addquery.executeUpdate();
	}
	public static void unsuscribeTo(Integer idUser, String idEnt) throws SQLException {
		DbHandler db = new DbHandler();
		db.loadDb();
		Connection conn = db.getConn();
		Statement st = conn.createStatement();
		st.executeQuery("DELETE FROM Abonnement WHERE idUser = "+ idUser + " AND idEnter = \""+idEnt+"\"");
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

	public static void updateUser(int id, User user) {
		DbHandler db = new DbHandler();
		db.loadDb();

		try {
			logger.debug("update user " + id);
			Connection conn = db.getConn();

			// TODO : code à factoriser
			if (user.getPdp() != null) {
				logger.debug("update pdp, length : " + user.getPdp().length());
				PreparedStatement query = conn.prepareStatement("UPDATE Utilisateurs SET pdp=? WHERE id = ?;");
				query.setString(1, user.getPdp());
				query.setString(2, Integer.toString(id));
				query.execute();
			}
			if (user.getEmail() != null) {
				logger.debug("update email : " + user.getEmail());
				PreparedStatement query = conn.prepareStatement("UPDATE Utilisateurs SET email=? WHERE id = ?;");
				query.setString(1, user.getEmail());
				query.setString(2, Integer.toString(id));
				query.execute();
			}
			if (user.getBio() != null) {
				logger.debug("update bio : " + user.getBio());
				PreparedStatement query = conn.prepareStatement("UPDATE Utilisateurs SET bio=? WHERE id = ?;");
				query.setString(1, user.getBio());
				query.setString(2, Integer.toString(id));
				query.execute();
			}
			if (user.getPseudo() != null) {
				logger.debug("update pseudo : " + user.getPseudo());
				PreparedStatement query = conn.prepareStatement("UPDATE Utilisateurs SET pseudo=? WHERE id = ?;");
				query.setString(1, user.getPseudo());
				query.setString(2, Integer.toString(id));
				query.execute();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
