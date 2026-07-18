package com.api.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {
	
	
	/*The Job of Config Manager - is to load information related to Environments 
	 * The static block makes sure that the object is created only once.
	 * The fail-first way - no file we get the fail message first 
	 * Kind of singleton because object creation happens only once - but this a way to implement singleton class 
	*/

	private static Properties properties = new Properties();
	private static String path = "config/config.properties";
	private static String env;

	private ConfigManager() {
		// private constructor
	}

	static {

		env = System.getProperty("env", "qa"); // "qa is default if no one passes any value
		env = env.toLowerCase().trim();

		switch (env) {
		case "dev" -> path = "config/config.dev.properties";  //arrow operator JAVA 14 
		case "qa" -> path = "config/config.qa.properties";    //why ? reduces boiler plate code 
		case "uat" -> path = "config/config.uat.properties";
		default -> path = "config/config.properties";

		}

		InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);

		if (input == null) {
			throw new RuntimeException("Cannot Find the file at path" + path);
		}

		try {
			properties.load(input);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static String getProperty(String key) throws IOException {

		return properties.getProperty(key);

	}

}
