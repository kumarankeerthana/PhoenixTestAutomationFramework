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
import com.github.javafaker.Faker;



public class CreateJobAPITest2_Demo {
	
	
	private CreateJobPayload createjobPayload;
	private final static String COUNTRY = "India";
	
	@BeforeMethod(description = " Setup for create job api ", groups = { "api", "smoke", "regression" })
	public void setup() {
		

		Faker faker = new Faker(new Locale("en-IND"));

		String first_name = faker.name().firstName();
		String last_name = faker.name().lastName();
		String mobileNumber = faker.numerify("9#########");
		String alternateMobileNumber = faker.numerify("9#########");
		String customerEmailAddress = faker.internet().emailAddress();
		String alternateCustomerEmailAddress = faker.internet().emailAddress();
		Customer customer = new Customer(first_name, last_name, mobileNumber, alternateMobileNumber,
				customerEmailAddress, alternateCustomerEmailAddress);
		System.out.println(customer);
		
		

		String flatnumber = faker.numerify("###");
		String apartmentname = faker.address().buildingNumber();
		String streetname = faker.address().streetName();
		String landmark = faker.address().streetName();
		String area = faker.address().streetName();
		String pincode = faker.numerify("#######");
		String state = faker.address().state();
		CustomerAddress address = new CustomerAddress(flatnumber, apartmentname, streetname, landmark, area, pincode,
				COUNTRY, state);
		System.out.println(address);
		
		
		String dop = DayTimeUtil.getTimeWithDaysAgo(10); 
		String serial_number = faker.numerify("##############");
		String popurl = faker.internet().url() ;
		
		
		CustomerProduct  customerproduct = new CustomerProduct(dop, serial_number, serial_number, serial_number, popurl, 1, 1);
		System.out.println(customerproduct);
		
		
		String fakeRemark = faker.lorem().sentence(4);
		Random random = new Random();
		int problemid = random.nextInt(26)+1;		
		Problems problems = new Problems(problemid, fakeRemark);
		System.out.println(problems);
		
		List<Problems> problemList = new ArrayList<Problems>();
		problemList.add(problems);
		
		createjobPayload = new CreateJobPayload(0, 2, 1 , 1, customer, address, customerproduct, problemList)	;
		System.out.println(createjobPayload);

		
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
