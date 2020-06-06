package user;

import java.sql.ResultSet;

public class User {

	private int id;
	private String pseudo;
	private String bio;
	private String pdp;
	private String email;
	private String mdp;

	public User() {
	}

	public User(int id, String pseudo, String bio, String pdp, String email) {
		this.id = id;
		this.pseudo = pseudo;
		this.bio = bio;
		this.pdp = pdp;
		this.email = pdp;
	}

	public User(ResultSet user) {
		try {
			id = user.getInt("id");
			bio = user.getString("bio");
			email = user.getString("email");
			pdp = user.getString("pdp");
			pseudo = user.getString("pseudo");
		} catch (Exception e) {
			throw new RuntimeException("cant create user from ResultSet");
		}
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	public int getId() {
		return id;
	}

	public User setId(int id) {
		this.id = id;
		return this;
	}

	public String getPseudo() {
		return pseudo;
	}

	public User setPseudo(String pseudo) {
		this.pseudo = pseudo;
		return this;
	}

	public String getBio() {
		return bio;
	}

	public User setBio(String bio) {
		this.bio = bio;
		return this;
	}

	public String getPdp() {
		return pdp;
	}

	public User setPdp(String pdp) {
		this.pdp = pdp;
		return this;
	}

	public String getEmail() {
		return email;
	}

	public User setEmail(String email) {
		this.email = email;
		return this;
	}

	public String getMdp() {
		return mdp;
	}

	public User setMdp(String mdp) {
		this.mdp = mdp;
		return this;
	}

}
