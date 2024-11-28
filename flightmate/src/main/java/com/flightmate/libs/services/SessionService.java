package com.flightmate.libs.services;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.flightmate.beans.User;

public class SessionService {
    public static final SessionService srv = new SessionService();

    private static final String USER_SESSION_KEY = "loggedInUser";

    // Get user from session
    public User getSessionUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
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
