package com.practice.businessfunctions;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class APIMethods {

	public static Response postRequest(String requestBody, String endpoints) {

		Response response = null;

		try {
			System.out.println("Request Body to be Posted : " + requestBody);

			RestAssured.baseURI = endpoints;
		//	RestAssured.useRelaxedHTTPSValidation();
			RequestSpecification request = RestAssured.given().contentType(ContentType.JSON)
					.header("Content-type", "application/json").body(requestBody);

			System.out.println("Posting data to server.......");

			response = request.post(endpoints);

			System.out.println("Response is recieved .. " + response.asPrettyString());
			return response;

		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return response;
		}

	}

}
