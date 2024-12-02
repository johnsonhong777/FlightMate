package com.flightmate.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.flightmate.beans.Airport;

import com.flightmate.libs.builders.AirportBuilder;

public class AirportDao {
	private static AirportDao dao;
	public static final String AIRPORT_ID = "id";
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
               ps.setString(1, airport.getAirportName());
               ps.setString(2, airport.getAirportCode());
               ps.setString(3, airport.getCity());
               ps.setString(4, airport.getCountry());
               ps.setInt(5, airport.getRunways());

               int rowsInserted = ps.executeUpdate();
               if (rowsInserted == 0) {
                   throw new SQLException("Failed to add airport.");
               }
        } catch (Exception e) {
        	e.printStackTrace();
        }
	}
    
	   // Get All Airports
    public static List<Airport> getAllAirports() {
        List<Airport> airports = new ArrayList<>();
        String sql = "SELECT * FROM Airports";
        try (Connection conn = DBConnection.getDBInstance();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                airports.add(new AirportBuilder()
                        .setAirportId(rs.getInt("id"))
                        .setAirportCode(rs.getString("airport_code"))
                        .setAirportName(rs.getString("airport_name"))
                        .setCity(rs.getString("city"))
                        .setCountry(rs.getString("country"))
                        .setRunways(rs.getInt("runways"))
                        .setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime())
                        .create());

            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
		return airports;
    }
	
	public void updateAirport(Airport airport) {
	    String sql = "UPDATE airports SET airport_name = ?, airport_code = ?, city = ?, country = ?, runways = ? WHERE id = ?";
	    try (Connection conn = DBConnection.getDBInstance();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setString(1, airport.getAirportName());
	        stmt.setString(2, airport.getAirportCode());
	        stmt.setString(3, airport.getCity());
	        stmt.setString(4, airport.getCountry());
	        stmt.setInt(5, airport.getRunways());
	        stmt.setInt(6, airport.getAirportId());

	        int affectedRows = stmt.executeUpdate();
	        if (affectedRows == 0) {
	            throw new SQLException("Updating airport failed, no rows affected.");
	        }
	    } catch (SQLException | ClassNotFoundException e) {
	        e.printStackTrace();  // Handle exceptions appropriately in production
	    }
	}

	public void deleteAirport(int airportId) {
	    String sql = "DELETE FROM airports WHERE id = ?";
	    try (Connection conn = DBConnection.getDBInstance();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setInt(1, airportId);
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
				+ " WHERE "+AIRPORT_ID+" = ?";
		
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
		
	public Airport getAirportById(int airportId) {
		Airport Airport = null;
		String sql = "SELECT "+AIRPORT_ID+", "+AIRPORT_NAME+", "+AIRPORT_CODE+", "+CITY+", "+COUNTRY + ", " + RUNWAYS +" FROM " +ApplicationDao.AIRPORTS_TABLE+" WHERE " + AIRPORT_ID + " = ?";
		try (
				Connection conn = DBConnection.getDBInstance();
				PreparedStatement stmt = conn.prepareStatement(sql);
			) {
			stmt.setInt(1, airportId);
			ResultSet rs = stmt.executeQuery();
			
			if (rs != null && rs.next()) {
				Airport = new AirportBuilder()
						.setAirportId(airportId)
						.setAirportName(rs.getString(AIRPORT_NAME))
						.setAirportCode(rs.getString(AIRPORT_CODE))
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

	public Map<String, Long> getAirportCountByCity() {
		Map<String, Long> cityAirportCountMap = new HashMap<>();
		String sql = "SELECT city, COUNT(*) AS airport_count FROM airports GROUP BY city";

		try (Connection conn = DBConnection.getDBInstance();
			 PreparedStatement stmt = conn.prepareStatement(sql);
			 ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				String city = rs.getString("city");
				long count = rs.getLong("airport_count");
				cityAirportCountMap.put(city, count);
			}

		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}

		return cityAirportCountMap;
	}
}