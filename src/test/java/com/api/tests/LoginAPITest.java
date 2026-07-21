package com.api.tests;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import java.io.IOException;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.request.model.UserCredentials;
import static com.api.utils.SpecUtil.*;

import io.restassured.module.jsv.JsonSchemaValidator;

public class LoginAPITest {

	private UserCredentials fdcreds;

	@BeforeMethod(description = " creating payload for login api")
	public void setup() {

		fdcreds = new UserCredentials("iamfd", "password");

	}

	@Test(description = "Verify if login api is working for User iamfd", groups = { "api", "regression", "smoke" })
	public void loginAPITest() throws IOException {

		given().spec(requestSpec(fdcreds)).and()

				.when().post("login")

				.then().spec(responseSpec_OK()).body("message", equalTo("Success"))
				.body("data", notNullValue())
				.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/LoginAPIRequestSchema.json"));

	}

}
