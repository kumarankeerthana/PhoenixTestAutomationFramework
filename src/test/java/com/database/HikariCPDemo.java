package com.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.api.utils.ConfigManager;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class HikariCPDemo {

	public static void main(String[] args) throws SQLException {

		HikariConfig hikariconfig = new HikariConfig();
		hikariconfig.setJdbcUrl(ConfigManager.getProperty("DB_URL"));
		System.out.println("Using URL: " + ConfigManager.getProperty("DB_URL"));
		hikariconfig.setUsername(ConfigManager.getProperty("DB_USER_NAME"));
		hikariconfig.setPassword(ConfigManager.getProperty("DB_PASSWORD"));
		hikariconfig.setMaximumPoolSize(10);
		hikariconfig.setMinimumIdle(2); // reserved for higher load
		hikariconfig.setConnectionTimeout(100000); // if all the connections are full and a new test case is awaiting for
		// connection - it will wait only for 10 secs. --> doesnt get a connection it
		// will throw SQLTimeout Exception

		// POOL MANAGEMENT
		// IT created 10 connections --> connects to same database
		// During parallel execution of test case - hikari is going to give the
		// connection to each test case
		//

		hikariconfig.setIdleTimeout(10000); // if hikari connections are idle for more than 10 secs it will close
											// automatically
		hikariconfig.setMaxLifetime(1800000); //30 mins 
		//after 30 seconds hikari will close old connections and creates new ones 
		//stale connections can be eliminated 
		hikariconfig.setPoolName("Phoenix Test Automation Framework Pool"); //reporting purpose 
		
		HikariDataSource ds = new HikariDataSource(hikariconfig);
		Connection conn = ds.getConnection();
		System.out.println(conn);

		Statement statement = conn.createStatement();
		ResultSet rs = statement.executeQuery("SELECT first_name , last_name , mobile_number from tr_customer ;");

		while (rs.next()) {
			String first_name = rs.getString("first_name");
			String last_name = rs.getString("last_name");
			String mobile_number = rs.getString("mobile_number");
			System.out.println(first_name + " | " + last_name + " | " + mobile_number);
		}
		ds.close();

	}
}
