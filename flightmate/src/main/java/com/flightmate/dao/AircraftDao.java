package com.flightmate.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.flightmate.beans.Aircraft;
import com.flightmate.dao.DBConnection;

public class AircraftDao {
    private static AircraftDao dao;

    private AircraftDao() {}

    public static synchronized AircraftDao getDao() {
        if (dao == null) dao = new AircraftDao();
        return dao;
    }

    public boolean addAircraft(Aircraft aircraft) {
        String sql = "INSERT INTO aircrafts (model, details, airport_id) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getDBInstance();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, aircraft.getModel());
            stmt.setString(2, aircraft.getDetails());
            stmt.setInt(3, aircraft.getAirportId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }
}
