package user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

import db.DbHandler;
import user.security.PwdHandler;

public class Users {
	private String pseudo;
	private String bio;
	private String pdp;
	private String email;
	
	public Users() {
		// TODO Auto-generated constructor stub
	}
	
	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public String getPdp() {
		return pdp;
	}

	public void setPdp(String pdp) {
		this.pdp = pdp;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Users(String pseudo,String bio, String pdp, String email) {
		// TODO Auto-generated constructor stub
		this.pseudo = pseudo;
		this.bio = bio;
		this.pdp = pdp;
		this.email = pdp;
	}
	
	public static void AddUser(String pseudo, String mdp, String bio, String pdp, String email) throws SQLException {
		DbHandler db = new DbHandler();
		db.loadDb();
		Connection conn = db.getConn();
		PwdHandler pwd = new PwdHandler();
		String salt = pwd.generateSalt();
		Optional<String> hashedPwdOpt = pwd.hashPwd(mdp, salt);
		String hashedPwd;
		if(hashedPwdOpt.isPresent()) {
			hashedPwd = hashedPwdOpt.get();
			PreparedStatement addquery = conn.prepareStatement("INSERT INTO Utilisateurs(pseudo,email,mdp,salt,pdp,bio) VALUES(?,?,?,?,?,?);");
			addquery.setString(1, pseudo);
			addquery.setString(2, email);
			addquery.setString(3, hashedPwd);
			addquery.setString(4, salt);
			addquery.setString(5, pdp);
			addquery.setString(6, bio);
			addquery.executeUpdate();
		}
	}

	public void UpdateField(String label, Object value) {
		
	}

}
