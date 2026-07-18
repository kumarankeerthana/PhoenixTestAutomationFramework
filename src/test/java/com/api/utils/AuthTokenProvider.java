package com.api.utils;

import static com.api.constants.Roles.ENG;
import static com.api.constants.Roles.FD;
import static com.api.constants.Roles.QC;
import static com.api.constants.Roles.SUP;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;
import com.api.constants.Roles;
import com.api.pojo.UserCredentials;
import io.restassured.http.ContentType;

public class AuthTokenProvider {

	public static String getToken(Roles role) throws IOException {

		String token = null;
		UserCredentials credentials = null;

		if (role == FD) {
			credentials = new UserCredentials("iamfd", "password");
		} else if (role == SUP) {
			credentials = new UserCredentials("iamsup", "password");

		} else if (role == ENG) {
			credentials = new UserCredentials("iameng", "password");

		} else if (role == QC) {
			credentials = new UserCredentials("iamqc", "password");

		} else {
			System.out.println("Please Enter the correct Role ---> to get token ");
		}

		token = given().baseUri(ConfigManager.getProperty("BASE_URI"))
				.contentType(ContentType.JSON)
				.body(credentials)
				
				.when().post("login")
				
				.then()
				.log().ifValidationFails().statusCode(200).body("message", equalTo("Success")).extract().jsonPath()
				.getString("data.token");

		return token;

	}

}
