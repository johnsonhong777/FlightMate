package com.flightmate.servlets.aircraft;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.flightmate.beans.Aircraft;
import com.flightmate.beans.Airport;
import com.flightmate.beans.User;
import com.flightmate.dao.AircraftDao;
import com.flightmate.dao.AirportDao;
import com.flightmate.dao.UserDao;
import com.flightmate.libs.Role;
import com.flightmate.libs.Route;
import com.flightmate.libs.builders.AircraftBuilder;
import com.flightmate.libs.services.SessionService;

@WebServlet("/aircraft")
public class AircraftServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = SessionService.srv.getSessionUser(req);

        // Check if user is logged in and has ADMINISTRATOR role
        if (user == null || user.getRole() != Role.ADMINISTRATOR) {
            resp.sendRedirect(Route.LOGIN);
            return;
        }

        // Fetch list of aircraft, administrators, and airports
        List<Aircraft> aircraftList = AircraftDao.getDao().getAllAircraft();
        List<User> administrators = UserDao.getDao().getAllAdministrators();
        List<Airport> airports = AirportDao.getDao().getAllAirports();

        // Set attributes for the JSP
        req.setAttribute("aircrafts", aircraftList);
        req.setAttribute("administrators", administrators);
        req.setAttribute("airports", airports);

        // Forward to the aircraft management page
        req.getRequestDispatcher(Route.AIRCRAFT).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = SessionService.srv.getSessionUser(req);

        // Check if user is logged in and has ADMINISTRATOR role
        if (user == null || user.getRole() != Role.ADMINISTRATOR) {
            resp.sendRedirect(Route.LOGIN);
            return;
        }

        // Retrieve form parameters
        String aircraftModel = req.getParameter("aircraftModel");
        String manufactureDateStr = req.getParameter("manufactureDate");
        String lastMaintenanceDateStr = req.getParameter("lastMaintenanceDate");
        String nextMaintenanceDateStr = req.getParameter("nextMaintenanceDate");
        String aircraftNotes = req.getParameter("aircraftNotes");
        int administratorId = Integer.parseInt(req.getParameter("administratorId"));
        int airportId = Integer.parseInt(req.getParameter("airportId"));

        // Validate and parse date values
        try {
            LocalDate manufactureDate = LocalDate.parse(manufactureDateStr);
            LocalDate lastMaintenanceDate = LocalDate.parse(lastMaintenanceDateStr);
            LocalDate nextMaintenanceDate = LocalDate.parse(nextMaintenanceDateStr);

            // Build Aircraft object using builder pattern
            Aircraft aircraft = new AircraftBuilder()
                .setAircraftModel(aircraftModel)
                .setManufactureDate(manufactureDate)
                .setLastMaintenanceDate(lastMaintenanceDate)
                .setNextMaintenanceDate(nextMaintenanceDate)
                .setAircraftNotes(aircraftNotes)
                .setCreatedAt(LocalDateTime.now())
                .setAdministratorId(administratorId)
                .setAirportId(airportId)
                .create();

            // Save the Aircraft to the database
            boolean success = AircraftDao.addAircraft(aircraft);

            // Set success or error message in the session
            if (success) {
                req.getSession().setAttribute("success", "Aircraft added successfully!");
            } else {
                req.getSession().setAttribute("error", "Failed to add aircraft.");
            }

        } catch (Exception e) {
            req.getSession().setAttribute("error", "Invalid data entered: " + e.getMessage());
        }

        // Redirect after POST to avoid form resubmission and show the updated list of aircraft
        resp.sendRedirect("/flightmate/aircraft"); // This should redirect to the correct servlet
    }
    
    
}