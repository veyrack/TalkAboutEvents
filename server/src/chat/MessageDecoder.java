package chat;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

import com.google.gson.Gson;

/**
 * Convertis un message sérialisé en json en Object Message
 *
 */
public class MessageDecoder implements Decoder.Text<Message> {

	@Override
	public void destroy() {
	}

	@Override
	public void init(EndpointConfig arg0) {
	}

	/**
	 * Converti un message sérialisé en object message
	 * 
	 * @param messageString string du message a convertir
	 */
	@Override
	public Message decode(String messageString) throws DecodeException {
		return new Gson().fromJson(messageString, Message.class);
	}

	@Override
	public boolean willDecode(String arg0) {
		return true;
	}

}
