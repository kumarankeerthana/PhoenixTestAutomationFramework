package com.database;

import java.sql.Connection;
import java.sql.SQLException;

import com.api.utils.ConfigManager;
import com.api.utils.EnvUtil;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DatabaseManager {

	private static final String DB_URL = EnvUtil.getValue("DB_URL");
	
	private static final String DB_USERNAME = EnvUtil.getValue("DB_USERNAME");
	private static final String DB_PASSWORD = EnvUtil.getValue("DB_PASSWORD");
	private static HikariConfig hikariconfig;
	private static volatile HikariDataSource hikariDataSource = null;
	private static final int MAXIMUM_POOL_SIZE = Integer.parseInt(ConfigManager.getProperty("MAXIMUM_POOL_SIZE"));
	private static final int MINIMUM_IDLE_COUNT = Integer.parseInt(ConfigManager.getProperty("MINIMUM_IDLE_COUNT"));
	private static final int CONNECTION_TIMEOUT = Integer
			.parseInt(ConfigManager.getProperty("CONNECTION_TIMEOUT_IN_SECS")) * 1000;
	private static final int IDLE_TIMEOUT = Integer.parseInt(ConfigManager.getProperty("IDLE_TIMEOUT")) * 1000;
	private static final int MAX_LIFE_TIME_IN_MINS = Integer
			.parseInt(ConfigManager.getProperty("MAX_LIFE_TIME_IN_MINS")) * 60 * 1000;
	private static final String HIKARICP_POOLNAME = ConfigManager.getProperty("HIKARICP_POOLNAME");

	private DatabaseManager() {
		// private constructor to restrict object creation outside the class
	}

	private static void initializePool() {

		if (hikariDataSource == null) {// double checked lock-in pattern
			synchronized (DatabaseManager.class) {
				if (hikariDataSource == null) {
					hikariconfig = new HikariConfig();
					
					hikariconfig.setJdbcUrl(DB_URL);
					hikariconfig.setUsername(DB_USERNAME);
					hikariconfig.setPassword(DB_PASSWORD);
					hikariconfig.setMaximumPoolSize(MAXIMUM_POOL_SIZE);
					hikariconfig.setMinimumIdle(MINIMUM_IDLE_COUNT);
					hikariconfig.setConnectionTimeout(CONNECTION_TIMEOUT);
					/*
					 * if all the connections are full and a new test case is awaiting for //
					 * connection - it will wait only for 10 secs. --> doesnt get a connection it //
					 * will throw SQLTimeout Exception
					 * 
					 * // POOL MANAGEMENT // IT created 10 connections --> connects to same database
					 * // During parallel execution of test case - hikari is going to give the //
					 * connection to each test case
					 */
					hikariconfig.setIdleTimeout(IDLE_TIMEOUT); /*
																 * if hikari connections are idle for more than 10 secs
																 * it will close automatically
																 */
					hikariconfig.setMaxLifetime(MAX_LIFE_TIME_IN_MINS);
					/*
					 * after 30 seconds hikari will close old connections and creates new one stale
					 * connections can be eliminated
					 */
					hikariconfig.setPoolName(HIKARICP_POOLNAME); // reporting purpose
					hikariDataSource = new HikariDataSource(hikariconfig);

				}

			}
		}

	}

	public static Connection getConnection() throws SQLException {
		Connection connection = null;

		if (hikariDataSource == null) {
			initializePool();
		} else if (hikariDataSource.isClosed()) {

			throw new SQLException("Hikari Data Source is Closed");

		}

		connection = hikariDataSource.getConnection();

		return connection;
	}

}
