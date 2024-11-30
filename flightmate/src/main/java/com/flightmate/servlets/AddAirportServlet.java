package com.flightmate.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.flightmate.beans.Airport;
import com.flightmate.dao.AirportDao;



@WebServlet("/AddAirportServlet")
public class AddAirportServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Get form data
        String airportName = request.getParameter("airportName").trim();
        String airportCode = request.getParameter("airportCode").trim();
        String city = request.getParameter("city").trim();
        String country = request.getParameter("country").trim();
        String runwaysStr = request.getParameter("runways").trim();

        // Validate input
        if (airportName.isEmpty() || airportCode.isEmpty() || city.isEmpty() || 
            country.isEmpty() || runwaysStr.isEmpty()) {
            response.getWriter().write("Error: All fields are required.");
            return;
        }

        if (!airportCode.matches("[A-Z]{3}")) {
            response.getWriter().write("Error: Airport Code must be exactly 3 uppercase letters.");
            return;
        }

        int runways;
        try {
            runways = Integer.parseInt(runwaysStr);
            if (runways <= 0) {
                response.getWriter().write("Error: Number of Runways must be a positive number.");
                return;
            }
        } catch (NumberFormatException e) {
            response.getWriter().write("Error: Invalid number of runways.");
            return;
        }       
        
        
     // Create an airport object
        Airport airport = new Airport(0, airportName, airportCode, city, country, runways);
        AirportDao dao = AirportDao.getDao();
        
        try {
            dao.createAirport(airport);
            response.sendRedirect("listairports.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("Error: " + e.getMessage());
        }        
        
    }
}