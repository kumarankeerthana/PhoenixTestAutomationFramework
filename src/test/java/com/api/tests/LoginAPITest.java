package com.api.tests;
import static io.restassured.RestAssured.*;

import static org.hamcrest.Matchers.*;

import java.io.IOException;

import org.testng.annotations.Test;

import com.api.request.model.UserCredentials;
import com.api.utils.SpecUtil;

import static com.api.utils.ConfigManager.*;


import io.restassured.module.jsv.JsonSchemaValidator;

import static io.restassured.http.ContentType.*;

public class LoginAPITest {
	
	
	@Test 
	public void loginAPITest() throws IOException {
		
		UserCredentials fdcreds = new UserCredentials("iamfd", "password");
	
		
		given().spec(SpecUtil.requestSpec(fdcreds))
		.and()
		
		
		.when().post("login")
		
		.then().spec(SpecUtil.responseSpec_OK())
		.body("message", equalTo("Success"))
		.body("data", notNullValue())
		.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/LoginAPIRequestSchema.json"));
		
		
		
	}

}
