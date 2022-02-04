package com.practice.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Constants {
	
	public static String env;
	public static Properties prop;
	
	public static final String TAGVALUECONFIGPATH=System.getProperty("user.dir") + "\\src\\com\\practice\\config\\tagValues.properties";
	public static final String EMPLOYEEJSONPATH =System.getProperty("user.dir") + "\\Basefile\\employee.json";;
	public static final String RESULTPATH=System.getProperty("user.dir") + "\\Results";
	public static final String SCHEDULERPATH=System.getProperty("user.dir") +"\\TestData\\Scheduler.xlsx";
	
	static {
		File file;;
		FileInputStream fi,fienv;
		
		try {
			file=new File(System.getProperty("user.dir") + "\\src\\com\\practice\\config\\config.properties");
			fi= new FileInputStream(file);
			prop= new Properties();
			prop.load(fi);
			env =prop.getProperty("Environment");
			System.out.println("Execution started in  " + env + "Environement");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			
			fienv=new FileInputStream(new File(System.getProperty("user.dir") + "\\src\\com\\practice\\config\\config_"+env+".properties"));
			prop.load(fienv);
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}
	
	public static final String CONFIGFILEPATH=System.getProperty("user.dir") + "\\src\\com\\practice\\config\\config_"+env+".properties";
	public static final String TESTDATASHEET= System.getProperty("user.dir") +"\\TestData\\TestData_"+env+ ".xlsx";
	
	

}
