package com.flightmate.dao;

import com.flightmate.beans.Flight;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.flightmate.beans.FlightHour;
import java.util.HashMap;
import java.util.Map;


public class FlightDao {
    private static FlightDao dao;
    public static final String FLIGHT_ID = "id";
    public static final String FLIGHT_NUMBER = "flight_number";
    public static final String ORIGIN = "origin";
    public static final String DESTINATION = "destination";
    public static final String STATUS = "status";
    public static final String DEPARTURE_TIME = "departure_time";
    public static final String ARRIVAL_TIME = "arrival_time";

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private FlightDao() {}

    public static synchronized FlightDao getDao() {
        if (dao == null) dao = new FlightDao();
        return dao;
    }
    
    private Flight extractFlight(ResultSet rs) throws SQLException {
        Flight flight = new Flight();
        flight.setFlightId(rs.getInt(FLIGHT_ID));
        flight.setFlightNumber(rs.getString(FLIGHT_NUMBER));
        flight.setOrigin(rs.getString(ORIGIN));
        flight.setDestination(rs.getString(DESTINATION));
        flight.setStatus(rs.getString(STATUS));
        flight.setDepartureTime(LocalDateTime.parse(rs.getString(DEPARTURE_TIME), FORMATTER));
        flight.setArrivalTime(LocalDateTime.parse(rs.getString(ARRIVAL_TIME), FORMATTER));
        return flight;
    }

    public int addFlight(Flight flight) {
        String sql = "INSERT INTO flights (" + FLIGHT_NUMBER + ", " + ORIGIN + ", " + DESTINATION + ", " 
                + STATUS + ", " + DEPARTURE_TIME + ", " + ARRIVAL_TIME + ") VALUES (?, ?, ?, ?, ?, ?)";
        try (
                Connection conn = DBConnection.getDBInstance();
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ) {
            stmt.setString(1, flight.getFlightNumber());
            stmt.setString(2, flight.getOrigin());
            stmt.setString(3, flight.getDestination());
            stmt.setString(4, flight.getStatus());
            stmt.setString(5, flight.getDepartureTime().format(FORMATTER));
            stmt.setString(6, flight.getArrivalTime().format(FORMATTER));
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1); // Return the generated flight ID
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return -1; // Return -1 if insertion failed
    }

    public boolean updateFlight(Flight flight) {
        String sql = "UPDATE flights SET " + FLIGHT_NUMBER + " = ?, " + ORIGIN + " = ?, " + DESTINATION + " = ?, "
                + STATUS + " = ?, " + DEPARTURE_TIME + " = ?, " + ARRIVAL_TIME + " = ? WHERE " + FLIGHT_ID + " = ?";
        try (
                Connection conn = DBConnection.getDBInstance();
                PreparedStatement stmt = conn.prepareStatement(sql);
        ) {
            stmt.setString(1, flight.getFlightNumber());
            stmt.setString(2, flight.getOrigin());
            stmt.setString(3, flight.getDestination());
            stmt.setString(4, flight.getStatus());
            stmt.setString(5, flight.getDepartureTime().format(FORMATTER));
            stmt.setString(6, flight.getArrivalTime().format(FORMATTER));
            stmt.setInt(7, flight.getFlightId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteFlight(int flightId) {
        String sql = "DELETE FROM flights WHERE " + FLIGHT_ID + " = ?";
        try (
                Connection conn = DBConnection.getDBInstance();
                PreparedStatement stmt = conn.prepareStatement(sql);
        ) {
            stmt.setInt(1, flightId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Flight getFlightById(int flightId) {
        String sql = "SELECT * FROM flights WHERE " + FLIGHT_ID + " = ?";
        try (
                Connection conn = DBConnection.getDBInstance();
                PreparedStatement stmt = conn.prepareStatement(sql);
        ) {
            stmt.setInt(1, flightId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extractFlight(rs);
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }



    public boolean addPilotToFlight(int flightId, int pilotId) {
        String sql = "INSERT INTO flight_pilots (flight_id, pilot_id) VALUES (?, ?)";
        try (
                Connection conn = DBConnection.getDBInstance();
                PreparedStatement stmt = conn.prepareStatement(sql);
        ) {
            stmt.setInt(1, flightId);
            stmt.setInt(2, pilotId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean addAirportStopToFlight(int flightId, int airportId) {
        String sql = "INSERT INTO flight_airports (flight_id, airport_id) VALUES (?, ?)";
        try (
                Connection conn = DBConnection.getDBInstance();
                PreparedStatement stmt = conn.prepareStatement(sql);
        ) {
            stmt.setInt(1, flightId);
            stmt.setInt(2, airportId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void updateFlightStatus(int flightId, String newStatus) throws SQLException, ClassNotFoundException {
        String query = "UPDATE flights SET status = ? WHERE id = ?";
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
        try (
                Connection conn = DBConnection.getDBInstance();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
        ) {
            while (rs.next()) {
                flights.add(extractFlight(rs));
            }
        } catch (SQLException | ClassNotFoundException e) {
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
