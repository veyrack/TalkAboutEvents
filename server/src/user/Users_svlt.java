package user;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.LogManager;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import db.DBUser;

@WebServlet("/Users_svlt")
public class Users_svlt extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private final org.apache.log4j.Logger logger = LogManager.getLogger(Users_svlt.class);

	public Users_svlt() {
		super();
	}

	/**
	 * Vérifie si l'utilisateur de la session existe
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		PrintWriter out = response.getWriter();
		Gson gson = new Gson();

		String id = request.getParameter("id");

		if (id != null) { // on renvoie l'utilisateur associé à cet id
			logger.debug("get user " + id);
			Optional<ResultSet> user = DBUser.getUserById(Integer.parseInt(id));
			if (user.isPresent()) { // l'utilisateur existe on le renvoie
				response.setStatus(HttpServletResponse.SC_OK);
				User res = new User(user.get());
				out.println(gson.toJson(res));
			} else { // l'utilisateur n'existe pas
				response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			}
		} else { // on renvoie l'utilisateur courant s'il existe
			HttpSession session = request.getSession(false);
			if (session == null) {
				logger.debug("check session : no session");
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			} else {
				User user = (User) session.getAttribute("user");
//				logger.debug("check session : ok, user " + user.getId());
				response.setStatus(HttpServletResponse.SC_OK);
				out.println(gson.toJson(user));
			}
		}
	}

	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		// on récupère la session et l'id de l'utilisateur
		HttpSession session = request.getSession(false);
		User sessionUser = (User) session.getAttribute("user");

		logger.debug("try update user " + sessionUser.getId());

		User toUpdate = new User();

		// on récupère l'objet du corp de la requête
		String requestDataString = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
		JsonObject data = JsonParser.parseString(requestDataString).getAsJsonObject();

		String bio = data.get("bio") == null ? null : data.get("bio").getAsString();
		String email = data.get("email") == null ? null : data.get("email").getAsString();
		String pdp = data.get("pdp") == null ? null : data.get("pdp").getAsString();
		String pseudo = data.get("pseudo") == null ? null : data.get("pseudo").getAsString();

		toUpdate.setId(sessionUser.getId()).setBio(bio).setEmail(email).setPdp(pdp).setPseudo(pseudo);
		DBUser.updateUser(sessionUser.getId(), toUpdate);

	}

}
