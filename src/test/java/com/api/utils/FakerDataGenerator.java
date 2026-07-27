package com.api.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import com.api.request.model.CreateJobPayload;
import com.api.request.model.Customer;
import com.api.request.model.CustomerAddress;
import com.api.request.model.CustomerProduct;
import com.api.request.model.Problems;
import com.github.javafaker.Faker;

public class FakerDataGenerator {
	
	private static Faker faker = new Faker(new Locale("en-IND"));
	
	private final static String COUNTRY = "India";
	private static CreateJobPayload createjobPayload;
	private static Random RANDOM = new Random();
	private final static  int MST_SERVICE_LOCATION_ID = 0;
	private final static int MST_PLATFORM_ID = 2;
	private final static int MST_WARRANTY_STATUS_ID = 1 ;
	private final static int MST_OEM_ID = 1;
	private final static int PRODUCT_ID = 1;
	private final static int MST_MODEL_ID = 1;
	
	
	private FakerDataGenerator() {
		
	}
	
	private static Customer generateFakeCustomerData() {
		String first_name = faker.name().firstName();
		String last_name = faker.name().lastName();
		String mobileNumber = faker.numerify("9#########");
		String alternateMobileNumber = faker.numerify("9#########");
		String customerEmailAddress = faker.internet().emailAddress();
		String alternateCustomerEmailAddress = faker.internet().emailAddress();
		Customer customer = new Customer(first_name, last_name, mobileNumber, alternateMobileNumber,
				customerEmailAddress, alternateCustomerEmailAddress);
		return customer;
	}
	
	private static CustomerAddress generateFakeCustomerAddress() {
		String flatnumber = faker.numerify("###");
		String apartmentname = faker.address().buildingNumber();
		String streetname = faker.address().streetName();
		String landmark = faker.address().streetName();
		String area = faker.address().streetName();
		String pincode = faker.numerify("#######");
		String state = faker.address().state();
		CustomerAddress address = new CustomerAddress(flatnumber, apartmentname, streetname, landmark, area, pincode,
				COUNTRY, state);
		return address;
		
	}
	
	private static CustomerProduct generateFakeCustomerProduct() {

		String dop = DayTimeUtil.getTimeWithDaysAgo(10); 
		String serial_number = faker.numerify("##############");
		String popurl = faker.internet().url() ;
		
		
		CustomerProduct  customerproduct = new CustomerProduct(dop, serial_number, serial_number, serial_number, popurl, PRODUCT_ID, MST_MODEL_ID);
		return customerproduct;
		
		
	}
	private static List<Problems> generateFakeProblems() {
		String fakeRemark = faker.lorem().sentence(4);
		
		int problemid = RANDOM.nextInt(26)+1;		
		Problems problems = new Problems(problemid, fakeRemark);
		
		
		List<Problems> problemList = new ArrayList<Problems>();
		problemList.add(problems);
		return problemList;
		
	}
	
	
	
	public static CreateJobPayload generateFakeCreateJobData() {

		
		
		
		Customer customer = generateFakeCustomerData();
		CustomerAddress address = generateFakeCustomerAddress();
		CustomerProduct customerproduct = generateFakeCustomerProduct();
		List<Problems> problemList = generateFakeProblems();
		createjobPayload = new CreateJobPayload(MST_SERVICE_LOCATION_ID,MST_PLATFORM_ID , MST_WARRANTY_STATUS_ID , MST_OEM_ID, customer, address, customerproduct, problemList)	;
		return createjobPayload;
	}
	
	
	
	public static Iterator<CreateJobPayload> generateFakeCreateJobData(int count)
	{
		
		List<CreateJobPayload> payloadList = new ArrayList<CreateJobPayload>();
		for (int i=0; i<=count;i++) {
			Customer customer = generateFakeCustomerData();
			CustomerAddress address = generateFakeCustomerAddress();
			CustomerProduct customerproduct = generateFakeCustomerProduct();
			List<Problems> problemList = generateFakeProblems();
			createjobPayload = new CreateJobPayload(MST_SERVICE_LOCATION_ID,MST_PLATFORM_ID , MST_WARRANTY_STATUS_ID , MST_OEM_ID, customer, address, customerproduct, problemList)	;
			payloadList.add(createjobPayload);
			
		}
		
		return payloadList.iterator();
	
		
	}
}
