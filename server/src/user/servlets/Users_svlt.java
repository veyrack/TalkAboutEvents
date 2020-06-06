package user.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.LogManager;

import com.google.gson.Gson;

import db.DBUser;
import user.User;

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

//		logger.debug("blbla");

		String id = request.getParameter("id");
		logger.debug("id = " + id);
		if (id != null) { // on renvoie l'utilisateur associé à cet id
			Optional<ResultSet> user = DBUser.getUserById(Integer.parseInt(id));
			if (user.isPresent()) { // l'utilisateur existe on le renvoie
				response.setStatus(HttpServletResponse.SC_OK);
				out.println(gson.toJson(user.get()));
			} else { // l'utilisateur n'existe pas
				response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			}
		} else { // on renvoie l'utilisateur courant s'il existe
			HttpSession session = request.getSession(false);
			if (session == null)
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			else {
				User user = (User) session.getAttribute("user");
				response.setStatus(HttpServletResponse.SC_OK);
				out.println(gson.toJson(user));
			}
		}
	}

}
