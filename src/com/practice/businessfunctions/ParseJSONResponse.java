package com.practice.businessfunctions;

import java.util.Map;

import groovy.json.JsonParser;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class ParseJSONResponse {

	public static String verifyStatusCode(Response response, int code) {
		String result = null;
		try {
			if (response.getStatusCode() == code) {
				result = "PASS Status Code : " + response.getStatusCode();
				return result;
			} else {
				result = "FAIL Status code :" + code + "Actual Status code: " + response.getStatusCode();
				return result;
			}
		} catch (Exception e) {
			return "FAIL " + e.getMessage();
		}
	}

	public static String verifyStatusCode(Response response, String statusLine) {
		String result = null;
		try {
			if (response.getStatusLine().equals(statusLine)) {
				result = "PASS Status Line : " + response.getStatusLine();
				return result;
			} else {
				result = "FAIL " + "Expected Status Line is :  " + response.getStatusLine() + "Actual Status line is : "
						+ statusLine;
				return result;
			}
		} catch (Exception e) {
			return "FAIL " + e.getMessage();
		}
	}

	public static String verifyJSONResponse(Response response, String name, String value) {
		String result = null;
		String actualValue = null;
		String[] roottags = { "metadata", "result" };

		try {
			JsonPath parser = new JsonPath(response.asString());

			for (int i = 0; i < roottags.length; i++) {
				Map<String, String> rootElement = parser.get(roottags[i]);
				if (rootElement.containsKey(name)) {
					actualValue = String.valueOf(rootElement.get(name));
					if (actualValue.equals(value)) {
						result = "PASS" + name + ": " + actualValue;
						return result;
					} else {
						result = "FAIL Expected value is " + value + "Actual Value is : " + actualValue;
						return result;
					}

				} else {
					continue;
				}

			}

			if (result == null) {

				return "Key: " + name + "not found in JSON Response ";

			}

		} catch (Exception e) {
			return "FAIL" + e.getMessage();
		}
		return actualValue;
	}
	
	
	public static String getJSONResponse(Response response, String name) {
		String actualValue = null;
		String[] roottags = { "metadata", "result" };
		
		try {
			JsonPath parser=  new JsonPath(response.asString());
			for (int i = 0; i < roottags.length; i++) {
				Map<String, String> rootelement = parser.get(roottags[i]);
				if (rootelement.containsKey(name)) {
					actualValue=String.valueOf(rootelement.get(name));
				}else {
					continue;
				}
				
			}
			if (actualValue==null) {
				return "NA";
			}
			
		} catch (Exception e) {
			return "FAIL" +e.getMessage();
		}
		return actualValue;
	}
	
	public static void verifyEmployeeAPIResponse(Response response) {
		 response.toString();
		 response.jsonPath().getMap("");
		 
		 
		
	}
	
	
	
	

}
