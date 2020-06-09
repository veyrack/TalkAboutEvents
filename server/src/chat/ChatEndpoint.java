package chat;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import db.DBMessage;

/**
 * Gère les salons de chat des evenements
 */
@ServerEndpoint(value = "/chat/{room}", encoders = MessageEncoder.class, decoders = MessageDecoder.class)
public class ChatEndpoint {

	private static final Logger logger = LogManager.getLogger(ChatEndpoint.class);

	/**
	 * Ensemble des salons de chat des evenements, pour chaque identifiant de salon
	 * on a un ensemble de session (utilisateur) dans le salon
	 */
	private static Map<String, Set<Session>> rooms = Collections.synchronizedMap(new HashMap<String, Set<Session>>());

	/**
	 * Ouvre une session et la stocke dans le salon d'id passé en parametre si le
	 * salon n'existe pas on le créer
	 * 
	 * @param session session ouverte
	 * @param room    id du salon
	 */
	@OnOpen
	public void onOpen(Session session, @PathParam("room") String room) throws IOException {
		// on vérifie si la room existe déjà
		if (rooms.containsKey(room)) { // la room existe déjà, on ajoute la session dedans
			logger.debug("Un utilisateur rejoint la room : " + room);
			rooms.get(room).add(session);
		} else { // la room n'existe pas
					// on créer la room
			logger.debug("Création de la room : " + room);
			Set<Session> peers = Collections.synchronizedSet(new HashSet<Session>());
			rooms.put(room, peers);
			// on ajoute la session dedans
			logger.debug("Un utilisateur rejoint la room : " + room);
			peers.add(session);
		}
	}

	/**
	 * Gère la recepetion des messages, les transmets a tout les utilisateurs du
	 * salon concerné et sauvegarde le message en bdd
	 * 
	 * @param message message a transmettre
	 */
	@OnMessage
	public void onMessage(Session session, Message message) throws IOException, EncodeException {
		logger.debug("Message reçu : (from :  " + message.getFrom() + ", to : " + message.getTo() + "), content : "
				+ message.getMessage());
		// on recupère le salon destination
		String room = message.getTo();
		for (Session peer : rooms.get(room)) {
			// envoie du message à tout les membres de la room
			peer.getBasicRemote().sendObject(message);
		}
		// on stocke le message en bdd
		try {
			DBMessage.addMessage(message);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Supprime un utilisateur du salon de chat, si le salon de chat est vide on le
	 * supprime aussi
	 * 
	 * @param session utilisateur a supprimer du salon
	 * @param room    salon
	 */
	@OnClose
	public void onClose(Session session, @PathParam("room") String room) throws IOException, EncodeException {
		logger.debug("Un utilisateur part de la room : " + room);
		// on supprime la session de la room
		rooms.get(room).remove(session);
		// si la room est vide on la supprime
		if (rooms.get(room).isEmpty()) {
			logger.debug("La room " + room + " est vide, suppression.");
			rooms.remove(room);
		}
	}

	/**
	 * gère les erreurs
	 */
	@OnError
	public void onError(Session session, Throwable throwable) {
		logger.error(throwable.getMessage());
	}
}