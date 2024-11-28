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
import com.flightmate.libs.Role;
import com.flightmate.libs.Validation;
import com.flightmate.libs.services.SessionService;
@WebServlet("/login")
public class LoginServlet extends Validation {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = SessionService.srv.getSessionUser(req);
        
        // If user is already logged in, redirect to dashboard
        if (user != null) {
            resp.sendRedirect("./dashboard");
            return;
        }
        
        // Forward to login page
        req.getRequestDispatcher("login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Hardcoded credentials for temporary login
        String tempEmail = "admin@example.com";
        String tempPassword = "1";
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        if (tempEmail.equals(email) && tempPassword.equals(password)) {
            User admin = new User(1, tempEmail, "Admin", "User", Role.ADMINISTRATOR.toInt());

            SessionService.srv.setSessionUser(req, admin);

            resp.sendRedirect("./admin-dashboard");
            return;
        }

        // Proceed with real validation and authentication if temporary credentials don't match
        String message = validateForm(req.getParameterMap());
        if (message != null) {
            req.setAttribute("message", message);
            req.getRequestDispatcher("login.jsp").forward(req, resp);
            return;
        }

        // Authenticate user with database
        UserDao.getDao().authenticateUser(req);
        User user = SessionService.srv.getSessionUser(req);

        if (user != null) {
            if (user.getRole() == Role.ADMINISTRATOR) {
                resp.sendRedirect("./admin-dashboard");
            } else {
                resp.sendRedirect("./dashboard");
            }
        } else {
            req.setAttribute("message", "Cannot find user with this email address. Please register for an account.");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
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
