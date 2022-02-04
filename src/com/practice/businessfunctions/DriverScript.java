package com.practice.businessfunctions;

import java.io.IOException;
import java.util.HashMap;

import com.practice.utils.CommonMethods;
import com.practice.utils.GenerateReports;

import io.restassured.response.Response;

public class DriverScript {

	HashMap<String,String> uniqueData;
	
	
	public void employeeAPI(String folderpath,String TCID , String TCScenario,String TCDesc,HashMap<String,String> runtimeData) throws IOException {
		String endPoints= CommonMethods.getconfigValue("endPoint_employee");
	//	String serviceUrl = CommonMethods.getconfigValue("serviceURL_employee");
		
		uniqueData=new HashMap<>();
		
		uniqueData=GenJsonRequest.genUniqueData();
		
		
		
		String requestBody= GenJsonRequest.employeeAPI(uniqueData, runtimeData);
		System.out.println("Sending Post request on URI " + endPoints);
		Response response = APIMethods.postRequest(requestBody, endPoints);
		
		System.out.println("Response recieved as :  " + response.asPrettyString());
		
		System.out.println("Generating reports for NIKE : " + runtimeData.get("TestCaseName") +"\n");
		GenerateReports.genReport(TCID, TCScenario, runtimeData, TCDesc, folderpath, response, runtimeData, requestBody, requestBody);
		
	}
	
	
	
	
}
