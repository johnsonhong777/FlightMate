package com.flightmate.dao;

import com.flightmate.beans.Flight;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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
        try (Connection conn = DBConnection.getDBInstance();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, newStatus);
            stmt.setInt(2, flightId);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
