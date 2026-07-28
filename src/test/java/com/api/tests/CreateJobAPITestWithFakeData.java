package com.api.tests;

import static com.api.constants.Roles.FD;
import static com.api.utils.SpecUtil.requestSpecWithAuth;
import static com.api.utils.SpecUtil.responseSpec_JSON;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.startsWith;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.request.model.CreateJobPayload;
import com.api.request.model.Customer;
import com.api.request.model.CustomerAddress;
import com.api.request.model.CustomerProduct;
import com.api.request.model.Problems;
import com.api.utils.DayTimeUtil;
import com.api.utils.FakerDataGenerator;
import com.github.javafaker.Faker;



public class CreateJobAPITestWithFakeData{
	
	
	private CreateJobPayload createjobPayload;
	
	
	@BeforeMethod(description = " Setup for create job api ", groups = { "api", "smoke", "regression" })
	public void setup() {
		
		createjobPayload = FakerDataGenerator.generateFakeCreateJobData();
		

		
	}

	@Test(description = " Verify if create job  api response is correct for inwarranty flow ", groups = { "api",
			"smoke", "regression" })
	public void createJobAPITest() throws IOException {

		given().spec(requestSpecWithAuth(FD, createjobPayload)).and().when().post("/job/create").then()
				.spec(responseSpec_JSON(200)).body("message", equalTo("Job created successfully. "))
				.body("data", notNullValue()).body("data.mst_service_location_id", equalTo(1))
				.body("data.job_number", startsWith("JOB_"))
				.body(matchesJsonSchemaInClasspath("response-schema/CreateJobAPISchema.json"));

	}

}
