package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DBSubscription {

	public static void suscribeTo(Integer idUser, String idEnt) throws SQLException {
		DbHandler db = new DbHandler();
		db.loadDb();
		Connection conn = db.getConn();
		PreparedStatement addquery = conn.prepareStatement("INSERT INTO Abonnement(idUser,idEnter) VALUES(?,?);");
		addquery.setInt(1, idUser);
		addquery.setString(2, idEnt);
		addquery.executeUpdate();
	}

	public static void unsuscribeTo(Integer idUser, String idEnt) throws SQLException {
		DbHandler db = new DbHandler();
		db.loadDb();
		Connection conn = db.getConn();
		Statement st = conn.createStatement();
		st.executeUpdate("DELETE FROM Abonnement WHERE idUser = " + idUser + " AND idEnter = \"" + idEnt + "\"");
	}
}
