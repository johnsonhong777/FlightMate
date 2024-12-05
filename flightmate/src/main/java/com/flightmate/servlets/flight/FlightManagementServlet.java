package com.flightmate.servlets.flight;

import com.flightmate.beans.Flight;
import com.flightmate.dao.FlightDao;
import com.flightmate.libs.Route;
import com.flightmate.libs.services.SessionService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet("/flight-management")
public class FlightManagementServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String action = req.getParameter("action");

            if ("create".equalsIgnoreCase(action)) {
                // Redirect to add_flight.jsp for creating a new flight
                req.getRequestDispatcher("add_flight.jsp").forward(req, resp);
                return;
            } else if ("edit".equalsIgnoreCase(action)) {
                handleEdit(req, resp);
                return; // Stop further processing
            } else if ("delete".equalsIgnoreCase(action)) {
                handleDelete(req, resp);
                return; // Stop further processing
            }

            // Default action: Display flight list
            List<Flight> flightList = FlightDao.getDao().getAllFlights();
            req.setAttribute("flights", flightList);

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "Failed to fetch flight list: " + e.getMessage());
        }

        req.getRequestDispatcher(Route.FLIGHT_MANAGEMENT).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("save".equalsIgnoreCase(action)) {
            handleSave(req, resp);
            return; // Stop further processing
        }

        doGet(req, resp); // Delegate to doGet for any other actions
    }

    private void handleEdit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String flightIdParam = req.getParameter("id");
        try {
            int flightId = Integer.parseInt(flightIdParam);
            Flight flight = FlightDao.getDao().getFlightById(flightId);

            if (flight == null) {
                req.setAttribute("error", "Flight not found.");
                return;
            }

            req.setAttribute("flight", flight);
            req.getRequestDispatcher("edit_flight.jsp").forward(req, resp);
        } catch (NumberFormatException e) {
            req.setAttribute("error", "Invalid flight ID format.");
        }
    }

    private void handleDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String flightIdParam = req.getParameter("id");
        try {
            int flightId = Integer.parseInt(flightIdParam);
            boolean deleted = FlightDao.getDao().deleteFlight(flightId);

            if (deleted) {
                req.getSession().setAttribute("success", "Flight deleted successfully.");
            } else {
                req.getSession().setAttribute("error", "Failed to delete flight.");
            }
        } catch (NumberFormatException e) {
            req.getSession().setAttribute("error", "Invalid flight ID format.");
        }
        resp.sendRedirect(req.getContextPath() + "/flight-management");
    }

    private void handleSave(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String flightIdParam = req.getParameter("flight_id");
        String flightNumber = req.getParameter("flight_number");
        String origin = req.getParameter("origin");
        String destination = req.getParameter("destination");
        String status = req.getParameter("status");
        String departureTime = req.getParameter("departure_time");
        String arrivalTime = req.getParameter("arrival_time");

        try {
            LocalDateTime departure = LocalDateTime.parse(departureTime); // Parse LocalDateTime
            LocalDateTime arrival = LocalDateTime.parse(arrivalTime); // Parse LocalDateTime

            Flight flight = new Flight();
            flight.setFlightNumber(flightNumber);
            flight.setOrigin(origin);
            flight.setDestination(destination);
            flight.setStatus(status);
            flight.setDepartureTime(departure);
            flight.setArrivalTime(arrival);

            if (flightIdParam != null && !flightIdParam.isEmpty()) {
                flight.setFlightId(Integer.parseInt(flightIdParam));
                FlightDao.getDao().updateFlight(flight);
                req.getSession().setAttribute("success", "Flight updated successfully.");
            } else {
                FlightDao.getDao().addFlight(flight);
                req.getSession().setAttribute("success", "Flight added successfully.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            req.getSession().setAttribute("error", "Failed to save flight: " + e.getMessage());
        }
        resp.sendRedirect(req.getContextPath() + "/flight-management");
    }
}
