package com.api.tests;

import static com.api.constants.Roles.FD;
import static com.api.utils.DayTimeUtil.getTimeWithDaysAgo;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.startsWith;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.BeforeMethod;
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
import static com.api.utils.SpecUtil.*;



public class CreateJobAPITest {
	
	
	private CreateJobPayload createjobPayload;
	
	@BeforeMethod(description = " Setup for create job api ", groups = { "api", "smoke", "regression" })
	public void setup() {

		// createjobpayload
		Customer customer = new Customer("Keerthana", "Kumaran", "9381011566", "9381011566 ", "kitty@gmail.com",
				"kitty@gmail.com");
		CustomerAddress customeraddress = new CustomerAddress("1673", "Kitty Residence", "St.clair ave ", "freshco",
				"windsor", "600044", "India", "TamilNadu");

		CustomerProduct customerproduct = new CustomerProduct(getTimeWithDaysAgo(10), "78726078157948",
				"78726078157948", "78726078157948", getTimeWithDaysAgo(10), Product.NEXUS_2.getcode(),
				Model.NEXUS_2_BLUE.getCode());

		Problems problems = new Problems(Problem.SMARTPHONE_IS_RUNNING_SLOW.getCode(), "Battery Issue");
		List<Problems> problemsList = new ArrayList<>();
		problemsList.add(problems);

	createjobPayload = new CreateJobPayload(ServiceLocation.SERVICE_LOCATION_A.getCode(),
				Platform.FRONT_DESK.getCode(), Warranty_status.IN_WARRANTY.getCode(), OEM.GOOGLE.getCode(), customer,
				customeraddress, customerproduct, problemsList);

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
