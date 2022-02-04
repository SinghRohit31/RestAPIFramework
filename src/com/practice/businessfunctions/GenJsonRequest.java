package com.practice.businessfunctions;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.Random;
import java.util.TimeZone;

import com.practice.utils.CommonMethods;
import com.practice.utils.Constants;

public class GenJsonRequest {
	
	
	public static String employeeAPI(HashMap<String, String> newData, HashMap<String, String> runtimedata) throws IOException {
		String jsonRequestFile= CommonMethods.readFile(Constants.EMPLOYEEJSONPATH);
		jsonRequestFile=replaceRuntimeData(jsonRequestFile, runtimedata);
		return jsonRequestFile;
	}
	
	public static String replaceRuntimeData(String JSONRequest,HashMap<String,String> runtimedata) throws IOException {
		HashMap<String, String>  staticvalues = CommonMethods.gettagValues();
		HashMap<String, String> temp= new HashMap<>();
		
		temp.putAll(runtimedata);
		
		
		if(!(runtimedata.isEmpty())) {
			for (Entry<String, String> map : runtimedata.entrySet()) {
				if(staticvalues.containsKey(map.getKey())) {
					if(!(runtimedata.get(map.getKey()).equals("-"))) {
						temp.remove(map.getKey());
						temp.put(staticvalues.get(map.getKey()), map.getValue());
					}
					else {
						temp.remove(map.getKey());
					}
				}
			}
			for ( Entry<String, String> map2 : temp.entrySet()) {
				if (JSONRequest.contains(map2.getKey())) {
					JSONRequest= JSONRequest.replaceAll(map2.getKey().trim(), map2.getValue());
				}
			}
		}
		
		return JSONRequest;
	}
	
	public static HashMap<String, String> genUniqueData() {
		HashMap<String, String> uniqueValues= new HashMap<String, String>();
		String requestID = CommonMethods.getAlphanumericString(8) + "-" + CommonMethods.getAlphanumericString(4);
		String eventID = CommonMethods.getAlphanumericString(8) + "-" + CommonMethods.getAlphanumericString(4);
		uniqueValues.put("requestID", requestID);
		uniqueValues.put("eventID", eventID);
		
		SimpleDateFormat sdf =new SimpleDateFormat("yyyy-mm-dd'T'HH:mm:ss.SSSZ",Locale.US);
		sdf.setTimeZone(TimeZone.getTimeZone("EST"));
		
		uniqueValues.put("CurrentDate", sdf.format(new Date()));
		
		Random random= new Random();
		uniqueValues.put("EndToEndID", "E2EID" + random.nextInt(100000000));
		
		return uniqueValues;
		
	}
	
	
	
	
}
