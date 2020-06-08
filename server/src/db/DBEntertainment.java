package db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public class DBEntertainment {

	public static Optional<ResultSet> getEntertainment(String id) {
		DbHandler db = new DbHandler();
		try {
			Connection conn = db.getConn();
			Statement st = conn.createStatement();
			ResultSet res = st.executeQuery("select * from Entertainment where id = \"" + id + "\"");
			if (res.next())
				return Optional.of(res);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Optional.empty();
	}

}
