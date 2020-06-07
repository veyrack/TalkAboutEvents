package chat;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

import com.google.gson.Gson;

public class MessageDecoder implements Decoder.Text<Message> {

	@Override
	public void destroy() {
	}

	@Override
	public void init(EndpointConfig arg0) {
	}

	@Override
	public Message decode(String messageString) throws DecodeException {
		return new Gson().fromJson(messageString, Message.class);
	}

	@Override
	public boolean willDecode(String arg0) {
		return true;
	}

}
