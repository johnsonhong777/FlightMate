package com.flightmate.servlets.pages;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.flightmate.beans.FlightHour;
import com.flightmate.beans.User;
import com.flightmate.dao.PilotHoursDao;
import com.flightmate.dao.FlightDao;
import com.flightmate.libs.Role;
import com.flightmate.libs.Route;
import com.flightmate.libs.services.SessionService;


@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    User user = SessionService.srv.getSessionUser(req);

        if (user == null) {
            resp.sendRedirect(Route.LOGIN);
            return;
        }
	    
	    // PILOT-specific queries
	    if (user.getRole().equals(Role.PILOT)) {
	    	 req.setAttribute("flights", FlightDao.getDao().getAllFlights());
	    }
	    
	    
	    // ADMINISTRATOR-specific queries
	    if (user.getRole().equals(Role.ADMINISTRATOR)) {		    
		    // For approving/rejecting hours
            try {
                List<FlightHour> pendingHours = PilotHoursDao.getDao().getPendingFlightHours();
                req.setAttribute("pendingFlightHours", pendingHours);
            } catch (Exception e) {
                e.printStackTrace();
                req.setAttribute("error", "Failed to retrieve pending flight hours.");
            }
	    }
	    
	    req.getRequestDispatcher(Route.DASHBOARD).forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    User user = SessionService.srv.getSessionUser(req);

	    if (user == null) {
	        resp.sendRedirect(Route.LOGIN);
	        return; // Ensure no further actions are taken
	    }

	    doGet(req, resp); // Delegate to doGet for any other actions
	}
}
