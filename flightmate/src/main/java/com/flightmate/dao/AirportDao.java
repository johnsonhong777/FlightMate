package com.flightmate.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.flightmate.beans.Airport;
import com.flightmate.dao.DBConnection;
import com.flightmate.libs.builders.AirportBuilder;

public class AirportDao {
    
	   // Get All Airports
    public static List<Airport> getAllAirports() {
        List<Airport> airports = new ArrayList<>();
        String sql = "SELECT * FROM Airports";
        try (Connection conn = DBConnection.getDBInstance();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                airports.add(new AirportBuilder()
                        .setAirportId(rs.getInt("airport_id"))
                        .setAirportCode(rs.getString("airport_code"))
                        .setAirportName(rs.getString("airport_name"))
                        .setCity(rs.getString("city"))
                        .setCountry(rs.getString("country"))
                        .setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime())
                        .create());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return airports;
    }}