package com.flightmate.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.flightmate.beans.Airport;
import com.flightmate.dao.DBConnection;

public class AirportDao {
    
    private static AirportDao dao;

    private AirportDao() {}

    public static synchronized AirportDao getDao() {
        if (dao == null) {
            dao = new AirportDao();
        }
        return dao;
    }

    public List<Airport> getAllAirports() {
        List<Airport> airports = new ArrayList<>();
        String sql = "SELECT airport_id, name, location FROM airports";

        try (Connection conn = DBConnection.getDBInstance();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Airport airport = new Airport(
                    rs.getInt("airport_id"),
                    rs.getString("name"),
                    rs.getString("location")
                );
                airports.add(airport);
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return airports;
    }

    public boolean addAirport(Airport airport) {
        String sql = "INSERT INTO airports (name, location) VALUES (?, ?)";
        try (Connection conn = DBConnection.getDBInstance();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, airport.getName());
            stmt.setString(2, airport.getLocation());
            return stmt.executeUpdate() > 0;

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateAirport(Airport airport) {
        String sql = "UPDATE airports SET name = ?, location = ? WHERE airport_id = ?";
        try (Connection conn = DBConnection.getDBInstance();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, airport.getName());
            stmt.setString(2, airport.getLocation());
            stmt.setInt(3, airport.getAirportId());
            return stmt.executeUpdate() > 0;

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteAirport(int airportId) {
        String sql = "DELETE FROM airports WHERE airport_id = ?";
        try (Connection conn = DBConnection.getDBInstance();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, airportId);
            return stmt.executeUpdate() > 0;

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Airport getAirportById(int airportId) {
        String sql = "SELECT airport_id, name, location FROM airports WHERE airport_id = ?";
        try (Connection conn = DBConnection.getDBInstance();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, airportId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Airport(
                    rs.getInt("airport_id"),
                    rs.getString("name"),
                    rs.getString("location")
                );
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}