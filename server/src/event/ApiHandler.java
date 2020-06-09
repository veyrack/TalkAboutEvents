package event;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class ApiHandler {

	private static final String apikey = "KZwJAuD9N7uusFRMfCzSR2RBPKxcA1yJ";

	private static final Logger logger = LogManager.getLogger(ApiHandler.class);

	/**
	 * Contact l'api pour récupérer des evenements a partir de filtres
	 * 
	 * @param params filtres, doivent correspondres au attributs de l'api
	 */
	public String searchEventBy(Map<String, String[]> params) throws IOException {
		String headUrl = "https://app.ticketmaster.com/discovery/v2/events?apikey=" + apikey;
		String contentUrl = "";
		String tailUrl = "&locale=*";
		for (Map.Entry<String, String[]> entry : params.entrySet()) {
			contentUrl += "&" + entry.getKey() + "=" + entry.getValue()[0];
		}
		URL url = new URL(headUrl + contentUrl + tailUrl);
		logger.debug("search events : " + url);
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

	/**
	 * Contact l'api pour recuperer un evenement a partir de son id
	 * 
	 * @param id id de l'evenement
	 */
	public String getEventById(String id) throws IOException {
		String headUrl = "https://app.ticketmaster.com/discovery/v2/events/";
		String tailUrl = "?apikey=" + apikey;
		URL url = new URL(headUrl + id + tailUrl);
		logger.debug("search event : " + url);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		int status = con.getResponseCode();
		Reader streamReader = null;
		if (status > 299)
			streamReader = new InputStreamReader(con.getErrorStream());
		else
			streamReader = new InputStreamReader(con.getInputStream());

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
