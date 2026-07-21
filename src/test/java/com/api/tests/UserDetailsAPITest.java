package com.api.tests;
import static io.restassured.RestAssured.*;

import static org.hamcrest.Matchers.*;

import java.io.IOException;

import org.testng.annotations.Test;

import com.api.utils.SpecUtil;

import static com.api.constants.Roles.*;

import static com.api.utils.AuthTokenProvider.*;

import io.restassured.http.Header;
import io.restassured.module.jsv.JsonSchemaValidator;

import static io.restassured.http.ContentType.*;
import static com.api.utils.ConfigManager.*;


public class UserDetailsAPITest {
	
	
	@Test
	public void userDetailsAPITest() throws IOException {
		
		Header authHeader = new Header("Authorization", getToken(SUP));
		
		given().spec(SpecUtil.requestSpecWithAuth(FD))
		.header(authHeader)
		
		
		.when().get("userdetails")
		
		.then().spec(SpecUtil.responseSpec_OK())
		.body("message", equalTo("Success"))
		//.body("data.id", equalTo(4))
		.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/UserDetailsFDSchema.json"));
		
	}

}
