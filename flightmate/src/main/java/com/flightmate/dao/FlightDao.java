package com.flightmate.dao;

import com.flightmate.beans.Flight;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.flightmate.beans.FlightHour;
import java.util.HashMap;
import java.util.Map;

public class FlightDao {
    private static FlightDao dao;

    public static synchronized FlightDao getDao() {
        if (dao == null) dao = new FlightDao();
        return dao;
    }

    public void addPilotHours(int pilotId, String flightDate, String hoursFlighted, String notes) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO pilot_hours (pilot_id, flight_date, hours_flighted, notes) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getDBInstance();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, pilotId);
            stmt.setDate(2, java.sql.Date.valueOf(flightDate));
            stmt.setBigDecimal(3, new java.math.BigDecimal(hoursFlighted));
            stmt.setString(4, notes);
            stmt.executeUpdate();
        }
    }

    public List<FlightHour> getPendingFlightHours() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM pilot_hours WHERE approval_status = 'PENDING'";
        List<FlightHour> pendingHours = new ArrayList<>();
        try (Connection conn = DBConnection.getDBInstance();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                FlightHour hour = new FlightHour(
                    rs.getInt("id"),
                    rs.getInt("pilot_id"),
                    rs.getDate("flight_date"),
                    rs.getBigDecimal("hours_flighted"),
                    rs.getString("notes"),
                    rs.getString("approval_status"),
                    rs.getTimestamp("created_at")
                );
                pendingHours.add(hour);
            }
        }
        return pendingHours;
    }

    public void updateApprovalStatus(int id, String status) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE pilot_hours SET approval_status = ? WHERE id = ?";
        try (Connection conn = DBConnection.getDBInstance();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setInt(2, id);
            stmt.executeUpdate();
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
