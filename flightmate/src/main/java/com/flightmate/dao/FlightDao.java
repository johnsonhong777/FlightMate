package com.flightmate.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FlightDao {
    public void updateFlightStatus(int flightId, String newStatus) throws SQLException, ClassNotFoundException {
        String query = "UPDATE flights SET status = ? WHERE id = ?";
        try (Connection conn = DBConnection.getDBInstance();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, newStatus);
            stmt.setInt(2, flightId);
            stmt.executeUpdate();
        }
    }
    
    public class UpdateFlightStatus {
        public static void main(String[] args) {
            FlightDao flightDao = new FlightDao();

            try {
                int flightId = 1; 
                String newStatus = "Departed"; 

                flightDao.updateFlightStatus(flightId, newStatus);
                System.out.println("Flight status updated successfully!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}