package com.api.tests;
import static io.restassured.RestAssured.*;

import java.io.IOException;

import static org.hamcrest.Matchers.*;
import org.testng.annotations.Test;

import io.restassured.module.jsv.JsonSchemaValidator;

import static com.api.constants.Roles.*;
import static com.api.utils.AuthTokenProvider.*;

import static io.restassured.http.ContentType.*;

import static com.api.utils.ConfigManager.*;

public class MasterAPITest {
	
	@Test
	public void masterAPITest() throws IOException {
		
		given().baseUri(getProperty("BASE_URI"))
		.contentType(JSON)
		.header("Authorization",getToken(FD))
		.log().uri()
		.log().method()
		.log().headers()
		
		.when().post("master")  //Default content type added by Rest Assured is url-formed 
		
		
		.then().log().all()
		.statusCode(200)
		.time(lessThan(6000L))
		.body("message", equalTo("Success"))
		.body("data", notNullValue())
		.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/MasterAPIFDSchema.json"))
		.body("data", hasKey("mst_oem"))
		.body("data", hasKey("mst_model"))
		.body("$", hasKey("message"))
		.body("$", hasKey("data"))
		.body("data.mst_oem.size()", greaterThanOrEqualTo(0))
		.body("data.mst_model.size()", greaterThanOrEqualTo(0))
		.body("data.mst_oem.id", everyItem(notNullValue()))
		.body("data.mst_oem.name", everyItem(notNullValue()));
	}
	
	@Test
	public void masterAPI_MissingToken() throws IOException  {
		
		given().baseUri(getProperty("BASE_URI"))
		.contentType(JSON)
		.log().uri()
		.log().method()
		.log().headers()
		
		.when().post("master")  //Default content type added by Rest Assured is url-formed 
		
		
		.then().log().all()
		.statusCode(401);
		
	}

}

