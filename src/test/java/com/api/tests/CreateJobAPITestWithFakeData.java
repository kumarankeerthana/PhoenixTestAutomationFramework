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

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.request.model.CreateJobPayload;
import com.api.request.model.Customer;
import com.api.request.model.CustomerAddress;
import com.api.utils.FakerDataGenerator;
import com.database.dao.CustomerAddressDao;
import com.database.dao.CustomerDao;
import com.database.model.CustomerAddressDBModel;
import com.database.model.CustomerDBModel;



public class CreateJobAPITestWithFakeData{
	
	
	private CreateJobPayload createjobPayload;
	
	
	@BeforeMethod(description = " Setup for create job api ", groups = { "api", "smoke", "regression" })
	public void setup() {
		
		createjobPayload = FakerDataGenerator.generateFakeCreateJobData();
		

		
	}

	@Test(description = " Verify if create job  api response is correct for inwarranty flow ", groups = { "api",
			"smoke", "regression" })
	public void createJobAPITest()  {

		
		int tr_customer_id ;
		int tr_customer_address_id;
		Customer expectedCustomerData = createjobPayload.customer();
		CustomerAddress expectedCustomerAddressData = createjobPayload.customer_address();
		CustomerDBModel customerDBValue = null;
		CustomerAddressDBModel customerAddressDBValue = null; 
		try {
			tr_customer_id= given().spec(requestSpecWithAuth(FD, createjobPayload)).and().when().post("/job/create").then()
					.spec(responseSpec_JSON(200)).body("message", equalTo("Job created successfully. "))
					.body("data", notNullValue()).body("data.mst_service_location_id", equalTo(1))
					.body("data.job_number", startsWith("JOB_"))
					.body(matchesJsonSchemaInClasspath("response-schema/CreateJobAPISchema.json"))
					.extract().body().jsonPath().getInt("data.tr_customer_id");
			 customerDBValue = CustomerDao.getCustomerInfo(tr_customer_id);
			 tr_customer_address_id = customerDBValue.getTr_customer_address_id();
			 customerAddressDBValue =CustomerAddressDao.getCustomerAddressInfo(tr_customer_address_id);
		} catch ( IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		Assert.assertEquals(expectedCustomerData.first_name(), customerDBValue.getFirst_name());
		Assert.assertEquals(expectedCustomerData.last_name(), customerDBValue.getLast_name());
		Assert.assertEquals(expectedCustomerData.mobile_number(), customerDBValue.getMobile_number());
		Assert.assertEquals(expectedCustomerData.mobile_number_alt(), customerDBValue.getMobile_number_alt());
		Assert.assertEquals(expectedCustomerData.email_id(), customerDBValue.getEmail_id());
		Assert.assertEquals(expectedCustomerData.email_id_alt(), customerDBValue.getEmail_id_alt());
		
		Assert.assertEquals(expectedCustomerAddressData.flat_number(), customerAddressDBValue.getFlat_number());
		Assert.assertEquals(expectedCustomerAddressData.apartment_name(), customerAddressDBValue.getApartment_name());
		Assert.assertEquals(expectedCustomerAddressData.street_name(), customerAddressDBValue.getStreet_name());
		Assert.assertEquals(expectedCustomerAddressData.landmark(), customerAddressDBValue.getLandmark());
		Assert.assertEquals(expectedCustomerAddressData.area(), customerAddressDBValue.getArea());
		Assert.assertEquals(expectedCustomerAddressData.pincode(), customerAddressDBValue.getPincode());
		Assert.assertEquals(expectedCustomerAddressData.country(), customerAddressDBValue.getCountry());
		Assert.assertEquals(expectedCustomerAddressData.state(), customerAddressDBValue.getState());

	}

}
