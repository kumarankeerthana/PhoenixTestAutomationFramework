package com.api.tests;

import static com.api.constants.Roles.FD;
import static com.api.utils.DayTimeUtil.getTimeWithDaysAgo;
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

import org.testng.Assert;
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
import com.database.dao.CustomerAddressDao;
import com.database.dao.CustomerDao;
import com.database.dao.CustomerProductDao;
import com.database.model.CustomerAddressDBModel;
import com.database.model.CustomerDBModel;
import com.database.model.CustomerProductDBModel;

import io.restassured.response.Response;



public class CreateJobAPIWithDBValidationTest {
	private Customer customer;
	private CustomerDBModel customerDBModel ;
	private CustomerAddress customeraddress;
	private CustomerAddressDBModel customerAddressDBModel; 
	CustomerProduct customerproduct ;
	Problems problems;
	
	
	private CreateJobPayload createjobPayload;
	
	@BeforeMethod(description = " Setup for create job api ", groups = { "api", "smoke", "regression" })
	public void setup() {

		// createjobpayload
		 customer = new Customer("Keerthu", "Kumaran", "9381011566", "9381011566 ", "kitty@gmail.com",
				"kitty@gmail.com");
		 customeraddress = new CustomerAddress("1673", "Kitty Residence", "St.clair ave ", "freshco",
				"windsor", "600044", "India", "TamilNadu");

		 customerproduct = new CustomerProduct(getTimeWithDaysAgo(10), "12589077155998",
				"12589077155998", "12589077155998", getTimeWithDaysAgo(10), Product.NEXUS_2.getcode(),
				Model.NEXUS_2_BLUE.getCode());

		problems = new Problems(Problem.SMARTPHONE_IS_RUNNING_SLOW.getCode(), "Battery Issue");
		List<Problems> problemsList = new ArrayList<>();
		problemsList.add(problems);

	createjobPayload = new CreateJobPayload(ServiceLocation.SERVICE_LOCATION_A.getCode(),
				Platform.FRONT_DESK.getCode(), Warranty_status.IN_WARRANTY.getCode(), OEM.GOOGLE.getCode(), customer,
				customeraddress, customerproduct, problemsList);

	}

	@Test(description = " Verify if create job  api response is correct for inwarranty flow ", groups = { "api",
			"smoke", "regression" })
	public void createJobAPITest() throws IOException{
		
		int tr_customer_id;
		
		

		Response response = given().spec(requestSpecWithAuth(FD, createjobPayload)).and().when().post("/job/create").then()
				.spec(responseSpec_JSON(200)).body("message", equalTo("Job created successfully. "))
				.body("data", notNullValue()).body("data.mst_service_location_id", equalTo(1))
				.body("data.job_number", startsWith("JOB_"))
				.body(matchesJsonSchemaInClasspath("response-schema/CreateJobAPISchema.json"))
				.extract().response();
		
		tr_customer_id = response.then().extract().body().jsonPath().getInt("data.tr_customer_id");
		
		
		
		
		
		
		System.out.println("------------------------------------------------------------");
		System.out.println(tr_customer_id);
		
		
		customerDBModel = CustomerDao.getCustomerInfo(tr_customer_id);
		System.out.println(customerDBModel);
		int tr_customer_address_id = customerDBModel.getTr_customer_address_id();
		customerAddressDBModel = CustomerAddressDao.getCustomerAddressInfo(tr_customer_address_id);
		System.out.println(customerAddressDBModel);
		
		
		Assert.assertEquals(customer.first_name(), customerDBModel.getFirst_name());
		Assert.assertEquals(customer.last_name(), customerDBModel.getLast_name());
		Assert.assertEquals(customer.mobile_number(), customerDBModel.getMobile_number());
		Assert.assertEquals(customer.mobile_number_alt(), customerDBModel.getMobile_number_alt());
		Assert.assertEquals(customer.email_id(), customerDBModel.getEmail_id());
		Assert.assertEquals(customer.email_id_alt(), customerDBModel.getEmail_id_alt());
		
		Assert.assertEquals(customeraddress.flat_number(), customerAddressDBModel.getFlat_number());
		Assert.assertEquals(customeraddress.apartment_name(), customerAddressDBModel.getApartment_name());
		Assert.assertEquals(customeraddress.street_name(), customerAddressDBModel.getStreet_name());
		Assert.assertEquals(customeraddress.landmark(), customerAddressDBModel.getLandmark());
		Assert.assertEquals(customeraddress.area(), customerAddressDBModel.getArea());
		Assert.assertEquals(customeraddress.pincode(), customerAddressDBModel.getPincode());
		Assert.assertEquals(customeraddress.country(), customerAddressDBModel.getCountry());
		Assert.assertEquals(customeraddress.state(), customerAddressDBModel.getState());
		
		int product_id = response.then().extract().body().jsonPath().getInt("data.tr_customer_product_id");
		CustomerProductDBModel cutomerProductData = CustomerProductDao.getCustomerProductInfo(product_id);
		
		//Assert.assertEquals(cutomerProductData.getDop(), customerproduct.dop().substring(0, 10));
		Assert.assertEquals(cutomerProductData.getSerial_number(), customerproduct.serial_number());
		Assert.assertEquals(cutomerProductData.getImei1(), customerproduct.imei1());
		Assert.assertEquals(cutomerProductData.getImei2(), customerproduct.imei2());
		//Assert.assertEquals(cutomerProductData.getPopurl(), customerproduct.popurl().substring(0, 10));
		Assert.assertEquals(cutomerProductData.getMst_model_id(), customerproduct.mst_model_id());
		
	}

}
