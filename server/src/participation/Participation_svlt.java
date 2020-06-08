package participation;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.LogManager;

import db.DBParticipation;
import user.User;

public class Participation_svlt extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private final org.apache.log4j.Logger logger = LogManager.getLogger(Participation_svlt.class);

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		Integer idUser = user.getId();
		String idEvent = request.getParameter("eventId");
		PrintWriter out = response.getWriter();

		logger.debug("check participation for user " + idUser + " at event " + idEvent);

		out.println(DBParticipation.isParticipating(idUser, idEvent));
//		if (DBParticipation.isParticipating(idUser, idEvent))
//			out.println(true);
//		else
//			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
	}

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
