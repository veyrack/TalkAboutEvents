package event.servlets;

import java.io.IOException;
import java.io.PrintWriter;
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

import db.DBEntertainment;
import db.DBUser;
import user.User;

/**
 * Servlet implementation class Search_svlt
 */
@WebServlet("/Search_svlt")
public class Search_svlt extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final Logger logger = LogManager.getLogger(Search_svlt.class);

	public Search_svlt() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		ApiHandler api = new ApiHandler();

		String content = api.searchEventBy(request.getParameterMap());

		out.println(content);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		Integer idUser = user.getId();
		String idEvent = request.getParameter("idEvent");
		if(DBUser.getUserById(idUser).isPresent()) {
			try {
				DBUser.participateTo(idUser, idEvent);
				response.setStatus(HttpServletResponse.SC_OK);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		}
	}

	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		Integer idUser = user.getId();
		String idEvent = request.getParameter("idEnt");
		if(DBUser.getUserById(idUser).isPresent()) {
			try {
				DBUser.unparticipateTo(idUser, idEvent);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
