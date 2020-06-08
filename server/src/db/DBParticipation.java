package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DBParticipation {

	public static void participateTo(Integer idUser, String idEvent) throws SQLException {
		DbHandler db = new DbHandler();
		db.loadDb();
		Connection conn = db.getConn();
		PreparedStatement addquery = conn.prepareStatement("INSERT INTO Participe(idUser,idEvent) VALUES(?,?);");
		addquery.setInt(1, idUser);
		addquery.setString(2, idEvent);
		addquery.executeUpdate();
	}

	public static void unparticipateTo(Integer idUser, String idEvent) throws SQLException {
		DbHandler db = new DbHandler();
		db.loadDb();
		Connection conn = db.getConn();
		Statement st = conn.createStatement();
		st.executeUpdate("DELETE FROM Participe WHERE idUser = " + idUser + " AND idEvent = \"" + idEvent + "\"");
	}
}
