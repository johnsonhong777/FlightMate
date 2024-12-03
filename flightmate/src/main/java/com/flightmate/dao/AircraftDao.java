package com.flightmate.dao;

import com.flightmate.beans.Aircraft;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class AircraftDao {
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

 
}
