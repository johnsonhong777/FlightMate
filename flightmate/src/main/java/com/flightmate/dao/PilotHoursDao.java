package com.flightmate.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;

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
}