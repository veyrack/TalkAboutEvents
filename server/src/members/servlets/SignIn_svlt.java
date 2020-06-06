package members.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.google.gson.Gson;

import db.DBUser;
import security.PwdHandler;
import user.User;

/**
 * Servlet implementation class SignIn_svlt
 */
@WebServlet("/SignIn_svlt")
public class SignIn_svlt extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(SignIn_svlt.class);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SignIn_svlt() {
		super();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		PrintWriter out = response.getWriter();
		Gson gson = new Gson();
		PwdHandler pwd = new PwdHandler();

		String email = request.getParameter("email");
		String mdp = request.getParameter("mdp");

		logger.debug("try signin : " + email + " / " + mdp);

		Optional<ResultSet> resOpt = DBUser.getUser(email);
		if (resOpt.isPresent()) {
			ResultSet res = resOpt.get();
			try {
				String mdpDb = res.getString("mdp");
				String saltDb = res.getString("salt");

				if (pwd.verifyPwd(mdp, mdpDb, saltDb)) {
					User user = new User(res);
					HttpSession session = request.getSession(); // on créer la session
					session.setMaxInactiveInterval(-1); // la session n'a pas de date de mort
					session.setAttribute("user", user);
					response.setStatus(HttpServletResponse.SC_OK);
					out.println(gson.toJson(user));
				} else {
					response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
					out.println(gson.toJson(Optional.empty()));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					res.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} else {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			out.println(gson.toJson(Optional.empty()));
		}

	}

}
