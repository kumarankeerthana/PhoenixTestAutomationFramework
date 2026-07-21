package com.api.tests;
import static com.api.constants.Roles.FD;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.notNullValue;

import java.io.IOException;

import org.testng.annotations.Test;

import static com.api.utils.SpecUtil.*;

import static io.restassured.module.jsv.JsonSchemaValidator.*;

public class MasterAPITest {
	
	@Test(description = " Verify if master api response is correct" , groups = {"api","smoke","regression"})
	public void masterAPITest() throws IOException {
		
		given().spec(requestSpecWithAuth(FD))
		
		.when().post("master")  //Default content type added by Rest Assured is url-formed 
		
		
		.then().spec(responseSpec_OK())
		.body("message", equalTo("Success"))
		.body("data", notNullValue())
		.body(matchesJsonSchemaInClasspath("response-schema/MasterAPIFDSchema.json"))
		.body("data", hasKey("mst_oem"))
		.body("data", hasKey("mst_model"))
		.body("$", hasKey("message"))
		.body("$", hasKey("data"))
		.body("data.mst_oem.size()", greaterThanOrEqualTo(0))
		.body("data.mst_model.size()", greaterThanOrEqualTo(0))
		.body("data.mst_oem.id", everyItem(notNullValue()))
		.body("data.mst_oem.name", everyItem(notNullValue()));
	}
	
	@Test (description = " Verifying if master api status code is 401 with invalid token", groups = {"api","smoke","regression"})
	public void masterAPI_MissingToken() throws IOException  {
		
		given().spec(requestSpec())
		
		.when().post("master")  //Default content type added by Rest Assured is url-formed 
		
		
		.then().spec(responseSpec_TEXT(401));
		
	}

}

