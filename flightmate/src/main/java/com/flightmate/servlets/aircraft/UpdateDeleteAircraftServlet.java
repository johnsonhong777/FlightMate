package com.flightmate.servlets.aircraft;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.logging.Logger;

import com.flightmate.beans.Aircraft;
import com.flightmate.dao.AircraftDao;

@WebServlet("/UpdateDeleteAircraftServlet")
public class UpdateDeleteAircraftServlet extends HttpServlet {

@Override
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String action = request.getParameter("action");
    int id = Integer.parseInt(request.getParameter("id"));
    
    AircraftDao dao = AircraftDao.getDao();

    if ("Update".equals(action)) {
        // Get form parameters
        String model = request.getParameter("aircraft_model");
        String manufactureDateStr = request.getParameter("manufacture_date");
        String lastMaintenanceDateStr = request.getParameter("last_maintenance_date");
        String nextMaintenanceDateStr = request.getParameter("next_maintenance_date");
        String aircraftNotes = request.getParameter("aircraft_notes");
        String administratorIdStr = request.getParameter("administratorId");
        String airportIdStr = request.getParameter("airportId");
        System.out.println("airportId: " + airportIdStr);  // Log the value for debugging
        // Log the form data for debugging purposes
        System.out.println("Received data for update:");
        System.out.println("Model: " + model);
        System.out.println("Manufacture Date: " + manufactureDateStr);
        System.out.println("Last Maintenance Date: " + lastMaintenanceDateStr);
        System.out.println("Next Maintenance Date: " + nextMaintenanceDateStr);
        System.out.println("Administrator ID: " + administratorIdStr);
        System.out.println("Airport ID: " + airportIdStr);

        // Check if administratorId is null or empty
        if (administratorIdStr == null || administratorIdStr.trim().isEmpty()) {
            request.getSession().setAttribute("error", "Administrator ID cannot be empty.");
            response.sendRedirect(request.getContextPath() + "/aircraft");
            return;
        }

        try {
            int administratorId = Integer.parseInt(administratorIdStr); // Parse Administrator ID
            int airportId = Integer.parseInt(airportIdStr); // Parse Airport ID

            // Convert string date parameters to LocalDate
            java.time.LocalDate manufactureDate = java.time.LocalDate.parse(manufactureDateStr);
            java.time.LocalDate lastMaintenanceDate = java.time.LocalDate.parse(lastMaintenanceDateStr);
            java.time.LocalDate nextMaintenanceDate = java.time.LocalDate.parse(nextMaintenanceDateStr);

            // Create the Aircraft object
            Aircraft aircraft = new Aircraft(id, model, manufactureDate, lastMaintenanceDate, nextMaintenanceDate, aircraftNotes, java.time.LocalDateTime.now(), administratorId, airportId);

            // Log aircraft object before update
            System.out.println("Aircraft object to update: " + aircraft);

            // Update the aircraft in the database
            boolean success = dao.updateAircraft(aircraft);

            if (success) {
                request.getSession().setAttribute("success", "Aircraft updated successfully.");
            } else {
                request.getSession().setAttribute("error", "Failed to update aircraft.");
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            request.getSession().setAttribute("error", "Invalid data: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("error", "Failed to update aircraft: " + e.getMessage());
        }
    } else if ("Delete".equals(action)) {
        try {
            // Delete the aircraft by id
            boolean success = dao.deleteAircraft(id);

            if (success) {
                request.getSession().setAttribute("success", "Aircraft deleted successfully.");
            } else {
                request.getSession().setAttribute("error", "Failed to delete aircraft.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("error", "Failed to delete aircraft: " + e.getMessage());
        }
    }

    // Redirect after POST to avoid form resubmission
    response.sendRedirect(request.getContextPath() + "/aircraft");
}}