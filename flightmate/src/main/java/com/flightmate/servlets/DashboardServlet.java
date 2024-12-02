package com.flightmate.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.flightmate.beans.FlightHour;
import com.flightmate.beans.User;
import com.flightmate.dao.PilotHoursDao;
import com.flightmate.dao.FlightDao;
import com.flightmate.dao.UserDao;
import com.flightmate.libs.Role;
import com.flightmate.libs.Route;
import com.flightmate.libs.services.SessionService;


@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = SessionService.srv.getSessionUser(req);

        if (user == null) {
            resp.sendRedirect(Route.LOGIN);
            return;
        }

        String action = req.getParameter("action");
        if (action != null && user.getRole().equals(Role.ADMINISTRATOR)) {
            try {
                switch (action) {
                    case "edit":
                        handleEdit(req, resp);
                        return;
                    case "delete":
                        handleDelete(req);
                        break;
                    default:
                        req.setAttribute("error", "Invalid action.");
                }
            } catch (Exception e) {
                e.printStackTrace();
                req.setAttribute("error", "Error processing action: " + e.getMessage());
            }
        }

        // Fetch all users for admin role
        req.setAttribute("users", UserDao.getDao().getAllUsers());

        // Fetch pending flight hours for admin role
        if (user.getRole().equals(Role.ADMINISTRATOR)) {
            try {
                List<FlightHour> pendingHours = PilotHoursDao.getDao().getPendingFlightHours();
                req.setAttribute("pendingFlightHours", pendingHours);
            } catch (Exception e) {
                e.printStackTrace();
                req.setAttribute("error", "Failed to retrieve pending flight hours.");
            }
        
		req.setAttribute("users", UserDao.getDao().getAllUsers()); 
        req.setAttribute("flights", FlightDao.getDao().getAllFlights());
						
        }
        
		req.getRequestDispatcher(Route.DASHBOARD).forward(req, resp);
    }
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User user = SessionService.srv.getSessionUser(req);
		
		if (user == null) {
			resp.sendRedirect(Route.LOGIN);
			return;
		}
		
		String action = req.getParameter("action");
		if ("save".equals(action)) {
            handleSave(req, resp);
            
        }

		 doGet(req, resp);
    }

    private void handleEdit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userIdParam = req.getParameter("id");
        if (userIdParam == null || userIdParam.isEmpty()) {
            req.setAttribute("error", "User ID is required for editing.");
            return;
        }

        try {
            int userId = Integer.parseInt(userIdParam);

            User user = UserDao.getDao().getUserById(userId);
            if (user == null) {
                req.setAttribute("error", "User not found.");
                return;
            }

            req.setAttribute("user", user);
            req.getRequestDispatcher("edit_user.jsp").forward(req, resp);
        } catch (NumberFormatException e) {
            req.setAttribute("error", "Invalid User ID format.");
        }
    }

    private void handleDelete(HttpServletRequest req) {
        String userIdParam = req.getParameter("id");
        if (userIdParam == null || userIdParam.isEmpty()) {
            req.setAttribute("error", "User ID is required for deletion.");
            return;
        }

        try {
            int userId = Integer.parseInt(userIdParam);

            boolean deleted = UserDao.getDao().deleteUser(userId);

            if (deleted) {
                req.setAttribute("success", "User deleted successfully.");
            } else {
                req.setAttribute("error", "Failed to delete user.");
            }
        } catch (NumberFormatException e) {
            req.setAttribute("error", "Invalid User ID format.");
        }
    }

    private void handleSave(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userIdParam = req.getParameter("user_id");
        String firstName = req.getParameter("first_name");
        String lastName = req.getParameter("last_name");
        String email = req.getParameter("email_address");
        String roleIdParam = req.getParameter("role_id");

        try {
            int userId = Integer.parseInt(userIdParam);
            int roleId = Integer.parseInt(roleIdParam);

            boolean updated = UserDao.getDao().updateUser(firstName, lastName, email, roleId);

            if (updated) {
                req.setAttribute("success", "User updated successfully.");
            } else {
                req.setAttribute("error", "Failed to update user.");
            }
        } catch (NumberFormatException e) {
            req.setAttribute("error", "Invalid input for User ID or Role ID.");
        }
    }
}
