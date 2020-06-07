package chat;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

@ServerEndpoint(value = "/chat", encoders = MessageEncoder.class, decoders = MessageDecoder.class)
public class ChatEndpoint {

	private static final Logger logger = LogManager.getLogger(ChatEndpoint.class);

	private static Set<Session> peers = Collections.synchronizedSet(new HashSet<Session>());

	@OnOpen
	public void onOpen(Session session) throws IOException {
		logger.debug(String.format("%s joined the chat room.", session.getId()));
		peers.add(session);
	}

	@OnMessage
	public void onMessage(Session session, Message message) throws IOException, EncodeException {
		// broadcast the message
		logger.debug("Message reçu : (from :  " + message.getFrom() + ", to : " + message.getTo() + "), content : "
				+ message.getMessage());
		for (Session peer : peers) {
//			if (!session.getId().equals(peer.getId())) { // do not resend the message to its sender
			peer.getBasicRemote().sendObject(message);
//			}
		}
	}

	@OnClose
	public void onClose(Session session) throws IOException, EncodeException {
		logger.debug(String.format("%s left the chat room.", session.getId()));
		peers.remove(session);
		// notify peers about leaving the chat room
//		for (Session peer : peers) {
//			Message message = new Message();
//			message.setSender("Server");
//			message.setContent(format("%s left the chat room", (String) session.getUserProperties().get("user")));
//			message.setReceived(new Date());
//			peer.getBasicRemote().sendObject(message);
//		}
	}

	@OnError
	public void onError(Session session, Throwable throwable) {
		// Do error handling here
		logger.error(throwable.getMessage());
	}
}