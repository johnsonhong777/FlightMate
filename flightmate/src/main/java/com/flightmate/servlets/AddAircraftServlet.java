package com.flightmate.servlets;

import com.flightmate.beans.Aircraft;
import com.flightmate.beans.Airport;
import com.flightmate.beans.User;
import com.flightmate.dao.AircraftDao;
import com.flightmate.dao.AirportDao;
import com.flightmate.dao.UserDao;
import com.flightmate.libs.builders.AircraftBuilder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet("/add-aircraft")
public class AddAircraftServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // Fetch administrators and airports
            List<User> administrators = AircraftDao.getAllAdministrators(); // Users with ADMIN role
            List<Airport> airports = AirportDao.getAllAirports();       // All airports

            // Set attributes for JSP
            req.setAttribute("administrators", administrators);
            req.setAttribute("airports", airports);

            // Forward to JSP
            req.getRequestDispatcher("add-aircraft.jsp").forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unable to load form data.");
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // Parse parameters from the form
            String aircraftModel = req.getParameter("aircraftModel");
            LocalDate manufactureDate = LocalDate.parse(req.getParameter("manufactureDate"));
            LocalDate lastMaintenanceDate = LocalDate.parse(req.getParameter("lastMaintenanceDate"));
            LocalDate nextMaintenanceDate = LocalDate.parse(req.getParameter("nextMaintenanceDate"));
            String aircraftNotes = req.getParameter("aircraftNotes");
            int administratorId = Integer.parseInt(req.getParameter("administratorId"));
            int airportId = Integer.parseInt(req.getParameter("airportId"));

            // Use AircraftBuilder to create the Aircraft object
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
                req.setAttribute("success", "Aircraft added successfully!");
            } else {
                req.setAttribute("error", "Failed to add aircraft.");
            }
        } catch (Exception e) {
            req.setAttribute("error", "Error adding aircraft: " + e.getMessage());
        }

        // Redirect back to the form with status messages
        doGet(req, resp);
    }
}

