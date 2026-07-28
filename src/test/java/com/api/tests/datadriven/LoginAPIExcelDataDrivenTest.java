package com.api.tests.datadriven;

import static com.api.utils.SpecUtil.requestSpec;
import static com.api.utils.SpecUtil.responseSpec_OK;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import java.io.IOException;

import org.testng.annotations.Test;

import com.api.request.model.UserCredentials;

import io.restassured.module.jsv.JsonSchemaValidator;

public class LoginAPIExcelDataDrivenTest {

	

	@Test(description = "Verify if login api is working for User iamfd", groups = { "api", "regression", "datadriven" },
			dataProviderClass = com.dataproviders.DataProviderUtils.class, 
			dataProvider = "LoginAPIExcelDataProvider")
	public void loginAPITest(UserCredentials user) throws IOException {

		given().spec(requestSpec(user)).and()

				.when().post("login")

				.then().spec(responseSpec_OK()).body("message", equalTo("Success"))
				.body("data", notNullValue())
				.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/LoginAPIRequestSchema.json"));

	}

}
