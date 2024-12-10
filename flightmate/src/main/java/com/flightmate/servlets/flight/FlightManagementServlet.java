package com.flightmate.servlets.flight;

import com.flightmate.beans.Airport;
import com.flightmate.beans.Flight;
import com.flightmate.beans.User;
import com.flightmate.dao.AirportDao;
import com.flightmate.dao.FlightDao;
import com.flightmate.dao.UserDao;
import com.flightmate.libs.Route;
import com.flightmate.libs.services.SessionService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
            List<Airport> airports = AirportDao.getDao().getAllAirports();
            List<User> pilots = UserDao.getDao().getAllPilots(); // Fetch only users with role 'PILOT'

            req.setAttribute("airports", airports);
            req.setAttribute("pilots", pilots);
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
        String departureTime = req.getParameter("departureTime");
        String arrivalTime = req.getParameter("arrivalTime");

        // Debugging: log all the received parameters
        System.out.println("Received parameters:");
        System.out.println("flightIdParam: " + flightIdParam);
        System.out.println("flightNumber: " + flightNumber);
        System.out.println("origin: " + origin);
        System.out.println("destination: " + destination);
        System.out.println("status: " + status);
        System.out.println("departureTime: " + departureTime);
        System.out.println("arrivalTime: " + arrivalTime);

        try {
            // Check if the datetime fields are null or empty
            if (departureTime == null || departureTime.isEmpty() || arrivalTime == null || arrivalTime.isEmpty()) {
                System.out.println("Error: Departure or Arrival time is missing.");
                req.getSession().setAttribute("error", "Both departure and arrival times must be provided.");
                resp.sendRedirect(req.getContextPath() + "/flight-management");
                return;  // Prevent further execution
            }

            // Debugging: log the departure and arrival time before parsing
            System.out.println("Parsing times...");
            System.out.println("Departure Time (String): " + departureTime);
            System.out.println("Arrival Time (String): " + arrivalTime);

            // Parse LocalDateTime
            LocalDateTime departure = LocalDateTime.parse(departureTime); // Parse LocalDateTime
            LocalDateTime arrival = LocalDateTime.parse(arrivalTime); // Parse LocalDateTime

            // Debugging: log the parsed LocalDateTime objects
            System.out.println("Parsed Departure Time: " + departure);
            System.out.println("Parsed Arrival Time: " + arrival);

            // Validate the flightNumber before continuing
            if (flightNumber == null || flightNumber.trim().isEmpty()) {
                System.out.println("Error: Flight number is missing.");
                req.getSession().setAttribute("error", "Flight number is required.");
                resp.sendRedirect(req.getContextPath() + "/flight-management");
                return;  // Prevent further execution
            }

            // Debugging: log before creating the Flight object
            System.out.println("Creating Flight object...");
            Flight flight = new Flight();
            flight.setFlightNumber(flightNumber);
            flight.setOrigin(origin);
            flight.setDestination(destination);
            flight.setStatus(status);
            flight.setDepartureTime(departure);
            flight.setArrivalTime(arrival);

            // Debugging: log the Flight object creation
            System.out.println("Flight object created: " + flight);

            if (flightIdParam != null && !flightIdParam.isEmpty()) {
                // If flightId is provided, update the flight
                flight.setFlightId(Integer.parseInt(flightIdParam));
                System.out.println("Updating flight with ID: " + flight.getFlightId());
                FlightDao.getDao().updateFlight(flight);
                req.getSession().setAttribute("success", "Flight updated successfully.");
            } else {
                // If flightId is not provided, create a new flight
                System.out.println("Adding new flight.");
                FlightDao.getDao().addFlight(flight);
                req.getSession().setAttribute("success", "Flight added successfully.");
            }

        } catch (Exception e) {
            // Exception handling with debug output
            System.out.println("Error during flight save:");
            e.printStackTrace();
            req.getSession().setAttribute("error", "Failed to save flight: " + e.getMessage());
        }

        // Debugging: log the redirect action
        System.out.println("Redirecting to /flight-management");
        resp.sendRedirect(req.getContextPath() + "/flight-management");
    }
}
