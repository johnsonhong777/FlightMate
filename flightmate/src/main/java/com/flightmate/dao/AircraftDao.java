package com.flightmate.dao;

import com.flightmate.beans.Aircraft;
import com.flightmate.beans.Airport;
import com.flightmate.beans.User;
import com.flightmate.dao.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AircraftDao {

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
    public static List<User> getAllAdministrators() {
        //Fetching users with ADMINISTRATOR role
        List<User> administrators = new ArrayList<>();
        String sql = "SELECT user_id, first_name, last_name, email, role_id FROM Users WHERE role_id = 2";
        try (Connection conn = DBConnection.getDBInstance();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                administrators.add(new User(
                    rs.getInt("user_id"),
                    rs.getString("email"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getInt("role_id") 
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return administrators;
    }

 
}
