package com.flightmate.servlets;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.flightmate.beans.User;
import com.flightmate.dao.UserDao;
import com.flightmate.libs.Role;
import com.flightmate.libs.Route;
import com.flightmate.libs.Validation;
import com.flightmate.libs.services.SessionService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/signup")
public class SignupServlet extends Validation {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User user = SessionService.srv.getSessionUser(req);
		
		if (user != null) {
			resp.sendRedirect(Route.DASHBOARD);
			return;
		}
		
		req.getRequestDispatcher(Route.SIGNUP).forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String message = validateForm(req.getParameterMap());

		if (message != null) {
			req.setAttribute("message", message);
			req.getRequestDispatcher(Route.SIGNUP).forward(req, resp);
			return;
		}
		
		UserDao.getDao().createUser(req);
		User user = SessionService.srv.getSessionUser(req);
		if (user != null) {
			resp.sendRedirect(Route.DASHBOARD);
			return;
		} else {
			req.setAttribute("message", "We could not create a user at this time. Please try again later.");
		}
	}
	
	@Override
	protected String validateForm(Map<String,String[]> params) {
		Iterator<Entry<String, String[]>> iterator = params.entrySet().iterator();
		
		while (iterator.hasNext()) {
			Map.Entry<String, String[]> entry = (Entry<String, String[]>)iterator.next();
			String paramName = entry.getKey();
			String paramValue = entry.getValue()[0];
			
			if (isEmpty(paramName)) {return paramName+" cannot be empty.";}
			if (paramName.equals("email")) {
				if (!paramValue.matches(emailRegEx)) return "Email is invalid.";
				if (UserDao.getDao().userExists(paramValue)) return "User already exists. Please login using your email & password.";
			}
			
			if (paramName.equals("role") && !paramValue.equals(Role.valueOf(paramValue).toString())) {
				return "This role does not exist. Please select a role.";
			}
		}
		return null;
	}
}
