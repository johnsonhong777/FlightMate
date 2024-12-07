package com.flightmate.servlets.pages;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.flightmate.beans.Feedback;
import com.flightmate.beans.User;
import com.flightmate.dao.FeedbackDao;
import com.flightmate.dao.UserDao;
import com.flightmate.libs.Role;
import com.flightmate.libs.Route;
import com.flightmate.libs.services.SessionService;

@WebServlet("/user-management")
public class UserManagementServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User user = SessionService.srv.getSessionUser(req);

        if (user == null) {
            resp.sendRedirect("./login");
            return;
        }
        
        if (!user.getRole().equals(Role.ADMINISTRATOR)) {
        	resp.sendRedirect("./dashboard");
        }
        
        String action = req.getParameter("action");
	    if (action != null) {
	        try {
	            switch (action) {
	                case "edit":
	                    handleEdit(req, resp);
	                    return; // Ensure no further actions are taken
	                case "delete":
	                    handleDelete(req, resp);
	                    return; // Ensure no further actions are taken
	                default:
	                    req.setAttribute("error", "Invalid action.");
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            req.setAttribute("error", "Error processing action: " + e.getMessage());
	        }
	    }
        
        List<User> userList = UserDao.getDao().getAllUsers();
        req.setAttribute("users", userList);
        
        
        // Generate feedback list based on filter param
        List<Feedback> feedbackList = FeedbackDao.getDao().getAllFeedback();
        String filterType = req.getParameter("filterType") != null ? req.getParameter("filterType") : "all";
        List<Feedback> filteredFeedback = feedbackList;
        if (filterType.equals("read")) {
        	filteredFeedback = feedbackList.stream().filter(Feedback::hasRead).collect(Collectors.toList());
        } else if (filterType.equals("unread")) {
        	filteredFeedback = feedbackList.stream().filter(feedback -> !feedback.hasRead()).collect(Collectors.toList());
        }
        
        req.setAttribute("feedback", filteredFeedback);
        
        req.getRequestDispatcher(Route.USER_MANAGEMENT).forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User user = SessionService.srv.getSessionUser(req);

        if (user == null) {
            resp.sendRedirect("./login");
            return;
        }
        
        if (!user.getRole().equals(Role.ADMINISTRATOR)) {
        	resp.sendRedirect("./dashboard");
        }
        
        String action = req.getParameter("action");
	    if ("save".equals(action)) {
	        handleSave(req, resp);
	        return; // Ensure no further actions are taken
	    }
	    
	    
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

		private void handleDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		    String userIdParam = req.getParameter("id");
		    if (userIdParam == null || userIdParam.isEmpty()) {
		        req.getSession().setAttribute("error", "User ID is required for deletion.");
		        resp.sendRedirect(req.getContextPath() + "/dashboard");
		        return; // Ensure no further actions are taken
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
		    } catch (Exception e) {
		        e.printStackTrace();
		        req.setAttribute("error", "Unexpected error: " + e.getMessage());
		    }
	    }

		private void handleSave(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		    String userIdParam = req.getParameter("user_id");
		    String firstName = req.getParameter("first_name");
		    String lastName = req.getParameter("last_name");
		    String email = req.getParameter("email_address");
		    String roleIdParam = req.getParameter("role_id");

		    try {
		        // Parse user ID and role ID
		        int userId = Integer.parseInt(userIdParam); // Parse user_id from form
		        int roleId = Integer.parseInt(roleIdParam); // Parse 1-based role_id from form
		        Role[] roles = Role.values();

		        // Convert 1-based role_id to 0-based index
		        int roleIndex = roleId - 1;

		        // Validate the role ID
		        if (roleIndex < 0 || roleIndex >= roles.length) {
		            req.getSession().setAttribute("error", "Invalid role selected.");
		            resp.sendRedirect(req.getContextPath() + "/dashboard"); // Redirect back to the dashboard
		            return;
		        }

		        Role role = roles[roleIndex]; // Get the corresponding Role enum

		        // Fetch the user by ID
		        User existingUser = UserDao.getDao().getUserById(userId);
		        if (existingUser == null) {
		            req.getSession().setAttribute("error", "User not found.");
		            resp.sendRedirect(req.getContextPath() + "/dashboard"); // Redirect back to the dashboard
		            return;
		        }

		        // Proceed with updating the user
		        boolean updated = UserDao.getDao().updateUser(firstName, lastName, email, role, userId);
		        if (updated) {
		            req.getSession().setAttribute("success", "User updated successfully.");
		        } else {
		            req.getSession().setAttribute("error", "Failed to update user.");
		        }

		    } catch (NumberFormatException e) {
		        req.getSession().setAttribute("error", "Invalid format for User ID or Role ID.");
		    } catch (Exception e) {
		        e.printStackTrace();
		        req.getSession().setAttribute("error", "Unexpected error: " + e.getMessage());
		    }

		    // Redirect back to the dashboard page
		    resp.sendRedirect(req.getContextPath() + "/dashboard");
		}

}
