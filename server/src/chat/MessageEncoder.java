package chat;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import com.google.gson.Gson;

/**
 * Serialize un message en json
 */
public class MessageEncoder implements Encoder.Text<Message> {

	@Override
	public void destroy() {
	}

	@Override
	public void init(EndpointConfig arg0) {
	}

	/**
	 * Serialize un message en json
	 * 
	 * @param message message a sérializé
	 */
	@Override
	public String encode(Message message) throws EncodeException {
		return new Gson().toJson(message);
	}

}
