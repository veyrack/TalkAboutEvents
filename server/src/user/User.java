package user;

public class User {

	private int id;
	private String pseudo;
	private String bio;
	private String pdp;
	private String email;

	public User() {
	}

	public User(int id, String pseudo, String bio, String pdp, String email) {
		this.id = id;
		this.pseudo = pseudo;
		this.bio = bio;
		this.pdp = pdp;
		this.email = pdp;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public void UpdateField(String label, Object value) {

	}

}
