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

@ServerEndpoint(value = "/chat/{room}", encoders = MessageEncoder.class, decoders = MessageDecoder.class)
public class ChatEndpoint {

	private static final Logger logger = LogManager.getLogger(ChatEndpoint.class);

	private static Map<String, Set<Session>> rooms = Collections.synchronizedMap(new HashMap<String, Set<Session>>());

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

	@OnMessage
	public void onMessage(Session session, Message message) throws IOException, EncodeException {
		logger.debug("Message reçu : (from :  " + message.getFrom() + ", to : " + message.getTo() + "), content : "
				+ message.getMessage());
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

	@OnError
	public void onError(Session session, Throwable throwable) {
		// Do error handling here
		logger.error(throwable.getMessage());
	}
}