package com.flightmate.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.flightmate.beans.Airport;
import com.flightmate.beans.User;
import com.flightmate.dao.AirportDao;
import com.flightmate.libs.Route;
import com.flightmate.libs.services.SessionService;

@WebServlet("/airport")
public class AirportServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User user = SessionService.srv.getSessionUser(req);
		
		if (user == null) {
			resp.sendRedirect(Route.LOGIN);
			return;
		}
		
		AirportDao dao = AirportDao.getDao();
        List<Airport> airports = dao.getAllAirports();
        
        req.setAttribute("airports", airports);
		
		
						
		req.getRequestDispatcher(Route.AIRPORT).forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User user = SessionService.srv.getSessionUser(req);
		
		if (user == null) {
			resp.sendRedirect(Route.LOGIN);
			return;
		}
		
		doGet(req, resp);
	}

}
