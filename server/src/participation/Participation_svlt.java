package participation;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.LogManager;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import db.DBParticipation;
import user.User;

/**
 * Servlet gerant la participation au evenements
 *
 */
public class Participation_svlt extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private final org.apache.log4j.Logger logger = LogManager.getLogger(Participation_svlt.class);

	/**
	 * Deux comportement differents : - si un attribut idEvent est passé, regarde si
	 * l'utilisateur participe a l'evenement donné ou pas - sinon, renvoie toute les
	 * participations de l'utilisateur
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		Integer idUser = user.getId();
		String idEvent = request.getParameter("eventId");
		PrintWriter out = response.getWriter();

		if (idEvent != null) {
			logger.debug("check participation for user " + idUser + " at event " + idEvent);
			out.println(DBParticipation.isParticipating(idUser, idEvent));
		} else {
			logger.debug("get all participations for user " + idUser);
			try {
				ResultSet res = DBParticipation.allParticipations(idUser);

				JsonArray jsonArray = new JsonArray();
				while (res.next()) {
					int nb_col = res.getMetaData().getColumnCount();
					JsonObject obj = new JsonObject();
					for (int i = 0; i < nb_col; i++) {
						obj.add(res.getMetaData().getColumnLabel(i + 1).toLowerCase(),
								new Gson().toJsonTree(res.getObject(i + 1)));
					}
					jsonArray.add(obj);
				}
				JsonObject toSend = new JsonObject();
				toSend.add("participations", jsonArray);

				String reString = new Gson().toJson(toSend);
				response.setStatus(HttpServletResponse.SC_OK);
				out.println(reString);
			} catch (Exception e) {
				e.printStackTrace();
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				logger.error(e.getMessage());
			}
		}
	}

	/**
	 * Créer une nouvelle participation pour l'utilisateur le parametre "idEvent"
	 * doit etre donné
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		Integer idUser = user.getId();
		String idEvent = request.getParameter("idEvent");

		logger.debug("user " + idUser + " participate to event " + idEvent);

		try {
			DBParticipation.participateTo(idUser, idEvent);
			response.setStatus(HttpServletResponse.SC_OK);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		Integer idUser = user.getId();
		String idEvent = request.getParameter("idEvent");
		try {
			DBParticipation.unparticipateTo(idUser, idEvent);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
