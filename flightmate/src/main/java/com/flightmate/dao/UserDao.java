package com.flightmate.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.flightmate.beans.User;
import com.flightmate.libs.Role;
import com.flightmate.libs.builders.UserBuilder;
import com.flightmate.libs.services.SessionService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class UserDao {
	private static UserDao dao;
	public static final String USER_ID = "user_id";
	public static final String EMAIL_ADDRESS = "email_address";
	public static final String FIRST_NAME = "first_name";
	public static final String LAST_NAME = "last_name";
	private final String PASSWORD = "password";
	private final String UPDATED_AT = "updated_at";
	public final static String ROLE_ID = "role_id";
	
	private UserDao() {}
	
	public static synchronized UserDao getDao() {
		if (dao == null) dao = new UserDao();
		return dao;
	}
	
	public void createUser(HttpServletRequest req) {
		String email = req.getParameter("email");
		String firstName = req.getParameter("firstname");
		String lastName = req.getParameter("lastname");
		String password = req.getParameter("password");
		int roleId = Role.toRoleId(req.getParameter("role"));
		
		String sql = "INSERT INTO "+ApplicationDao.USERS_TABLE+" ("+EMAIL_ADDRESS+","+FIRST_NAME+","+LAST_NAME+","+ROLE_ID+","+PASSWORD+") VALUES (?, ?, ?, ?, ?)";
		
		try (
				Connection conn = DBConnection.getDBInstance();
				PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			) {
    		stmt.setString(1, email);
    		stmt.setString(2, firstName);
    		stmt.setString(3, lastName);
    		stmt.setInt(4, roleId);
    		stmt.setString(5, password);
    		
    		stmt.executeUpdate();
    		
    		ResultSet rs = stmt.getGeneratedKeys();
    		
    		if (rs != null && rs.next()) {
    			HttpSession session = req.getSession();
    			session.setAttribute("user", new UserBuilder()
    					.setUserId(rs.getInt(1))
    					.setEmail(email)
    					.setFirstName(firstName)
    					.setLastName(lastName)
    					.setRoleId(roleId)
    					.create());
    		}
    		
    		if (rs != null) rs.close();
			
		} catch (SQLException e) {
			DBUtil.processException(e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public boolean updateUser(String firstName, String lastName, String email, Role role, int userId) {
	    boolean updated = false;

	    String checkEmailSQL = "SELECT COUNT(*) FROM " + ApplicationDao.USERS_TABLE 
	                         + " WHERE " + EMAIL_ADDRESS + " = ? AND " + USER_ID + " != ?";
	    String updateSQL = "UPDATE " + ApplicationDao.USERS_TABLE + " SET "
	                     + FIRST_NAME + " = ?, "
	                     + LAST_NAME + " = ?, "
	                     + ROLE_ID + " = ?, " // Save role_id (1-based)
	                     + EMAIL_ADDRESS + " = ?, "
	                     + UPDATED_AT + " = CURRENT_TIMESTAMP() "
	                     + "WHERE " + USER_ID + " = ?";

	    try (Connection conn = DBConnection.getDBInstance();
	         PreparedStatement checkStmt = conn.prepareStatement(checkEmailSQL);
	         PreparedStatement updateStmt = conn.prepareStatement(updateSQL)) {

	        // Check for duplicate email
	        checkStmt.setString(1, email);
	        checkStmt.setInt(2, userId);
	        try (ResultSet rs = checkStmt.executeQuery()) {
	            if (rs.next() && rs.getInt(1) > 0) {
	                throw new SQLException("Duplicate email address: " + email);
	            }
	        }

	        // Convert Role enum to 1-based role_id for database
	        int roleId = role.ordinal() + 1;

	        // Proceed with the update
	        updateStmt.setString(1, firstName);
	        updateStmt.setString(2, lastName);
	        updateStmt.setInt(3, roleId); // Save role_id in database
	        updateStmt.setString(4, email);
	        updateStmt.setInt(5, userId);

	        updated = updateStmt.executeUpdate() > 0;

	    } catch (SQLException e) {
	        DBUtil.processException(e); // Log SQL error details
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    }

	    return updated;
	}



	
	public boolean updatePassword(int userId, String newPass) {
		boolean updated = false;
		String sql = "UPDATE "+ApplicationDao.USERS_TABLE+" SET "
				+PASSWORD+" = ?, "
				+UPDATED_AT+" = CURRENT_TIMESTAMP()"
				+ " WHERE "+USER_ID+" = ?";
		
		try (
				Connection conn = DBConnection.getDBInstance();
				PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			) {
    		stmt.setString(1, newPass);
    		stmt.setInt(2, userId);
    		
    		updated = stmt.executeUpdate() > 0;
			
		} catch (SQLException e) {
			DBUtil.processException(e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return updated;
	}
	
	public User getUserById(int userId) {
		User user = null;
		String sql = "SELECT "+USER_ID+", "+EMAIL_ADDRESS+", "+FIRST_NAME+", "+LAST_NAME+", "+ROLE_ID+" FROM " +ApplicationDao.USERS_TABLE+" WHERE " + USER_ID + " = ?";
		try (
				Connection conn = DBConnection.getDBInstance();
				PreparedStatement stmt = conn.prepareStatement(sql);
			) {
			stmt.setInt(1, userId);
			ResultSet rs = stmt.executeQuery();
			
			if (rs != null && rs.next()) {
				user = new UserBuilder()
						.setUserId(userId)
						.setEmail(rs.getString(EMAIL_ADDRESS))
						.setFirstName(rs.getString(FIRST_NAME))
						.setLastName(rs.getString(LAST_NAME))
						.setRoleId(rs.getInt(ROLE_ID))
						.create();
				}
						
			if (rs != null) rs.close();
		} catch (SQLException e) {
			DBUtil.processException(e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			
		}
		return user;
	}
	
	public void authenticateUser(HttpServletRequest req) {
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		
		String sql = "SELECT "+USER_ID+", "+FIRST_NAME+", "+LAST_NAME+", "+ROLE_ID+" FROM "+ApplicationDao.USERS_TABLE+" WHERE "+EMAIL_ADDRESS+" = ? AND "+PASSWORD+" = ?";
		
		try (
				Connection conn = DBConnection.getDBInstance();
				PreparedStatement stmt = conn.prepareStatement(sql);
			) {
			stmt.setString(1, email);
			stmt.setString(2, password);
			
			ResultSet rs = stmt.executeQuery();
			
			if (rs != null && rs.next()) {
				
				User user = new UserBuilder()
						.setUserId(rs.getInt(USER_ID))
						.setEmail(email)
						.setFirstName(rs.getString(FIRST_NAME))
						.setLastName(rs.getString(LAST_NAME))
						.setRoleId(rs.getInt(ROLE_ID))
						.create();
				
				SessionService.srv.setSessionUser(req, user);
			}
			
			if (rs != null) rs.close();
			
		} catch (SQLException e) {
			DBUtil.processException(e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public boolean userExists(String email) {
		boolean isTrue = false;
		
		String sql = "SELECT COUNT(*) FROM "+ApplicationDao.USERS_TABLE+" WHERE "+EMAIL_ADDRESS+" = ?";
		
		try (
				Connection conn = DBConnection.getDBInstance();
				PreparedStatement stmt = conn.prepareStatement(sql);
			) {
			stmt.setString(1, email);
			
			ResultSet rs = stmt.executeQuery();
			if (rs != null && rs.next() && rs.getInt(1) > 0) {
				isTrue = true;
			}
			
			if (rs != null) rs.close();
			
		} catch (SQLException e) {
			DBUtil.processException(e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return isTrue;
	}
	
	public boolean passwordMatches(String email, String password) {
		boolean isTrue = false;
		
		String sql = "SELECT COUNT(*) FROM "+ApplicationDao.USERS_TABLE+" WHERE "+EMAIL_ADDRESS+" = ? AND "+PASSWORD+" = ?";
		
		try (
				Connection conn = DBConnection.getDBInstance();
				PreparedStatement stmt = conn.prepareStatement(sql);
			) {
			stmt.setString(1, email);
			stmt.setString(2, password);
			
			ResultSet rs = stmt.executeQuery();
			if (rs != null && rs.next() && rs.getInt(1) > 0) {
				isTrue = true;
			}
			
			
			if (rs != null) rs.close();
			
		} catch (SQLException e) {
			DBUtil.processException(e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return isTrue;
	}

	public List<User> getAllUsers() {
		    List<User> users = new ArrayList<>();
		    String sql = "SELECT " + USER_ID + ", " + EMAIL_ADDRESS + ", " + FIRST_NAME + ", " + LAST_NAME + ", " + ROLE_ID + " FROM " + ApplicationDao.USERS_TABLE;
		    
		    try (
		        Connection conn = DBConnection.getDBInstance();
		        PreparedStatement stmt = conn.prepareStatement(sql);
		        ResultSet rs = stmt.executeQuery();
		    ) {
		        while (rs.next()) {
		            User user = new UserBuilder()
		                .setUserId(rs.getInt(USER_ID))
		                .setEmail(rs.getString(EMAIL_ADDRESS))
		                .setFirstName(rs.getString(FIRST_NAME))
		                .setLastName(rs.getString(LAST_NAME))
		                .setRoleId(rs.getInt(ROLE_ID))
		                .create();
		            users.add(user);
		        }
		    } catch (SQLException e) {
		        DBUtil.processException(e);
		    } catch (ClassNotFoundException e) {
		        e.printStackTrace();
		    }
		    
		    return users;
		}
	public boolean deleteUser(int userId) {
	    boolean deleted = false;
	    String sql = "DELETE FROM users WHERE user_id = ?";

	    try (
	        Connection conn = DBConnection.getDBInstance();
	        PreparedStatement stmt = conn.prepareStatement(sql);
	    ) {
	        stmt.setInt(1, userId);

	        deleted = stmt.executeUpdate() > 0; // Returns true if the deletion was successful
	    } catch (SQLException e) {
	        DBUtil.processException(e);
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    }
	    return deleted;
	}
	
    public List<User> getAllAdministrators() {
        //Fetching users with ADMINISTRATOR role
        List<User> administrators = new ArrayList<>();
        String sql = "SELECT user_id, first_name, last_name, email_address, role_id FROM Users WHERE role_id = 2";
        try (Connection conn = DBConnection.getDBInstance();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                administrators.add(new User(
                    rs.getInt(USER_ID),
                    rs.getString(EMAIL_ADDRESS),
                    rs.getString(FIRST_NAME),
                    rs.getString(LAST_NAME),
                    rs.getInt(ROLE_ID) 
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return administrators;
    }

    public List<User> getAllPilots() {
        List<User> pilots = new ArrayList<>();
        String sql = "SELECT user_id, first_name, last_name, email_address, role_id FROM users WHERE role_id = 1";

        try (Connection conn = DBConnection.getDBInstance();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                pilots.add(new User(
                    rs.getInt("user_id"),
                    rs.getString("email_address"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getInt("role_id")
                ));
            }
        } catch (SQLException e) {
            DBUtil.processException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return pilots;
    }

	
}