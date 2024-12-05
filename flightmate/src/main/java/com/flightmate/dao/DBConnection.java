package com.flightmate.dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.flightmate.libs.DBType;

public class DBConnection {
	
	private static boolean initializedDB = false;
	
	public static void initDB() {
		
		if (initializedDB) { return; }
		initializedDB = true;
		
		ApplicationDao.createDatabase();
		ApplicationDao.getDao().createRolesTable();
		ApplicationDao.getDao().createUserTable();
		ApplicationDao.getDao().createPilotHoursTable();
		ApplicationDao.getDao().createAirportTable();
		ApplicationDao.getDao().createFlightsTable();
		ApplicationDao.getDao().createAircraftTable();
		ApplicationDao.getDao().createFeedbackTable();

	}	
	
    public static Connection getDBInstance() throws ClassNotFoundException {
    	initDB();
    	Connection connection = null;
        try {
        	connection = DBUtil.getConnection(DBType.MYSQL);
        } catch (SQLException e) {
        	DBUtil.processException(e);
        };
        
        return connection;
    }
}