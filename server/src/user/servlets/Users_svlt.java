package user.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.LogManager;

import com.google.gson.Gson;

import user.Users;

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

//		logger.debug("blbla");

		PrintWriter out = response.getWriter();
		Gson gson = new Gson();

		HttpSession session = request.getSession(false);

		if (session == null)
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		else {
			Users user = (Users) session.getAttribute("user");
			out.println(gson.toJson(user));
			response.setStatus(HttpServletResponse.SC_OK);
		}
	}

}
