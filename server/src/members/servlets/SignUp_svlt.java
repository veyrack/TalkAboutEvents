package members.servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import db.DBUser;
import user.User;

/**
 * Servlet implementation class SignUn_svlt
 */
@WebServlet("/SignUp_svlt")
public class SignUp_svlt extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SignUp_svlt() {
		super();
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String pseudo = request.getParameter("pseudo");
		String mdp = request.getParameter("mdp");
		String email = request.getParameter("email");
		User user = new User();
		user.setPseudo(pseudo).setEmail(email).setMdp(mdp);
		try {
			DBUser.AddUser(user);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
