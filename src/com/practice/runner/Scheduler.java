package com.practice.runner;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.practice.businessfunctions.DriverScript;
import com.practice.utils.Constants;
import com.practice.utils.GenerateReports;

public class Scheduler {
	
	static int num=1;
	public static Method method[];
	public static DriverScript driver;
	
	public Scheduler() {
		driver= new DriverScript();
		method=driver.getClass().getMethods();
	}

	public static void main(String[] args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException {
		Scheduler scheduler = new Scheduler();
		scheduler.executeScheduler();
	}
	
	
	public static void executeScheduler() throws IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		String execution_flg="";
		String TCID,TCScenario,TCName,TCDesc,DBQuery;
		LinkedHashMap<Integer, String> datasetMap;
		
		String folderPath = GenerateReports.createRunReports();
		
		File file = new File(Constants.SCHEDULERPATH);
		FileInputStream fi= new FileInputStream(file);
		XSSFWorkbook wb = new XSSFWorkbook(fi);
		XSSFSheet sheet= wb.getSheet("RunSheet");
		
		
		for (int i = 1; i < sheet.getLastRowNum()+1; i++) {
			XSSFRow row=sheet.getRow(i);
			if(!(row==null)) {
				XSSFCell cell=row.getCell(1);
				if(!(cell==null)) {
					execution_flg=cell.getStringCellValue().toUpperCase().trim();
				}
			}else {
				continue;
			}
			
			if(execution_flg.equals("Y")) {
				String testCase = row.getCell(2).getStringCellValue();
				TCID =row.getCell(0).getStringCellValue();
				TCScenario = row.getCell(2).getStringCellValue();
				TCDesc= row.getCell(3).getStringCellValue();
				
				LinkedHashMap<Integer, HashMap<String, String>> dataset =getTestData(TCScenario);
				
				for (Entry<Integer, HashMap<String, String>> map : dataset.entrySet()) {
					for (int k = 0; k < method.length; k++) {
						if(testCase.equalsIgnoreCase(method[k].getName())) {
							method[k].invoke(driver, folderPath,String.valueOf("TC_0"+map.getKey()),TCScenario,TCDesc,dataset.get(map.getKey()));  // Need to understand that
							break;
						}
						
					}
					
				
				}
			}
		}
	}
	
	public static LinkedHashMap<Integer, HashMap<String, String>> getTestData(String TCScenario) {
		File file;
		FileInputStream fileInput;
		XSSFWorkbook wrkbk;
		XSSFSheet sh;
		XSSFRow row;
		XSSFCell cell_runFlag;
		
		
		LinkedHashMap<Integer, HashMap<String,String>> TestDataset=new LinkedHashMap<>();
		
		file = new File(Constants.TESTDATASHEET);
		try {
			fileInput= new FileInputStream(file);
			wrkbk= new XSSFWorkbook(fileInput);
			sh=wrkbk.getSheet(TCScenario);
			XSSFRow header=sh.getRow(0);
			for (int i = 1; i <= sh.getLastRowNum(); i++) {
				row = sh.getRow(i);
				cell_runFlag=row.getCell(0);
				
				if(!(cell_runFlag==null)) {
					if(cell_runFlag.getStringCellValue().trim().equalsIgnoreCase("Y")) {
						HashMap<String, String> data= new HashMap<>();
						
						for (int j = 1; j <= row.getLastCellNum(); j++) {
						  XSSFCell  cell=row.getCell(j);
							if(!(cell==null)) {
								cell.setCellType(CellType.STRING);
								data.put(header.getCell(j).getStringCellValue(), cell.getStringCellValue());
							}
							/*
							 * else { //cell.setCellType(CellType.STRING); //
							 * data.put(header.getCell(j).getStringCellValue(), ""); }
							 */
						}
						TestDataset.put(num, data);
						num++;
					}
				
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return TestDataset;
		
		
		
		
	}
	
	

}
