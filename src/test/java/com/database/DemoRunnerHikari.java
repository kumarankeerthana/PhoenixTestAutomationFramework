package com.database;

import java.sql.Connection;
import java.sql.SQLException;

public class DemoRunnerHikari {

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		
		
		Connection conn = DatabaseManager.getConnection();
		System.out.println(conn);

	}

}
