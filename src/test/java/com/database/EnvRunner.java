package com.database;

import io.github.cdimascio.dotenv.Dotenv;

public class EnvRunner {
	
	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.load();
		String data = dotenv.get("DB_URL");
		System.out.println(data);
		String USERNAME = dotenv.get("DB_USERNAME");
		String password = dotenv.get("DB_PASSWORD");
		System.out.println(USERNAME);
		System.out.println(password);
	}
	

	
}

