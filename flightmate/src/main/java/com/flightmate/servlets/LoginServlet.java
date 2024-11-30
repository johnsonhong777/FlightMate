package com.flightmate.servlets;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.flightmate.beans.User;
import com.flightmate.dao.UserDao;

import com.flightmate.libs.Route;

import com.flightmate.libs.Validation;
import com.flightmate.libs.services.SessionService;


@WebServlet("/login")
public class LoginServlet extends Validation {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User user = SessionService.srv.getSessionUser(req);
		
		if (user != null) {
			resp.sendRedirect("./dashboard");
			return;
		}
		
		req.getRequestDispatcher(Route.LOGIN).forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String message = validateForm(req.getParameterMap());
		
		if (message != null) {
			req.setAttribute("message", message);
			req.getRequestDispatcher(Route.LOGIN).forward(req, resp);
			return;
		}
		
		
		UserDao.getDao().authenticateUser(req);
		User user = SessionService.srv.getSessionUser(req);
		
		if (user != null) {
			resp.sendRedirect("./dashboard");
			return;
		} else {
			req.setAttribute("message", "Cannot find user with this email address. Please register for an account.");
		}
	}

	@Override
	protected String validateForm(Map<String,String[]> params) {
		Iterator<Entry<String, String[]>> iterator = params.entrySet().iterator();
		String email = null;
		while (iterator.hasNext()) {
			Map.Entry<String, String[]> entry = (Entry<String, String[]>)iterator.next();
			String paramName = entry.getKey();
			String paramValue = entry.getValue()[0];

			
			if (isEmpty(paramName)) {return paramName+" cannot be empty.";}
			if (paramName.equals("email")) {
				if (!paramValue.matches(emailRegEx)) return "Email is invalid.";
				if (!UserDao.getDao().userExists(paramValue)) return "Cannot find user with this email address.";
				email = paramValue;
			}
			
			if (paramName.equals("password")) {
				if (!UserDao.getDao().passwordMatches(email, paramValue)) return "Email & Password do not match. Please try again.";
			}
				
		}
		return null;
	}
}
