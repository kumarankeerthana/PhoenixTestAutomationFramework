package com.api.tests;

import static com.api.constants.Roles.FD;
import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import org.testng.annotations.Test;

import com.api.request.model.CreateJobPayload;
import com.api.request.model.Customer;
import com.api.request.model.CustomerAddress;
import com.api.request.model.CustomerProduct;
import com.api.request.model.Problems;
import static com.api.utils.DayTimeUtil.*;
import com.api.utils.SpecUtil;

import io.restassured.module.jsv.JsonSchemaValidator;

public class CreateJobAPITest {
	
	@Test
	public void createJobAPITest() throws IOException {
		
		
		//createjobpayload 
		Customer customer = new Customer("Keerthana", "Kumaran", "9381011566", "9381011566 ", "kitty@gmail.com","kitty@gmail.com");
		CustomerAddress customeraddress = new CustomerAddress("1673", "Kitty Residence", "St.clair ave ", "freshco", "windsor", "600044", "India", "TamilNadu");
		CustomerProduct customerproduct = new CustomerProduct(getTimeWithDaysAgo(10), "78726078157946", "78726078157946", "78726078157946", getTimeWithDaysAgo(10), 1, 1);
		Problems problems = new Problems(1, "Battery Issue");
		List<Problems> problemsList = new ArrayList<>();
		problemsList.add(problems);
		
		CreateJobPayload createjobPayload = new CreateJobPayload(0, 2, 1, 1, customer, customeraddress, customerproduct, problemsList);
		
		given().spec(SpecUtil.requestSpecWithAuth(FD,createjobPayload))
		.and()
		.when().post("/job/create")
		.then()
		.spec(SpecUtil.responseSpec_JSON(200))
		.body("message", equalTo("Job created successfully. "))
		.body("data", notNullValue())
		.body("data.mst_service_location_id", equalTo(1))
		.body("data.job_number", startsWith("JOB_"))
		.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/CreateJobAPISchema.json"));
		
		
		
		
	}

}
