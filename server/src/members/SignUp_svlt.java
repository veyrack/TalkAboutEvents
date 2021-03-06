package members;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import db.DBUser;
import user.User;

/**
 * Servlet gerant l'inscription d'un utilisateur
 *
 */
@WebServlet("/SignUp_svlt")
public class SignUp_svlt extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(SignUp_svlt.class);

	public SignUp_svlt() {
		super();
	}

	/**
	 * créer un nouvel utilisateur, on doit passé les parametres "pseudo", "email",
	 * et "mdp"
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String pseudo = request.getParameter("pseudo");
		String mdp = request.getParameter("mdp");
		String email = request.getParameter("email");

		logger.debug("try post user : " + pseudo + " / " + email + " / " + mdp);

		User user = new User();
		user.setPseudo(pseudo).setEmail(email).setMdp(mdp);
		try {
			DBUser.AddUser(user);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
