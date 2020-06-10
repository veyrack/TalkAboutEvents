package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import members.SignIn_svlt;
import security.PwdHandler;
import user.User;

/**
 * Gère les requetes a la bdd concernant les utilisateurs
 *
 */
public class DBUser {

	private static final Logger logger = LogManager.getLogger(SignIn_svlt.class);

	/**
	 * Retourne un utilisateur a partir de son email, si il n'existe pas retourne
	 * empty
	 * 
	 * @param email email de l'utilisateur
	 */
	public static Optional<ResultSet> getUser(String email) {
		DbHandler db = new DbHandler();
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

	/**
	 * Recupère un utilisateur a partir de son id, si il n'existe pas renvoie empty
	 * 
	 * @param id id de l'utilisateur
	 */
	public static Optional<ResultSet> getUserById(int id) {
		DbHandler db = new DbHandler();
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

	/**
	 * Ajoute un nouvel utilisateur
	 * 
	 * @param user utilisateur a ajouté
	 */
	public static void AddUser(User user) throws SQLException {
		DbHandler db = new DbHandler();
		Connection conn = db.getConn();
		PwdHandler pwd = new PwdHandler();
		String salt = pwd.generateSalt();
		// on hash le mdp
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
			addquery.setString(5, user.getPdp());
			addquery.setString(6, user.getBio());
			addquery.executeUpdate();
		}
	}

	/**
	 * Met a jour un utilisateur
	 * 
	 * @param id   id de l'utilisateur a mettre a jour
	 * @param user nouvelles données de l'utilisateur
	 */
	public static void updateUser(int id, User user) {
		DbHandler db = new DbHandler();

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

	/**
	 * Supprime un utilisateur
	 * 
	 * @param user utilisateur a supprimé
	 */
	public static void deleteUser(User user) throws SQLException {
		DbHandler db = new DbHandler();
		Connection conn = db.getConn();
		Statement st = conn.createStatement();
		st.executeUpdate("DELETE FROM Utilisateurs WHERE id = " + user.getId() + "");
	}

}
