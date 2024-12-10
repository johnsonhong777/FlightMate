package com.flightmate.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ApplicationDao {
    private static ApplicationDao dao;
    public static final String DB_NAME = "flightmate";
    public static final String USERS_TABLE = "users";
    public static final String ROLES_TABLE = "roles";
    public static final String AIRPORTS_TABLE = "airports";
    public static final String AIRCRAFT_TABLE = "aircrafts";
    public static final String FLIGHTS_TABLE = "flights";
    public static final String PILOT_HOURS_TABLE = "pilot_hours";
    public static final String FEEDBACK_TABLE = "feedback";
    
    private ApplicationDao() {}

    public static synchronized ApplicationDao getDao() {
        if (dao == null) dao = new ApplicationDao();
        return dao;
    }

    public static void createDatabase() {
        try (
                Connection conn = DBConnection.getDBInstance();
                ResultSet resultSet = conn.getMetaData().getCatalogs();
                Statement stmt = conn.createStatement();
            ) {
            if (!dbExists(DB_NAME, resultSet)) {
                System.out.print("Creating DB...");
                String sql = "CREATE DATABASE IF NOT EXISTS "+ DB_NAME +" DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci";
                stmt.executeUpdate(sql);
                System.out.println("Created DB");
            }
            DBUtil.setConnStr();

        } catch (SQLException e) {
            DBUtil.processException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    public void createRolesTable() {
        try (
                Connection conn = DBConnection.getDBInstance();
                Statement stmt = conn.createStatement();
            ) {
            if (!tableExists(conn, ROLES_TABLE)) {
                System.out.print("Creating Roles Table...");
                String sql = "CREATE TABLE IF NOT EXISTS "+ ROLES_TABLE +" ("
                        + "role_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, "
                        + "role_name VARCHAR(25) NOT NULL, "
                        + "role_description VARCHAR(240) NOT NULL);";
                stmt.executeUpdate(sql);
                
                sql = "INSERT INTO " + ROLES_TABLE + "(role_name, role_description) VALUES "
                		+ "('PILOT', 'Default user. Can log flight hours and schedule own flights.'),"
                		+ "('ADMINISTRATOR', 'Can approve/reject flight hours, manage aircrafts and airports.')";
                
                stmt.executeUpdate(sql);
                
                System.out.println("Created Roles Table");
            }

        } catch (SQLException e) {
            DBUtil.processException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    public void createFlightsTable() {
        try (
                Connection conn = DBConnection.getDBInstance();
                Statement stmt = conn.createStatement();
            ) {
            if (!tableExists(conn, FLIGHTS_TABLE)) {
                System.out.print("Creating Flights Table...");
                String sql = "CREATE TABLE IF NOT EXISTS " + FLIGHTS_TABLE + " ("
                        + "id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, "
                        + "flight_number VARCHAR(50) NOT NULL UNIQUE, "
                        + "departure_time TIMESTAMP NOT NULL, "
                        + "arrival_time TIMESTAMP NOT NULL, "
                        + "origin VARCHAR(100) NOT NULL, "
                        + "destination VARCHAR(100) NOT NULL, "
                        + "status VARCHAR(25) NOT NULL DEFAULT 'Scheduled', "
                        + "created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, "
                        + "updated_at TIMESTAMP"
                        + ");";
                stmt.executeUpdate(sql);
                System.out.println("Created Flights Table");
            }

        } catch (SQLException e) {
            DBUtil.processException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    public void createPilotHoursTable() {
    	 try (
                 Connection conn = DBConnection.getDBInstance();
                 Statement stmt = conn.createStatement();
             ) {
             if (!tableExists(conn, PILOT_HOURS_TABLE)) {
            	 System.out.print("Creating Pilot_Hours Table...");
            	 String sql = "CREATE TABLE IF NOT EXISTS "+ PILOT_HOURS_TABLE +" ("
                         + "id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, "
                         + "pilot_id INT NOT NULL, "
                         + "flight_date DATE NOT NULL, "
                         + "hours_flighted DECIMAL(5,2) NOT NULL, "
                         + "notes TEXT, "
                         + "approval_status VARCHAR(15) DEFAULT 'PENDING', "
                         + "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, "
                         + "FOREIGN KEY (pilot_id) REFERENCES users(user_id) ON DELETE CASCADE);";
            	 stmt.executeUpdate(sql);
            	 System.out.println("Created Pilot Hours Table");
             }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void createUserTable() {
        try (
                Connection conn = DBConnection.getDBInstance();
                Statement stmt = conn.createStatement();
            ) {
            if (!tableExists(conn, USERS_TABLE)) {
                System.out.print("Creating User Table...");
                String sql = "CREATE TABLE IF NOT EXISTS "+ USERS_TABLE +" ("
                        + "user_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, "
                        + "role_id INT NOT NULL, "
                        + "email_address VARCHAR(128) NOT NULL UNIQUE, "
                        + "first_name VARCHAR(25) NOT NULL, "
                        + "last_name VARCHAR(25) NOT NULL, "
                        + "password VARCHAR(64) NOT NULL, "
                        + "created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP(), "
                        + "updated_at TIMESTAMP, "
                        + "deleted_at TIMESTAMP, "
                        + "FOREIGN KEY (role_id) REFERENCES " + ROLES_TABLE + "(role_id) ON DELETE CASCADE);";
                stmt.executeUpdate(sql);
                System.out.println("Created User Table");
            }

        } catch (SQLException e) {
            DBUtil.processException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    public void createAirportTable() {
        try (
                Connection conn = DBConnection.getDBInstance();
                Statement stmt = conn.createStatement();
            ) {
            if (!tableExists(conn, AIRPORTS_TABLE)) {
                System.out.print("Creating Airport Table...");
                String sql = "CREATE TABLE IF NOT EXISTS "+ AIRPORTS_TABLE +" ("
                        + "id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, "
                        + "airport_name VARCHAR(255) NOT NULL, "
                        + "airport_code VARCHAR(3) NOT NULL UNIQUE, "
                        + "city VARCHAR(255) NOT NULL, "
                        + "country VARCHAR(255) NOT NULL, "
                        + "runways INT NOT NULL, "
                        + "created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP());";              
                        
                stmt.executeUpdate(sql);
                System.out.println("Created airports Table");
            }

        } catch (SQLException e) {
            DBUtil.processException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void createAircraftTable() {
        try (
                Connection conn = DBConnection.getDBInstance();
                Statement stmt = conn.createStatement();
        ) {
            if (!tableExists(conn, AIRCRAFT_TABLE)) {
                System.out.print("Creating Aircrafts Table...");
                String sql = "CREATE TABLE IF NOT EXISTS " + AIRCRAFT_TABLE + " ("
                        + "aircraft_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, "
                        + "aircraft_model VARCHAR(50) NOT NULL, "
                        + "manufacture_date DATE NOT NULL, "
                        + "last_maintenance_date DATE NOT NULL, "
                        + "next_maintenance_date DATE NOT NULL, "
                        + "aircraft_notes TEXT, "
                        + "created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP(), "
                        + "administrator_id INT NOT NULL, "
                        + "airport_id INT NOT NULL, "
                        + "FOREIGN KEY (administrator_id) REFERENCES " + USERS_TABLE + "(user_id) ON DELETE CASCADE, "
                        + "FOREIGN KEY (airport_id) REFERENCES " + AIRPORTS_TABLE + "(id) ON DELETE CASCADE);";
                stmt.executeUpdate(sql);
                System.out.println("Created Aircrafts Table");
            }

        } catch (SQLException e) {
            DBUtil.processException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    public void createFeedbackTable() {
        try (
                Connection conn = DBConnection.getDBInstance();
                Statement stmt = conn.createStatement();
        ) {
            if (!tableExists(conn, FEEDBACK_TABLE)) {
                System.out.print("Creating Feedback Table...");
                String sql = "CREATE TABLE IF NOT EXISTS " + FEEDBACK_TABLE + " ("
                        + "feedback_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, "
                        + "feedback_type VARCHAR(50) NOT NULL, "
                        + "feedback_date DATE NOT NULL DEFAULT (CURDATE()), "
                        + "feedback_comment TEXT NOT NULL, "
                        + "has_read BOOLEAN DEFAULT FALSE, "
                        + "user_id INT NOT NULL, "
                        + "FOREIGN KEY (user_id) REFERENCES " + USERS_TABLE + "(user_id) ON DELETE CASCADE);";
                stmt.executeUpdate(sql);
                System.out.println("Created Feedback Table");
            }

        } catch (SQLException e) {
            DBUtil.processException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    
 
    private static boolean dbExists(String dbName, ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            if (resultSet.getString(1).equals(dbName)) return true;
        }
        return false;
    }

    public boolean tableExists(Connection conn, String tableName) throws SQLException {
        return conn.getMetaData().getTables(DB_NAME, null, tableName, new String[] {"TABLE"}).next();
    }    
    
    public void insertSampleAirports() throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO " + AIRPORTS_TABLE + " (airport_code, airport_name, city, country) VALUES "
                + "('JFK', 'John F. Kennedy International Airport', 'New York', 'USA'), "
                + "('LHR', 'Heathrow Airport', 'London', 'UK'), "
                + "('DXB', 'Dubai International Airport', 'Dubai', 'UAE');";
        try (
                Connection conn = DBConnection.getDBInstance();
                Statement stmt = conn.createStatement();
        ) {
            stmt.executeUpdate(sql);
            System.out.println("Sample airports inserted.");
        }
    }

    public void insertSampleAircrafts() throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO " + AIRCRAFT_TABLE + " (aircraft_model, manufacture_date, last_maintenance_date, "
                + "next_maintenance_date, aircraft_notes, administrator_id, airport_id) VALUES "
                + "('Boeing 747', '2000-01-01', '2024-01-01', '2025-01-01', 'Flagship aircraft.', 2, 1), "
                + "('Airbus A380', '2010-01-01', '2023-06-15', '2024-06-15', 'Double-decker aircraft.', 2, 2);";
        try (
                Connection conn = DBConnection.getDBInstance();
                Statement stmt = conn.createStatement();
        ) {
            stmt.executeUpdate(sql);
            System.out.println("Sample aircrafts inserted.");
        }
    }
}
