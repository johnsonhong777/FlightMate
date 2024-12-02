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
import java.sql.SQLException;

/**
 * Servlet implementation class PilotHourEntryServlet
 */
@WebServlet("/logFlightHours")
public class PilotHourEntryServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

  
    public PilotHourEntryServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().append("Served at: ").append(request.getContextPath());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = SessionService.srv.getSessionUser(req);

        if (user == null || user.getRole() != Role.PILOT) {
            resp.sendRedirect(Route.LOGIN);
            return;
        }

        String flightDate = req.getParameter("flight_date");
        String hoursFlighted = req.getParameter("hours_flighted");
        String notes = req.getParameter("notes");

        try {
            PilotHoursDao.getDao().addPilotHours(user.getUserId(), flightDate, hoursFlighted, notes);
            req.setAttribute("success", "Flight hours logged successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "Failed to log flight hours.");
        }

        req.getRequestDispatcher(Route.DASHBOARD).forward(req, resp);
    }
}
