package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBParticipation {

	static DbHandler db = new DbHandler();

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

	public static void participateTo(Integer idUser, String idEvent) throws SQLException {
		DbHandler db = new DbHandler();
		Connection conn = db.getConn();
		PreparedStatement addquery = conn.prepareStatement("INSERT INTO Participe(idUser,idEvent) VALUES(?,?);");
		addquery.setInt(1, idUser);
		addquery.setString(2, idEvent);
		addquery.executeUpdate();
	}

	public static void unparticipateTo(Integer idUser, String idEvent) throws SQLException {
		DbHandler db = new DbHandler();
		Connection conn = db.getConn();
		Statement st = conn.createStatement();
		st.executeUpdate("DELETE FROM Participe WHERE idUser = " + idUser + " AND idEvent = \"" + idEvent + "\"");
	}
}
