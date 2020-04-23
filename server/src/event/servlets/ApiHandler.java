package event.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiHandler {

	private static final String apikey = "KZwJAuD9N7uusFRMfCzSR2RBPKxcA1yJ";

	public String searchEventBy(String label) throws IOException {

		URL url = new URL("https://app.ticketmaster.com/discovery/v1/events.json?apikey=" + apikey + "&keyword=" + label
				+ "&locale=*");
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");

		int status = con.getResponseCode();
		Reader streamReader = null;

		if (status > 299) {
			streamReader = new InputStreamReader(con.getErrorStream());

		} else {
			streamReader = new InputStreamReader(con.getInputStream());
		}

		// recuperation du contenu de la réponse
		BufferedReader in = new BufferedReader(streamReader);
		StringBuffer content = new StringBuffer();
		String currentLine;
		while ((currentLine = in.readLine()) != null) {
			content.append(currentLine);
		}
		in.close();
		return content.toString();
	}

}
