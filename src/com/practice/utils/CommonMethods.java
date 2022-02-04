package com.practice.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

public class CommonMethods {
	
	public static String readFile(String filename) throws IOException {
	BufferedReader br= new BufferedReader(new FileReader(filename));
	try {
		StringBuilder sb = new StringBuilder();
		String line=br.readLine();
		while (line!=null) {
			sb.append(line);
			sb.append("\n");
			line = br.readLine();
		}
		return sb.toString();
	}
	finally {
		br.close();
	}
	}
	
	public static String getAlphanumericString(int size) {
		String alphanumericstring="0123456789" +"a";
		
		StringBuilder sb= new StringBuilder(size);
		
		for (int i = 0; i < size; i++) {
			int index= (int) ((alphanumericstring.length())*Math.random());
			sb.append(alphanumericstring.charAt(index));
		}
		return sb.toString();
	}
	
	public static String getconfigValue(String key) throws IOException {
		FileInputStream input=null;;
		Properties prop=null;
		try {
			input= new FileInputStream(Constants.CONFIGFILEPATH);
			prop = new Properties();
			prop.load(input);
			return prop.getProperty(key);
		}finally {
			input.close();
			prop.clear();
		
		}
		

	}
	
	public static HashMap<String, String> gettagValues() throws IOException {
		HashMap<String,String> tagvalues= new HashMap<>();
	    FileInputStream input= new FileInputStream(Constants.TAGVALUECONFIGPATH);
		Properties prop = new Properties();
		prop.load(input);
		for (Object keys : prop.keySet()) {
			tagvalues.put((String)keys, prop.getProperty((String)(keys)));
		}
		return tagvalues;
	}

}
