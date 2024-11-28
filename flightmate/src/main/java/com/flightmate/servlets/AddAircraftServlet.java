package com.flightmate.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.flightmate.beans.Aircraft;
import com.flightmate.beans.User;
import com.flightmate.dao.AircraftDao;
import com.flightmate.dao.AirportDao; // For fetching airports
import com.flightmate.libs.Role;
import com.flightmate.libs.services.SessionService;

@WebServlet("/add-aircraft")
public class AddAircraftServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = SessionService.srv.getSessionUser(req);
        
        if (user == null || user.getRole() != Role.ADMINISTRATOR) {
            resp.sendRedirect("./login");
            return;
        } 

        req.setAttribute("airports", AirportDao.getDao().getAllAirports());
        req.getRequestDispatcher("add_aircraft.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = SessionService.srv.getSessionUser(req);

        if (user == null || user.getRole() != Role.ADMINISTRATOR) {
            resp.sendRedirect("./login");
            return;
        }

        String model = req.getParameter("model");
        String details = req.getParameter("details");
        int airportId = Integer.parseInt(req.getParameter("airport"));

        Aircraft aircraft = new Aircraft(0, model, details, airportId);
        boolean success = AircraftDao.getDao().addAircraft(aircraft);

        if (success) {
            req.setAttribute("success", "Aircraft added successfully!");
        } else {
            req.setAttribute("message", "Failed to add aircraft. Please try again.");
        }

        doGet(req, resp);
    }
}
