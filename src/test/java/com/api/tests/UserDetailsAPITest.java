package com.api.tests;
import static com.api.constants.Roles.FD;
import static com.api.constants.Roles.SUP;
import static com.api.utils.AuthTokenProvider.getToken;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;

import org.testng.annotations.Test;

import static com.api.utils.SpecUtil.*;

import io.restassured.http.Header;
import static io.restassured.module.jsv.JsonSchemaValidator.*;


public class UserDetailsAPITest {
	
	
	@Test(description ="Verify if Userdetails api response is shown correctly", groups = {"api","smoke","regression"})
	public void userDetailsAPITest() throws IOException {
		
		Header authHeader = new Header("Authorization", getToken(SUP));
		
		given().spec(requestSpecWithAuth(FD))
		.header(authHeader)
		
		
		.when().get("userdetails")
		
		.then().spec(responseSpec_OK())
		.body("message", equalTo("Success"))
		//.body("data.id", equalTo(4))
		.body(matchesJsonSchemaInClasspath("response-schema/UserDetailsFDSchema.json"));
		
	}

}
