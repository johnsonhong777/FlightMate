package com.flightmate.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.flightmate.beans.User;
import com.flightmate.dao.PilotHoursDao;
import com.flightmate.libs.Role;
import com.flightmate.libs.Route;
import com.flightmate.libs.services.SessionService;

/**
 * Servlet implementation class FlightHourApprovalServlet
 */
@WebServlet("/approveFlightHours")
public class FlightHourApprovalServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     */
    public FlightHourApprovalServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().append("Served at: ").append(request.getContextPath());
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = SessionService.srv.getSessionUser(req);

        // Check if the user is logged in and has ADMINISTRATOR role
        if (user == null || user.getRole() != Role.ADMINISTRATOR) {
            resp.sendRedirect(Route.LOGIN);
            return;
        }

        // Get the action (approve/reject) and the pilot hour ID
        String action = req.getParameter("action");
        int id = Integer.parseInt(req.getParameter("id"));

        try {
            // Update approval status based on the action
            if ("approve".equalsIgnoreCase(action)) {
                PilotHoursDao.getDao().updateApprovalStatus(id, "APPROVED");
            } else if ("reject".equalsIgnoreCase(action)) {
                PilotHoursDao.getDao().updateApprovalStatus(id, "REJECTED");
            }
            req.setAttribute("success", "Action completed successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "Failed to process the action.");
        }

        // Redirect back to the dashboard
        req.getRequestDispatcher(Route.DASHBOARD).forward(req, resp);
    }
}
