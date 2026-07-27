package com.api.utils;

import java.util.Locale;

import com.github.javafaker.Faker;

public class FakerDemo {

	public static void main(String[] args) {
		
		Faker faker = new Faker(new Locale ("en-USA")); //fluent style 
		String name = faker.name().firstName();
		String last_name = faker.name().lastName();		
		System.out.println(last_name);
		
		
		System.out.println(faker.address().buildingNumber()); 
		

	}

}
