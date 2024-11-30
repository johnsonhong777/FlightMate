package com.flightmate.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.flightmate.beans.Airport;
import com.flightmate.libs.builders.AirportBuilder;

public class AirportDao {
	private static AirportDao dao;
	public static final String Airport_ID = "id";
	public static final String AIRPORT_NAME = "airport_name";
	public static final String AIRPORT_CODE = "airport_code";
	public static final String CITY = "city";
	public static final String COUNTRY = "country";
	public static final String RUNWAYS = "runways";
	
	
	private AirportDao() {}
	
	public static synchronized AirportDao getDao() {
		if (dao == null) dao = new AirportDao();
		return dao;
	}
	
	/*
	public void createAirport(HttpServletRequest req) {
		String airport_name = req.getParameter("airport_name");
		String airport_code = req.getParameter("airport_code");
		String city = req.getParameter("city");
		String country = req.getParameter("country");
		int runways = Role.toRoleId(req.getParameter("runways"));
		
		String sql = "INSERT INTO "+ApplicationDao.AIRPORTS_TABLE+" ("+AIRPORT_NAME+","+AIRPORT_CODE+","+CITY+","+COUNTRY+","+RUNWAYS+") VALUES (?, ?, ?, ?, ?)";
		
		try (
				Connection conn = DBConnection.getDBInstance();
				PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			) {
    		stmt.setString(1, airport_name);
    		stmt.setString(2, airport_code);
    		stmt.setString(3, city);
    		stmt.setString(4, country);
    		stmt.setInt(5, runways);
    		
    		stmt.executeUpdate();
    		
    		ResultSet rs = stmt.getGeneratedKeys();
    		
    		if (rs != null && rs.next()) {
    			HttpSession session = req.getSession();
    			session.setAttribute("Airport", new AirportBuilder()
    					.setAirportId(rs.getInt(1))
    					.setName(airport_name)
    					.setCode(airport_code)
    					.setCity(city)
    					.setCountry(country)
    					.setRunways(runways)
    					.create());
    		}
    		
    		if (rs != null) rs.close();
			
		} catch (SQLException e) {
			DBUtil.processException(e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	*/
	public void createAirport(Airport airport) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO airports (airport_name, airport_code, city, country, runways) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getDBInstance();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, airport.getAirport_name());
            ps.setString(2, airport.getAirport_code());
            ps.setString(3, airport.getCity());
            ps.setString(4, airport.getCountry());
            ps.setInt(5, airport.getRunways());

            int rowsInserted = ps.executeUpdate();
            if (rowsInserted == 0) {
                throw new SQLException("Failed to add airport.");
            }
        }
    }
	
	public List<Airport> getAllAirports() {
	    List<Airport> airports = new ArrayList<>();
	    String sql = "SELECT id, airport_name, airport_code, city, country, runways FROM airports";
	    try (Connection conn = DBConnection.getDBInstance();
	         PreparedStatement stmt = conn.prepareStatement(sql);
	         ResultSet rs = stmt.executeQuery()) {
	        while (rs.next()) {
	            Airport airport = new Airport(
	                rs.getInt("id"),
	                rs.getString("airport_name"),
	                rs.getString("airport_code"),
	                rs.getString("city"),
	                rs.getString("country"),
	                rs.getInt("runways")
	            );
	            airports.add(airport);
	        }
	    } catch (SQLException | ClassNotFoundException e) {
	        e.printStackTrace();  // Handle exceptions appropriately in production
	    }
	    return airports;
	}
	
	public void updateAirport(Airport airport) {
	    String sql = "UPDATE airports SET airport_name = ?, airport_code = ?, city = ?, country = ?, runways = ? WHERE id = ?";
	    try (Connection conn = DBConnection.getDBInstance();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setString(1, airport.getAirport_name());
	        stmt.setString(2, airport.getAirport_code());
	        stmt.setString(3, airport.getCity());
	        stmt.setString(4, airport.getCountry());
	        stmt.setInt(5, airport.getRunways());
	        stmt.setInt(6, airport.getId());

	        int affectedRows = stmt.executeUpdate();
	        if (affectedRows == 0) {
	            throw new SQLException("Updating airport failed, no rows affected.");
	        }
	    } catch (SQLException | ClassNotFoundException e) {
	        e.printStackTrace();  // Handle exceptions appropriately in production
	    }
	}

	public void deleteAirport(int id) {
	    String sql = "DELETE FROM airports WHERE id = ?";
	    try (Connection conn = DBConnection.getDBInstance();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setInt(1, id);
	        int affectedRows = stmt.executeUpdate();
	        if (affectedRows == 0) {
	            throw new SQLException("Deleting airport failed, no rows affected.");
	        }
	    } catch (SQLException | ClassNotFoundException e) {
	        e.printStackTrace();  // Handle exceptions appropriately in production
	    }
	}	
	
	public boolean updateAirport(String airport_name, String airport_code, String city, String country, int runways,  int AirportId) {
		boolean updated = false;
		String sql = "UPDATE "+ApplicationDao.AIRPORTS_TABLE+" SET "
				+AIRPORT_NAME+" = ?, "
				+AIRPORT_CODE+" = ?, "
				+CITY+" = ?, "
				+COUNTRY+" = ?, "
				+RUNWAYS+" = ? "
				+ " WHERE "+Airport_ID+" = ?";
		
		try (
				Connection conn = DBConnection.getDBInstance();
				PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			) {
    		stmt.setString(1, airport_name);
    		stmt.setString(2, airport_code);
    		stmt.setString(3, city);
    		stmt.setString(4, country);
    		stmt.setInt(5, runways);
    		stmt.setInt(6, AirportId);
    		
    		updated = stmt.executeUpdate() > 0;
			
		} catch (SQLException e) {
			DBUtil.processException(e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return updated;
	}
		
	public Airport getAirportById(int AirportId) {
		Airport Airport = null;
		String sql = "SELECT "+Airport_ID+", "+AIRPORT_NAME+", "+AIRPORT_CODE+", "+CITY+", "+COUNTRY + ", " + RUNWAYS +" FROM " +ApplicationDao.AIRPORTS_TABLE+" WHERE " + Airport_ID + " = ?";
		try (
				Connection conn = DBConnection.getDBInstance();
				PreparedStatement stmt = conn.prepareStatement(sql);
			) {
			stmt.setInt(1, AirportId);
			ResultSet rs = stmt.executeQuery();
			
			if (rs != null && rs.next()) {
				Airport = new AirportBuilder()
						.setAirportId(AirportId)
						.setName(rs.getString(AIRPORT_NAME))
						.setCode(rs.getString(AIRPORT_CODE))
						.setCity(rs.getString(CITY))
						.setCountry(rs.getString(COUNTRY))
						.setRunways(rs.getInt(RUNWAYS))
						.create();
				}
						
			if (rs != null) rs.close();
		} catch (SQLException e) {
			DBUtil.processException(e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			
		}
		return Airport;
	}	
	
	
}