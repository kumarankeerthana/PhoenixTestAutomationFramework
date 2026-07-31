package com.database;

import java.io.IOException;
import java.sql.SQLException;

public class DemoRunner {

	public static void main(String[] args) throws SQLException, IOException {
		
		DatabaseManagerOLD.createConnection();
		long start_time = System.currentTimeMillis();
		
	for(int i=1;i<400;i++) {
		DatabaseManagerOLD.createConnection();
		
	}
		
		long end_time = System.currentTimeMillis();
		System.out.println(end_time -start_time);

	}

}
