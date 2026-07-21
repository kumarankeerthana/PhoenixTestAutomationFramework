package com.api.utils;
import com.api.constants.*;

import java.io.IOException;

import org.hamcrest.Matchers;

import com.api.pojo.UserCredentials;

import static com.api.utils.ConfigManager.*;

import io.restassured.builder.*;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;


import static io.restassured.http.ContentType.*;
public class SpecUtil {
	// we will have only static methods
	
	public static RequestSpecification requestSpec() throws IOException {
		/*to take care of common request section 
		 * Request Specification --> configure our request
		 * Request Spec Builder 
		 */
		
		RequestSpecification request = new RequestSpecBuilder()
		.setBaseUri(getProperty("BASE_URI"))
		.setContentType(JSON)
		.setAccept(JSON)
		.log(LogDetail.URI)
		.log(LogDetail.METHOD)
		.log(LogDetail.HEADERS)
		.log(LogDetail.BODY)
		.build(); 
		
		return request;
	}
	
	
	
	
	//method for post put and patch ---> body is attached 
	// we are going to create a overloaded methods 
	
	public static RequestSpecification requestSpec(Object usercreds) throws IOException { // parent ref_var = new child()
                                                                                          //; --> we did loose coupling 
		                                                                                  // we have to create a pojo  class for other roles and new methods 
		                                                                                  // or object creation will not happen 
		RequestSpecification requestSpecification  = new RequestSpecBuilder()
				.setBaseUri(getProperty("BASE_URI"))
				.setContentType(JSON)
				.setAccept(JSON)
				.setBody(usercreds)
				.log(LogDetail.URI)
				.log(LogDetail.METHOD)
				.log(LogDetail.HEADERS)
				.log(LogDetail.BODY)
				.build(); 
				
				return requestSpecification;
		
		
		
	}
	
	public static RequestSpecification requestSpecWithAuth(Roles role) throws IOException {
		RequestSpecification requestSpecification  = new RequestSpecBuilder()
				.setBaseUri(getProperty("BASE_URI"))
				.setContentType(JSON)
				.setAccept(JSON)
				.addHeader("Authorization", AuthTokenProvider.getToken(role))
				.log(LogDetail.URI)
				.log(LogDetail.METHOD)
				.log(LogDetail.HEADERS)
				.log(LogDetail.BODY)
				.build(); 
				
				return requestSpecification;
		
		
	}
	
	public static RequestSpecification requestSpecWithAuth(Roles role, Object payload) throws IOException {
		RequestSpecification requestSpecification  = new RequestSpecBuilder()
				.setBaseUri(getProperty("BASE_URI"))
				.setContentType(JSON)
				.setAccept(JSON)
				.addHeader("Authorization", AuthTokenProvider.getToken(role))
				.setBody(payload)
				.log(LogDetail.URI)
				.log(LogDetail.METHOD)
				.log(LogDetail.HEADERS)
				.log(LogDetail.BODY)
				.build(); 
				
				return requestSpecification;
		
		
	}
	
	public static ResponseSpecification responseSpec_OK() {
		
		ResponseSpecification responseSpecification = new ResponseSpecBuilder()
		.log(LogDetail.ALL)
		.expectContentType(JSON)
		.expectStatusCode(200)
		.expectResponseTime(Matchers.lessThan(6000L))
		.build();
		
		return responseSpecification ;
		
	}
	
	public static ResponseSpecification responseSpec_JSON(int statuscode) {
		
		ResponseSpecification responseSpecification = new ResponseSpecBuilder()
		.log(LogDetail.ALL)
		.expectContentType(JSON)
		.expectStatusCode(statuscode)
		.expectResponseTime(Matchers.lessThan(6000L))
		.build();
		
		return responseSpecification ;
		
	}
	
	public static ResponseSpecification responseSpec_TEXT(int statuscode) {
		
		ResponseSpecification responseSpecification = new ResponseSpecBuilder()
		.log(LogDetail.ALL)
		.expectStatusCode(statuscode)
		.expectResponseTime(Matchers.lessThan(6000L))
		.build();
		
		return responseSpecification ;
		
	}

}
