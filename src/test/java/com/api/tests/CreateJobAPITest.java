package com.api.tests;

import org.testng.annotations.Test;

import static com.api.constants.Roles.*;

import com.api.pojo.CreateJobPayload;
import com.api.pojo.*;

import com.api.utils.SpecUtil;

import static io.restassured.RestAssured.*;

import java.io.IOException;

public class CreateJobAPITest {
	
	@Test
	public void createJobAPITest() throws IOException {
		
		
		//createjobpayload 
		Customer customer = new Customer("Keerthana", "Kumaran", "9381011566", "9381011566 ", "kitty@gmail.com","kitty@gmail.com");
		CustomerAddress customeraddress = new CustomerAddress("1673", "Kitty Residence", "St.clair ave ", "freshco", "windsor", "600044", "India", "TamilNadu");
		CustomerProduct customerproduct = new CustomerProduct("2026-06-01T04:00:00.000Z", "56796078157946", "56796078157946", "56796078157946", "2026-06-01T04:00:00.000Z", 1, 1);
		Problems problems = new Problems(1,"Battery Issue");
		Problems[] problemsArray = new Problems[1];
		problemsArray[0] = problems;
		
		CreateJobPayload createjobPayload = new CreateJobPayload(0, 2, 1, 1, customer, customeraddress, customerproduct, problemsArray);
		
		given().spec(SpecUtil.requestSpecWithAuth(FD,createjobPayload))
		.and()
		.when().post("/job/create")
		.then()
		.spec(SpecUtil.responseSpec_JSON(200));
		
		
		
		
	}

}
