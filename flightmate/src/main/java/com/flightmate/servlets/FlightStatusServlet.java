package com.flightmate.servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.flightmate.dao.FlightDao;


/**
 * Servlet implementation class FlightStatusServlet
 */
@WebServlet("/updateFlightStatus")
public class FlightStatusServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		response.getWriter().append("Served at: ").append(request.getContextPath());
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Get flight ID and status from request.. 
		int flightId = Integer.parseInt(request.getParameter("flightId"));
		String newStatus = request.getParameter("status");
		
		try {
			FlightDao.getDao().updateFlightStatus(flightId, newStatus);
			response.sendRedirect("flights.jsp"); // redirects to show update.. 
		}
		catch (SQLException | ClassNotFoundException e) {
		    e.printStackTrace();
		    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Flight status could not be updated");
		}

	}

}
