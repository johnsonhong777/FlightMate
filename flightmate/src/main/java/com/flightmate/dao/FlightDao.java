package com.flightmate.dao;

import com.flightmate.beans.Flight;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public List<Flight> getAllFlights() {
        List<Flight> flights = new ArrayList<>();
        String sql = "SELECT * FROM flights";
        try (Connection conn = DBConnection.getDBInstance();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Flight flight = new Flight();
                flight.setFlightNumber(rs.getString("flight_number"));
                flight.setDepartureTime(rs.getTimestamp("departure_time").toLocalDateTime());
                flight.setArrivalTime(rs.getTimestamp("arrival_time").toLocalDateTime());
                flight.setOrigin(rs.getString("origin"));
                flight.setDestination(rs.getString("destination"));
                flight.setStatus(rs.getString("status"));
                flights.add(flight);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return flights;
    }


    public Map<String, Integer> getFlightStatusCount(){
        Map<String, Integer> statusCountMap = new HashMap<>();
        String query = "SELECT status, COUNT(*) AS flight_count FROM flights GROUP BY status";

        try (Connection conn = DBConnection.getDBInstance();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            // Process the result set and populate the map
            while (rs.next()) {
                String status = rs.getString("status");
                int flightCount = rs.getInt("flight_count");
                statusCountMap.put(status, flightCount);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return statusCountMap;
    }
}