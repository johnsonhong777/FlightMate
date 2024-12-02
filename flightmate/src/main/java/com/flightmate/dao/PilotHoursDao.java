package com.flightmate.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.flightmate.beans.FlightHour;


public class PilotHoursDao {
    private static PilotHoursDao dao;

    public static synchronized PilotHoursDao getDao() {
        if (dao == null) dao = new PilotHoursDao();
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
}


