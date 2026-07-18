package com.api.tests;

import static org.hamcrest.Matchers.*;
import org.testng.annotations.Test;

import static com.api.constants.Roles.*;
import static com.api.utils.AuthTokenProvider.*;

import static io.restassured.http.ContentType.*;
import io.restassured.http.Header;
import io.restassured.module.jsv.JsonSchemaValidator;

import static com.api.utils.ConfigManager.*;

import static io.restassured.RestAssured.*;

import java.io.IOException;

public class CountAPITest {

	@Test
	public void countAPITest() throws IOException {

		given().baseUri(getProperty("BASE_URI")).header(new Header("Authorization", getToken(FD))).contentType(JSON)
				.log().uri().log().method().log().headers()

				.when().get("dashboard/count")

				.then().log().all().statusCode(200).body("message", equalTo("Success")).body("data", notNullValue())
				.body("data.size()", equalTo(3)).body("data.count", everyItem(greaterThanOrEqualTo(0)))
				.body("data.label", everyItem(not(blankOrNullString())))
				.body("data.key", containsInAnyOrder("pending_for_delivery", "created_today", "pending_fst_assignment"))
				.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/CountAPIFDSchema.json"))
				.time(lessThan(6000L))

		;

	}

	@Test
	public void countAPITest_MissingAuthtoken() throws IOException {

		given().baseUri(getProperty("BASE_URI")).log().uri().log().method().log().headers().and()

				.when().get("dashboard/count")

				.then().log().all().statusCode(401);

	}

}
