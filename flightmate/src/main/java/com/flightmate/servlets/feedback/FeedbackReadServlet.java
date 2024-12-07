package com.flightmate.servlets.feedback;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.flightmate.beans.Feedback;
import com.flightmate.beans.User;
import com.flightmate.dao.FeedbackDao;
import com.flightmate.libs.Role;
import com.flightmate.libs.services.SessionService;

@WebServlet("/feedback-read")
public class FeedbackReadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User user = SessionService.srv.getSessionUser(req);
		
		if (user == null) {
			resp.sendRedirect("./login");
		}
		
		if (!user.getRole().equals(Role.ADMINISTRATOR)) {
			resp.sendRedirect("./dashboard");
		}
		
		int feedbackId = Integer.parseInt(req.getParameter("id"));
		
		Feedback feedback = FeedbackDao.getDao().getFeedbackById(feedbackId);
		
		if (feedback == null) {
			req.setAttribute("message", "Could not find feedback.");
		}
		
		boolean feedbackReadStatus = feedback.hasRead();
		
		FeedbackDao.getDao().setFeedbackReadStatusById(!feedbackReadStatus, feedbackId);
		
		resp.sendRedirect("./user-management");
	}
}
