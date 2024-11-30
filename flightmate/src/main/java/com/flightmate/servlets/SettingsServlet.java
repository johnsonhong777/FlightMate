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

@WebServlet("/settings")
public class SettingsServlet extends Validation {
	private static final long serialVersionUID = 1L;
	private final String ACTION_INFO = "action_info";
	private final String ACTION_PASS = "action_pass";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User user = SessionService.srv.getSessionUser(req);
		
		if (user == null) {
			resp.sendRedirect(Route.LOGIN);
			return;
		}
		
		if (req.getAttribute(ACTION_INFO) == null) req.setAttribute(ACTION_INFO, "disabled");
		if (req.getAttribute(ACTION_PASS) == null) req.setAttribute(ACTION_PASS, "disabled");
		
		req.getRequestDispatcher(Route.SETTINGS).forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User user = SessionService.srv.getSessionUser(req);
		
		String message = null;
		boolean updated = false;
		
		if (user == null) {
			resp.sendRedirect(Route.LOGIN);
			return;
		}
		
		// Update User Information
		String action_info = req.getParameter(ACTION_INFO) != null ? req.getParameter(ACTION_INFO) : "";
		String action_pass = req.getParameter(ACTION_PASS) != null ? req.getParameter(ACTION_PASS) : "";
		
		if (action_info.equals("update")) {
			req.setAttribute(ACTION_INFO, "");
		} else if (action_info.equals("save")) {
			req.setAttribute(ACTION_INFO, "disabled");
			message = validateForm(req.getParameterMap());
			
			String firstName = req.getParameter("firstname");
			String lastName = req.getParameter("lastname");
			String email = req.getParameter("email");
			
			if (!user.getEmail().equals(email) && UserDao.getDao().userExists(email)) message = "User with that email already exists. Cannot update email.";
			if (user.getFirstName().equals(firstName) 
					&& user.getLastName().equals(lastName) 
					&& user.getEmail().equals(email) 
				) {
				message = "No info updated.";
			}
			if (message != null) {
				req.setAttribute("message", message);
			} else {
				user.setFirstName(firstName);
				user.setLastName(lastName);
				user.setEmail(email);
				
				updated = UserDao.getDao().updateUser(firstName, lastName, email, user.getUserId());
				if (updated) {
					req.setAttribute("success", "Profile updated.");
				}
			}
			
			
		}
		
		// Update Password
		if (action_pass.equals("update")) {
			req.setAttribute(ACTION_PASS, "");
		} else if (action_pass.equals("save")) {
			req.setAttribute(ACTION_PASS, "disabled");
			String currentPass = req.getParameter("password");
			String newPass = req.getParameter("new_password");
			String confirmNew = req.getParameter("confirm_new");
			
			message = validatePasswordForm(currentPass, newPass, confirmNew, user);
			
			if (message != null) {
				req.setAttribute("message", message);
			} else {
				updated = UserDao.getDao().updatePassword(user.getUserId(), newPass);
				if (updated) {
					req.setAttribute("success", "Password updated.");
				}
			}
		}
				
		doGet(req, resp);
	}
	
	@Override
	protected String validateForm(Map<String, String[]> params) {
		Iterator<Entry<String, String[]>> iterator = params.entrySet().iterator();
		
		while (iterator.hasNext()) {
			Map.Entry<String, String[]> entry = (Entry<String, String[]>)iterator.next();
			String paramName = entry.getKey();
			String paramValue = entry.getValue()[0];
			
			if (isEmpty(paramName)) {return paramName+" cannot be empty.";}
			if (paramName.equals("email")) {
				if (!paramValue.matches(emailRegEx)) return "Email is invalid.";
				
			}
			
			
		}
		return null;
	}
	
	protected String validatePasswordForm(String currentPass, String newPass, String confirmNew, User user) {
		if (isEmpty(currentPass)) return "Password cannot be empty.";
		if (!isEmpty(currentPass) && (isEmpty(newPass) || isEmpty(confirmNew))) return "New password cannot be blank and needs to be confirmed.";
		if (currentPass.equals(newPass)) return "New password cannot be identical to current password";
		if (!confirmNew.equals(newPass)) return "New password and confirmation do not match";
		if (!UserDao.getDao().passwordMatches(user.getEmail(), currentPass)) return "Password does not match account password.";
		return null;	
	}
}
