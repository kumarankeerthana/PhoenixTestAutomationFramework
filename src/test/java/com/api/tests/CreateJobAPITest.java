package com.api.tests;

import static com.api.constants.Roles.FD;
import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import org.testng.annotations.Test;

import com.api.constants.Model;
import com.api.constants.OEM;
import com.api.constants.Platform;
import com.api.constants.Problem;
import com.api.constants.Product;
import com.api.constants.ServiceLocation;
import com.api.constants.Warranty_status;
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
		
		CustomerProduct customerproduct = new CustomerProduct(getTimeWithDaysAgo(10), "78726078157948", "78726078157948", "78726078157948", getTimeWithDaysAgo(10), Product.NEXUS_2.getcode(), Model.NEXUS_2_BLUE.getCode());
		
		Problems problems = new Problems(Problem.SMARTPHONE_IS_RUNNING_SLOW.getCode(), "Battery Issue");
		List<Problems> problemsList = new ArrayList<>();
		problemsList.add(problems);
		
		CreateJobPayload createjobPayload = new CreateJobPayload(ServiceLocation.SERVICE_LOCATION_A.getCode(), Platform.FRONT_DESK.getCode(), Warranty_status.IN_WARRANTY.getCode(), OEM.GOOGLE.getCode(), customer, customeraddress, customerproduct, problemsList);
		
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
