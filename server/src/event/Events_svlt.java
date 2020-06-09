package event;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Servlet pour les evenements
 */
@WebServlet("/Search_svlt")
public class Events_svlt extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final Logger logger = LogManager.getLogger(Events_svlt.class);

	public Events_svlt() {
		super();
	}

	/**
	 * Deux comportements : - si la requete contient un parametre eventId, on
	 * renvoie l'evenement associé a cet id - sinon, on fais une recherche
	 * d'evenements avec les filtres donnés
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		ApiHandler api = new ApiHandler();

		// si on a passer un event id on recupère seulement cet evenement
		String eventId = request.getParameter("eventId");
		if (eventId != null) {
			String content = api.getEventById(eventId);
			out.println(content);
		} else {
			// sinon on fais une recherche générale
			String content = api.searchEventBy(request.getParameterMap());
			out.println(content);
		}
	}

}
