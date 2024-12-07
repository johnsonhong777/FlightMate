package com.flightmate.servlets.feedback;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.flightmate.beans.User;
import com.flightmate.dao.FeedbackDao;
import com.flightmate.libs.Route;
import com.flightmate.libs.services.SessionService;

@WebServlet("/submitFeedback")
public class SubmitFeedbackServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User user = SessionService.srv.getSessionUser(req);
		
		if (user == null) {
			resp.sendRedirect("./login");
		}
		
		FeedbackDao.getDao().createFeedback(req, user.getUserId());
		
		req.setAttribute("message", "Successfully sent feedback!");
		
		req.getRequestDispatcher(Route.DASHBOARD).forward(req, resp);
	}
}
