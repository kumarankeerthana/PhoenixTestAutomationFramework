package com.api.tests.datadriven;

import static com.api.constants.Roles.FD;
import static com.api.utils.SpecUtil.requestSpecWithAuth;
import static com.api.utils.SpecUtil.responseSpec_JSON;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.startsWith;

import java.io.IOException;

import org.testng.annotations.Test;

import com.api.request.model.CreateJobPayload;



public class CreateJobAPIDataDrivenTestWithFakeData {
	
	
	
	
	

	@Test(description = " Verify if create job  api response is correct for inwarranty flow ", groups = { "api",
			"datadriven", "regression", "faker" }, dataProviderClass = com.dataproviders.DataProviderUtils.class,
			dataProvider = "CreateJobAPIFakerDataProvider")
	public void createJobAPITest(CreateJobPayload createjobpayload) throws IOException {

		given().spec(requestSpecWithAuth(FD, createjobpayload)).and().when().post("/job/create").then()
				.spec(responseSpec_JSON(200)).body("message", equalTo("Job created successfully. "))
				.body("data", notNullValue()).body("data.mst_service_location_id", equalTo(1))
				.body("data.job_number", startsWith("JOB_"))
				.body(matchesJsonSchemaInClasspath("response-schema/CreateJobAPISchema.json"));

	}

}
  