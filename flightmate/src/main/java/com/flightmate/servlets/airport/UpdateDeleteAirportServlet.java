package com.flightmate.servlets.airport;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.flightmate.beans.Airport;
import com.flightmate.dao.AirportDao;

@WebServlet("/UpdateDeleteAirportServlet")
public class UpdateDeleteAirportServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        int id = Integer.parseInt(request.getParameter("id"));

        AirportDao dao = AirportDao.getDao();

        if ("Update".equals(action)) {
            String name = request.getParameter("airport_name");
            String code = request.getParameter("airport_code");
            String city = request.getParameter("city");
            String country = request.getParameter("country");
            int runways = Integer.parseInt(request.getParameter("runways"));

            Airport airport = new Airport(id, name, code, city, country, runways, null);
            try {
                dao.updateAirport(airport);
            } catch (Exception e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unable to update airport.");
            }
        } else if ("Delete".equals(action)) {
            try {
                dao.deleteAirport(id);
            } catch (Exception e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unable to delete airport.");
            }
        }

        response.sendRedirect("./airport");
    }
}