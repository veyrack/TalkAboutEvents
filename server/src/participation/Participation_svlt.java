package participation;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import db.DBParticipation;
import db.DBUser;
import user.User;

public class Participation_svlt extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		Integer idUser = user.getId();
		String idEvent = request.getParameter("idEvent");
		if (DBUser.getUserById(idUser).isPresent()) {
			try {
				DBParticipation.participateTo(idUser, idEvent);
				response.setStatus(HttpServletResponse.SC_OK);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		}
	}

	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		Integer idUser = user.getId();
		String idEvent = request.getParameter("idEvent");
		if (DBUser.getUserById(idUser).isPresent()) {
			try {
				DBParticipation.unparticipateTo(idUser, idEvent);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
