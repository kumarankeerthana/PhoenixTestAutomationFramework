package com.api.tests;
import static io.restassured.RestAssured.*;

import static org.hamcrest.Matchers.*;

import java.io.IOException;

import org.testng.annotations.Test;

import com.api.pojo.UserCredentials;

import static com.api.utils.ConfigManager.*;


import io.restassured.module.jsv.JsonSchemaValidator;

import static io.restassured.http.ContentType.*;

public class LoginAPITest {
	
	
	@Test 
	public void loginAPITest() throws IOException {
		
		UserCredentials fdcreds = new UserCredentials("iamfd", "password");
	
		
		given().baseUri(getProperty("BASE_URI"))
		.and()
		.contentType(JSON)
		.and()
		.accept(ANY)
		.body(fdcreds)
		.log().uri()
		.log().headers()
		.log().method()
		.log().body()
		
		.when().post("login")
		
		.then()
		.log().all()
		.and()
		.statusCode(200)
		.body("message", equalTo("Success"))
		.body("data", notNullValue())
		.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/LoginAPIRequestSchema.json"))
		.time(lessThan(1500L));
		
		
	}

}
