package com.flightmate.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.flightmate.libs.DBType;

/*
 * Utility to get the connection to our database by simply passing the DBType enum.
 * Also includes a processException() method to format the type of error we get if there's a problem in connecting to the DB.
 * 
 * This code was shared by David Gassner in Module 3: Connecting to multiple databases.
 * https://www.linkedin.com/learning/java-database-integration-with-jdbc/connecting-to-multiple-databases?resume=false&u=2199673
 *  
 */

public class DBUtil {
	private static final String USERNAME = "root";
	private static final String PASSWORD = "";
	private static String M_CONN_STRING = "jdbc:mysql://localhost/";

	public static Connection getConnection(DBType dbType) throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		switch (dbType) {
		case MYSQL:
			return DriverManager.getConnection(M_CONN_STRING, USERNAME, PASSWORD);
		default:
			return null;
		}
	}

	public static void setConnStr() {
		if (M_CONN_STRING.contains(ApplicationDao.DB_NAME))
			return;
		M_CONN_STRING += ApplicationDao.DB_NAME;
	}

	public static void processException(SQLException e) {
		System.err.println("Error message: " + e.getMessage());
		System.err.println("Error code: " + e.getErrorCode());
		System.err.println("SQL State: " + e.getSQLState());
		System.err.println("Trace project package: " + printRelevantStackTrace(e) + "\n");
	}

	private static String printRelevantStackTrace(Throwable e) {
		int i = 0;
		for (StackTraceElement element : e.getStackTrace()) {
			i++;
			if (element.getClassName().startsWith("com.flightmate")) {
				return ((String) "Relevant stack trace element[" + i + "]: " + element);
			}
		}
		return "not found";
	}
	
	 // Function to get all tables
    public static List<String> getAllTables(Connection conn) throws SQLException {
        List<String> tables = new ArrayList<>();
        try (ResultSet rs = conn.getMetaData().getTables(ApplicationDao.DB_NAME, null, "%", new String[] {"TABLE"})) {
            while (rs.next()) {
                String tableName = rs.getString("TABLE_NAME");
                tables.add(tableName);
            }
        }
        return tables;
    }

    // Function to display all tables
    public static void displayTables(Connection conn) {
        try {
            List<String> tables = getAllTables(conn);
            System.out.println("Tables in the database:");
            for (String table : tables) {
                System.out.println(table);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
