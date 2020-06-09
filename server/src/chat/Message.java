package chat;

/**
 * Message pouvant etre transmis entre le client et le serveur
 *
 */
public class Message {

	/**
	 * Id de l'expediteur
	 */
	private String from;
	/**
	 * Id du salon destinataire
	 */
	private String to;
	/**
	 * contenu du message
	 */
	private String message;

	public Message() {
	}

	public Message(String from, String to, String message) {
		this.from = from;
		this.to = to;
		this.message = message;
	}

	public String getFrom() {
		return from;
	}

	public Message setFrom(String from) {
		this.from = from;
		return this;
	}

	public String getTo() {
		return to;
	}

	public Message setTo(String to) {
		this.to = to;
		return this;
	}

	public String getMessage() {
		return message;
	}

	public Message setMessage(String message) {
		this.message = message;
		return this;
	}

}
