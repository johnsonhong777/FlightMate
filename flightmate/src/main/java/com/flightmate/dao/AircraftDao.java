package com.flightmate.dao;

import com.flightmate.beans.Aircraft;
import com.flightmate.libs.builders.AircraftBuilder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AircraftDao {
    private static AircraftDao dao;

    private AircraftDao() {}

    public static synchronized AircraftDao getDao() {
        if (dao == null) dao = new AircraftDao();
        return dao;
    }

    // Debug log added for addAircraft method
    public static boolean addAircraft(Aircraft aircraft) {
        String sql = "INSERT INTO Aircrafts (aircraft_model, manufacture_date, last_maintenance_date, next_maintenance_date, aircraft_notes, created_at, administrator_id, airport_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        System.out.println("Attempting to add aircraft: " + aircraft.getAircraftModel());
        try (Connection conn = DBConnection.getDBInstance();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, aircraft.getAircraftModel());
            stmt.setDate(2, java.sql.Date.valueOf(aircraft.getManufactureDate()));
            stmt.setDate(3, java.sql.Date.valueOf(aircraft.getLastMaintenanceDate()));
            stmt.setDate(4, java.sql.Date.valueOf(aircraft.getNextMaintenanceDate()));
            stmt.setString(5, aircraft.getAircraftNotes());
            stmt.setTimestamp(6, java.sql.Timestamp.valueOf(aircraft.getCreatedAt()));
            stmt.setInt(7, aircraft.getAdministratorId());
            stmt.setInt(8, aircraft.getAirportId());
            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Aircraft added successfully: " + aircraft.getAircraftModel());
                return true;
            } else {
                System.out.println("Failed to add aircraft: " + aircraft.getAircraftModel());
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to get all aircrafts from the database
    public List<Aircraft> getAllAircraft() {
        List<Aircraft> aircraftList = new ArrayList<>();
        String query = "SELECT * FROM aircrafts";
        System.out.println("Executing query to fetch all aircraft: " + query);

        try (Connection conn = DBConnection.getDBInstance();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            // Loop through the result set and create Aircraft objects
            while (rs.next()) {
                int aircraftId = rs.getInt("aircraft_id");
                String aircraftModel = rs.getString("aircraft_model");
                LocalDate manufactureDate = rs.getDate("manufacture_date").toLocalDate();
                LocalDate lastMaintenanceDate = rs.getDate("last_maintenance_date").toLocalDate();
                LocalDate nextMaintenanceDate = rs.getDate("next_maintenance_date").toLocalDate();
                String aircraftNotes = rs.getString("aircraft_notes");
                LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();
                int administratorId = rs.getInt("administrator_id");
                int airportId = rs.getInt("airport_id");

                // Debug log added for each aircraft creation
                System.out.println("Found aircraft: " + aircraftModel);

                // Create the Aircraft object using the builder pattern
                Aircraft aircraft = new AircraftBuilder()
                        .setAircraftId(aircraftId)
                        .setAircraftModel(aircraftModel)
                        .setManufactureDate(manufactureDate)
                        .setLastMaintenanceDate(lastMaintenanceDate)
                        .setNextMaintenanceDate(nextMaintenanceDate)
                        .setAircraftNotes(aircraftNotes)
                        .setCreatedAt(createdAt)
                        .setAdministratorId(administratorId)
                        .setAirportId(airportId)
                        .create();

                // Add the created aircraft to the list
                aircraftList.add(aircraft);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Returning list of " + aircraftList.size() + " aircrafts.");
        return aircraftList;
    }

    public boolean updateAircraft(Aircraft aircraft) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE aircrafts SET aircraft_model = ?, manufacture_date = ?, last_maintenance_date = ?, next_maintenance_date = ?, aircraft_notes = ?, administrator_id = ?, airport_id = ? WHERE aircraft_id = ?";
        System.out.println("Updating aircraft with ID: " + aircraft.getAircraftId());

        try (Connection conn = DBConnection.getDBInstance();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, aircraft.getAircraftModel());
            stmt.setDate(2, java.sql.Date.valueOf(aircraft.getManufactureDate()));
            stmt.setDate(3, java.sql.Date.valueOf(aircraft.getLastMaintenanceDate()));
            stmt.setDate(4, java.sql.Date.valueOf(aircraft.getNextMaintenanceDate()));
            stmt.setString(5, aircraft.getAircraftNotes());
            stmt.setInt(6, aircraft.getAdministratorId());
            stmt.setInt(7, aircraft.getAirportId());
            stmt.setInt(8, aircraft.getAircraftId());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Successfully updated aircraft with ID: " + aircraft.getAircraftId());
                return true;
            } else {
                System.out.println("Failed to update aircraft with ID: " + aircraft.getAircraftId());
                return false;
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw e; // rethrowing the exception for proper error handling
        }
    }

    // Delete Aircraft
    public boolean deleteAircraft(int aircraftId) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM aircrafts WHERE aircraft_id = ?";
        System.out.println("Attempting to delete aircraft with ID: " + aircraftId);

        try (Connection conn = DBConnection.getDBInstance();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, aircraftId);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Successfully deleted aircraft with ID: " + aircraftId);
                return true;
            } else {
                System.out.println("Failed to delete aircraft with ID: " + aircraftId);
                return false;
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw e; // rethrowing the exception for proper error handling
        }
    }

    // Get Aircraft by ID
    public Aircraft getAircraftById(int aircraftId) {
        Aircraft aircraft = null;
        String sql = "SELECT * FROM aircrafts WHERE aircraft_id = ?";
        System.out.println("Fetching aircraft with ID: " + aircraftId);

        try (Connection conn = DBConnection.getDBInstance();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, aircraftId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String aircraftModel = rs.getString("aircraft_model");
                java.sql.Date manufactureDate = rs.getDate("manufacture_date");
                java.sql.Date lastMaintenanceDate = rs.getDate("last_maintenance_date");
                java.sql.Date nextMaintenanceDate = rs.getDate("next_maintenance_date");
                String aircraftNotes = rs.getString("aircraft_notes");
                java.sql.Timestamp createdAt = rs.getTimestamp("created_at");
                int administratorId = rs.getInt("administrator_id");
                int airportId = rs.getInt("airport_id");

                aircraft = new AircraftBuilder()
                        .setAircraftId(aircraftId)
                        .setAircraftModel(aircraftModel)
                        .setManufactureDate(manufactureDate.toLocalDate())
                        .setLastMaintenanceDate(lastMaintenanceDate.toLocalDate())
                        .setNextMaintenanceDate(nextMaintenanceDate.toLocalDate())
                        .setAircraftNotes(aircraftNotes)
                        .setCreatedAt(createdAt.toLocalDateTime())
                        .setAdministratorId(administratorId)
                        .setAirportId(airportId)
                        .create();
                System.out.println("Aircraft with ID " + aircraftId + " found.");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return aircraft;
    }
}
