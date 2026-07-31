package com.database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.api.utils.ConfigManager;

public class DatabaseManagerOLD  {
	
	private static final String DB_URL = 	ConfigManager.getProperty("DB_URL");
	private static final String DB_USER_NAME = ConfigManager.getProperty("DB_USER_NAME");
	private static final String DB_PASSWORD = ConfigManager.getProperty("DB_PASSWORD");
	private static volatile Connection conn ;
	 
	private DatabaseManagerOLD() {
		//private constructor to restrict object creation outside the class 
	}
	
	

	public static  void createConnection() throws SQLException, IOException {
		
		
		if(conn ==null) {//double checked lock-in pattern
			synchronized(DatabaseManagerOLD.class) {
		if (conn ==null) {
		conn = DriverManager.getConnection(DB_URL,DB_USER_NAME ,
				DB_PASSWORD);
		System.out.println(conn);
		}
		
		
		}
		}
		
		
		
	}

}
