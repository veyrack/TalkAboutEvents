package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import chat.Message;

/**
 * Gère les requetes a la bdd concernant les messages/chat
 *
 */
public class DBMessage {

	/**
	 * Ajout un nouveau message
	 * 
	 * @param message message à ajouter
	 */
	public static void addMessage(Message message) throws SQLException {
		DbHandler db = new DbHandler();
		Connection conn = db.getConn();
		// requête
		PreparedStatement addquery = conn
				.prepareStatement("INSERT INTO Messages(Messages.from,Messages.to,Messages.message) VALUES(?,?,?);");
		addquery.setString(1, message.getFrom());
		addquery.setString(2, message.getTo());
		addquery.setString(3, message.getMessage());
		// execute
		addquery.executeUpdate();
	}

	/**
	 * Renvoie tout les messages d'un salon de chat (tout les messages qui ont pour
	 * destinataire l'id du salon)
	 * 
	 * @param to_id id du salon
	 */
	public static ResultSet getMessages(String to_id) throws SQLException {
		DbHandler db = new DbHandler();
		Connection conn = db.getConn();
		// requete
		Statement st = conn.createStatement();
		return st.executeQuery("SELECT * FROM Messages WHERE Messages.to=\"" + to_id + "\"");
	}
}
