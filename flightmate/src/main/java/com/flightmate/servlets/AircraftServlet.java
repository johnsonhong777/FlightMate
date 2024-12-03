package com.flightmate.servlets;

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
import com.flightmate.dao.AirportDao; // For fetching airports
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
        
        if (user == null || user.getRole() != Role.ADMINISTRATOR) {
            resp.sendRedirect(Route.LOGIN);
            return;
        } 
        
        List<User> administrators = UserDao.getDao().getAllAdministrators();
        List<Airport> airports = AirportDao.getDao().getAllAirports();

        req.setAttribute("administrators", administrators);
        req.setAttribute("airports", airports);
        req.getRequestDispatcher(Route.AIRCRAFT).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = SessionService.srv.getSessionUser(req);

        if (user == null || user.getRole() != Role.ADMINISTRATOR) {
            resp.sendRedirect(Route.LOGIN);
            return;
        }

        String aircraftModel = req.getParameter("aircraftModel");
        LocalDate manufactureDate = LocalDate.parse(req.getParameter("manufactureDate"));
        LocalDate lastMaintenanceDate = LocalDate.parse(req.getParameter("lastMaintenanceDate"));
        LocalDate nextMaintenanceDate = LocalDate.parse(req.getParameter("nextMaintenanceDate"));
        String aircraftNotes = req.getParameter("aircraftNotes");
        int administratorId = Integer.parseInt(req.getParameter("administratorId"));
        int airportId = Integer.parseInt(req.getParameter("airportId"));

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

        if (success) {
            req.getSession().setAttribute("success", "Aircraft added successfully!");  // Use session to store success message
        } else {
            req.getSession().setAttribute("error", "Failed to add aircraft.");
        }

        // Redirect to avoid form resubmission and display the message only after submission
        resp.sendRedirect(Route.AIRCRAFT);  // Redirect to prevent the message from showing before submission
    }

}