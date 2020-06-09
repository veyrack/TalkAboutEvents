package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Gère les requetes a la bdd pour les inscriptions au evenements
 *
 */
public class DBParticipation {

	static DbHandler db = new DbHandler();

	/**
	 * Retourne tout les evenements auquel un utilisateur est inscrit
	 * 
	 * @param idUser id de l'utilisateur
	 */
	public static ResultSet allParticipations(int idUser) {
		Connection conn;
		try {
			conn = db.getConn();
			Statement st = conn.createStatement();
			ResultSet res = st.executeQuery("select * from Participe where idUser='" + idUser + "'");
			return res;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Renvoie true si l'utilisateur participe a l'evenement, false sinon
	 * 
	 * @param idUser  id de l'utilisateur
	 * @param idEvent id de l'evenement
	 */
	public static boolean isParticipating(int idUser, String idEvent) {

		try {
			Connection conn = db.getConn();
			Statement st = conn.createStatement();
			ResultSet res = st.executeQuery(
					"select * from Participe where idUser='" + idUser + "' and idEvent='" + idEvent + "'");
			if (res.next())
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Créer une nouvelle participation
	 * 
	 * @param idUser  id de l'utilisateur a inscrire
	 * @param idEvent id de l'evenement auquel l'utilsiateur s'inscrit
	 */
	public static void participateTo(Integer idUser, String idEvent) throws SQLException {
		DbHandler db = new DbHandler();
		Connection conn = db.getConn();
		PreparedStatement addquery = conn.prepareStatement("INSERT INTO Participe(idUser,idEvent) VALUES(?,?);");
		addquery.setInt(1, idUser);
		addquery.setString(2, idEvent);
		addquery.executeUpdate();
	}

	/**
	 * Supprime une participation d'un utilisateur a un evenement
	 * 
	 * @param idUser  id de l'utilisateur
	 * @param idEvent id de l'evenement
	 */
	public static void unparticipateTo(Integer idUser, String idEvent) throws SQLException {
		DbHandler db = new DbHandler();
		Connection conn = db.getConn();
		Statement st = conn.createStatement();
		st.executeUpdate("DELETE FROM Participe WHERE idUser = " + idUser + " AND idEvent = \"" + idEvent + "\"");
	}
}
