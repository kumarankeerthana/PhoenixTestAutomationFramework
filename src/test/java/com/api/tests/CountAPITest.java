package com.api.tests;

import static com.api.constants.Roles.FD;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.blankOrNullString;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;

import java.io.IOException;

import org.testng.annotations.Test;

import static com.api.utils.SpecUtil.*;

import static io.restassured.module.jsv.JsonSchemaValidator.*;

public class CountAPITest {

	@Test(description = " Verify if count api response is correct" , groups = {"api","smoke","regression"})
	public void countAPITest() throws IOException {

		given().spec(requestSpecWithAuth(FD))

				.when().get("dashboard/count")

				.then()
				.spec(responseSpec_OK())
				.body("message", equalTo("Success")).body("data", notNullValue())
				.body("data.size()", equalTo(3)).body("data.count", everyItem(greaterThanOrEqualTo(0)))
				.body("data.label", everyItem(not(blankOrNullString())))
				.body("data.key", containsInAnyOrder("pending_for_delivery", "created_today", "pending_fst_assignment"))
				.body(matchesJsonSchemaInClasspath("response-schema/CountAPIFDSchema.json"))
				
		;

	}

	@Test(description = " Verify if count api is statuc code is 401 for invalid token" , groups = {"api","smoke","regression"})
	public void countAPITest_MissingAuthtoken() throws IOException {

		given().spec(requestSpec())

				.when().get("dashboard/count")

				.then().spec(responseSpec_TEXT(401));
	}

}
