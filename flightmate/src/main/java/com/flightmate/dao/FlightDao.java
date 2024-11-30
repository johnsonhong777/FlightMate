package com.flightmate.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FlightDao {
	private static FlightDao dao;
	
	private FlightDao() {}
	
	public static synchronized FlightDao getDao() {
		if (dao == null) dao = new FlightDao();
		return dao;
	}
	
    public void updateFlightStatus(int flightId, String newStatus) throws SQLException, ClassNotFoundException {
        String query = "UPDATE flights SET status = ? WHERE id = ?";
        try (Connection conn = DBConnection.getDBInstance();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, newStatus);
            stmt.setInt(2, flightId);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void getAllFlights() {
		// TODO Auto-generated method stub
	}
}