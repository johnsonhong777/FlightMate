package com.flightmate.servlets;

import com.flightmate.beans.Airport;
import com.flightmate.beans.Flight;
import com.flightmate.beans.User;
import com.flightmate.dao.AirportDao;
import com.flightmate.dao.FlightDao;
import com.flightmate.dao.UserDao;
import com.flightmate.libs.Route;
import com.flightmate.libs.builders.FlightBuilder;
import com.flightmate.libs.services.SessionService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet("/flight")
public class FlightServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = SessionService.srv.getSessionUser(req);

        if (user == null) {
            resp.sendRedirect(Route.LOGIN);
            return;
        }

        // Fetch necessary data for the form
        List<Airport> airports = AirportDao.getDao().getAllAirports();
        List<User> pilots = UserDao.getDao().getAllPilots(); // Fetch only users with role 'PILOT'

        req.setAttribute("airports", airports);
        req.setAttribute("pilots", pilots);

        String successMessage = (String) req.getSession().getAttribute("success");
        String errorMessage = (String) req.getSession().getAttribute("error");

        if (successMessage != null) {
            req.setAttribute("success", successMessage);
            req.getSession().removeAttribute("success");
        }

        if (errorMessage != null) {
            req.setAttribute("error", errorMessage);
            req.getSession().removeAttribute("error");
        }

        // Proceed to render the form
        req.getRequestDispatcher(Route.FLIGHT).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = SessionService.srv.getSessionUser(req);

        if (user == null) {
            resp.sendRedirect(Route.LOGIN);
            return;
        }

        try {
            // Gather form data
            String flightNumber = req.getParameter("flightNumber");
            String origin = req.getParameter("origin");
            String destination = req.getParameter("destination");
            String departureTimeStr = req.getParameter("departureTime");
            String arrivalTimeStr = req.getParameter("arrivalTime");
            String status = req.getParameter("status");

            // Validate date/time inputs
            LocalDateTime departureTime = LocalDateTime.parse(departureTimeStr);
            LocalDateTime arrivalTime = LocalDateTime.parse(arrivalTimeStr);

            // Process multi-select fields for pilots and airport stops
            String[] pilotIds = req.getParameterValues("pilots");
            String[] airportStopIds = req.getParameterValues("airportStops");

            // Create a Flight object
            Flight flight = new FlightBuilder()
                    .setFlightNumber(flightNumber)
                    .setOrigin(origin)
                    .setDestination(destination)
                    .setDepartureTime(departureTime)
                    .setArrivalTime(arrivalTime)
                    .setStatus(status)
                    .setCreatedAt(LocalDateTime.now())
                    .create();

            // Save the flight to the database
            int flightId = FlightDao.getDao().addFlight(flight);

            // Link pilots to the flight
            if (flightId > 0 && pilotIds != null) {
                for (String pilotId : pilotIds) {
                    int pilotIdInt = Integer.parseInt(pilotId);
                    FlightDao.getDao().addPilotToFlight(flightId, pilotIdInt);
                }
            }

            // Link airport stops to the flight
            if (flightId > 0 && airportStopIds != null) {
                for (String airportId : airportStopIds) {
                    int airportIdInt = Integer.parseInt(airportId);
                    FlightDao.getDao().addAirportStopToFlight(flightId, airportIdInt);
                }
            }

       


            // Store success message in session and redirect
            req.getSession().setAttribute("success", "Flight added successfully!");
            resp.sendRedirect(Route.FLIGHT);  // Redirect to another page, such as flight list

        } catch (Exception e) {
            e.printStackTrace();
            req.getSession().setAttribute("error", "Failed to add flight: " + e.getMessage());
            resp.sendRedirect(Route.FLIGHT);  // Redirect to handle errors too
        }
    }
}

