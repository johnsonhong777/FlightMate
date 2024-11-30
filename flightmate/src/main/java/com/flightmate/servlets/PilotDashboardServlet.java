package com.flightmate.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.flightmate.beans.User;
import com.flightmate.libs.services.SessionService;

@WebServlet("/pilotDashboard")
public class PilotDashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = SessionService.srv.getSessionUser(req);

        if (user == null) {
            resp.sendRedirect("./login");
            return;
        }

        if (!"PILOT".equals(user.getRole().toString())) {
            resp.sendRedirect("./unauthorized");
            return;
        }

        req.setAttribute("user", user);
        req.getRequestDispatcher("./pilotDashboard.jsp").forward(req, resp);
    }
}
