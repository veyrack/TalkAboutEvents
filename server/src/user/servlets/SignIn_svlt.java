package user.servlets;

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

import com.google.gson.Gson;

import user.Users;
import user.security.AuthHandler;
import user.security.PwdHandler;

/**
 * Servlet implementation class SignIn_svlt
 */
@WebServlet("/SignIn_svlt")
public class SignIn_svlt extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SignIn_svlt() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		Gson gson = new Gson();
		AuthHandler auth = new AuthHandler();
		PwdHandler pwd = new PwdHandler();

		String pseudo = request.getParameter("pseudo");
		String mdp = request.getParameter("mdp");
		Optional<ResultSet> resOpt = auth.isUserExist(pseudo);

		if (resOpt.isPresent()) {
			ResultSet res = resOpt.get();
			try {
				String mdpDb = res.getString("mdp");
				String saltDb = res.getString("salt");

				if (pwd.verifyPwd(mdp, mdpDb, saltDb)) {
					Users user = new Users();
					user.setBio(res.getString("bio"));
					user.setEmail(res.getString("email"));
					user.setPdp(res.getString("pdp"));
					user.setPseudo(res.getString("pseudo"));

					HttpSession session = request.getSession();
					session.setAttribute("userInfos", user);
					out.println(gson.toJson(user));
				} else {
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
		} else
			out.println(gson.toJson(Optional.empty()));

	}

}
