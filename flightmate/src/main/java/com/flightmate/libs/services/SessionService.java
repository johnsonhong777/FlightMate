package com.flightmate.libs.services;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.flightmate.beans.User;
import com.flightmate.libs.Role;

public class SessionService {
    public static final SessionService srv = new SessionService();
    private Map<String, Role> roles;
    private final String USER_SESSION_KEY = "user";
    private final Role ADMINISTRATOR = Role.ADMINISTRATOR;
    private final Role PILOT = Role.PILOT;
    
    private SessionService() {
    	roles = new HashMap<>();
    	roles.put(ADMINISTRATOR.toString(), Role.ADMINISTRATOR);
    	roles.put(PILOT.toString(), Role.PILOT);
    }
    

    // Get user from session
    public User getSessionUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
        	
        	request.setAttribute("roles", roles);
            return (User) session.getAttribute(USER_SESSION_KEY);
        }
        return null;
    }

    // Set user in session
    public void setSessionUser(HttpServletRequest request, User user) {
        HttpSession session = request.getSession(true); // Creates a session if it doesn't exist
        session.setAttribute(USER_SESSION_KEY, user);
    }

    // Clear user from session
    public void clearSessionUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(USER_SESSION_KEY);
            session.invalidate();
        }
    }
}