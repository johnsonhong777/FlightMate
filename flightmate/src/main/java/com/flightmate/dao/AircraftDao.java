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
    private static final String AIRCRAFT_TABLE = "aircrafts"; // Initialize with the correct table name
	private static AircraftDao dao;
	private AircraftDao() {}
	
	public static synchronized AircraftDao getDao() {
		if (dao == null) dao = new AircraftDao();
		return dao;
	}

    public static boolean addAircraft(Aircraft aircraft) {
        String sql = "INSERT INTO Aircrafts (aircraft_model, manufacture_date, last_maintenance_date, next_maintenance_date, aircraft_notes, created_at, administrator_id, airport_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
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
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    
    

 
    
    // Method to get all aircrafts from the database
    public List<Aircraft> getAllAircraft() {
        List<Aircraft> aircraftList = new ArrayList<>();
        String query = "SELECT * FROM " + AIRCRAFT_TABLE;

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
            e.printStackTrace();}
         

        return aircraftList;
    }
}
