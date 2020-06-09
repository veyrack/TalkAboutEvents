package chat;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import db.DBMessage;
import user.Users_svlt;

/**
 * Servlet pour les Messages
 */
@WebServlet("/Messages_svlt")
public class Messages_svlt extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private final org.apache.log4j.Logger logger = LogManager.getLogger(Users_svlt.class);

	/**
	 * Recupère tout les messages d'un salon depuis la bdd la requete doit contenir
	 * un parametre "to" contenant l'id du salon
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();

		logger.debug("get message from room : " + request.getParameter("to"));

		try {
			ResultSet res = DBMessage.getMessages(request.getParameter("to"));

			// on convertis tout les resultats (messages) en json et renvoie un tableau
			// d'objets json
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
			toSend.add("messages", jsonArray);

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
