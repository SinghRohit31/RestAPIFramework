package com.practice.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.practice.businessfunctions.ParseJSONResponse;

import io.restassured.response.Response;

public class GenerateReports {

	public static String createRunReports() {

		DateFormat date = new SimpleDateFormat("MMM-dd-yyyy HH.mm.ss");
		String dateTime = date.format(new Date());

		String resultFolder = Constants.RESULTPATH + "/Result" + "__" + dateTime;
		File file = new File(resultFolder);
		file.mkdir();

		return resultFolder;

	}

	public static void genReport(String TCID, String TCScenarios, HashMap<String, String> runtimeData, String TCDesc,
			String folderPath, Response response, HashMap<String, String> inputData, String requestBody,
			String UniqueData) throws IOException {
		XSSFWorkbook wb;
		XSSFSheet sh;
		XSSFRow row = null;

		File file = new File(folderPath + "\\RESULTS.xlsx");

		XSSFFont font, fontPass, fontFail;
		XSSFCellStyle style;

		if (file.exists()) {
			FileInputStream io = new FileInputStream(file);
			wb = new XSSFWorkbook(io);
			sh = wb.getSheetAt(0);
			int nextRow = sh.getLastRowNum() + 1;
			row = sh.createRow(nextRow);
		} else {
			wb = new XSSFWorkbook();
			sh = wb.createSheet();
			font = wb.createFont();
			font.setBold(true);
			XSSFRow headerRow = sh.createRow(0);
			headerRow.createCell(0).setCellValue("TCID");
			headerRow.createCell(1).setCellValue("Test Scenario");
			headerRow.createCell(2).setCellValue("TC Name");
			headerRow.createCell(3).setCellValue("TC Description");
			headerRow.createCell(4).setCellValue("Unique ID");
			headerRow.createCell(5).setCellValue("Response Code");
			headerRow.createCell(6).setCellValue("Status Line");
			headerRow.createCell(7).setCellValue("Response Status");
			headerRow.createCell(8).setCellValue("Response Success");
			headerRow.createCell(9).setCellValue("Additional Info");
			headerRow.createCell(10).setCellValue("Reason Code");

			if (inputData.containsKey("Status")) {
				headerRow.createCell(11).setCellValue("Status");
			}
			row = sh.createRow(1);
			style = wb.createCellStyle();
			style.setFillForegroundColor(IndexedColors.PINK.getIndex());
			style.setFillPattern(style.getFillPattern().SOLID_FOREGROUND);
			style.setFont(font);

			for (int i = 0; i < headerRow.getLastCellNum(); i++) {
				XSSFCell cell = headerRow.getCell(i);
				cell.setCellStyle(style);
			}
		}

		row.createCell(0).setCellValue(TCID);
		row.createCell(1).setCellValue(TCScenarios);
		row.createCell(2).setCellValue(runtimeData.get("TestName"));
		row.createCell(3).setCellValue(runtimeData.get("TestDesc"));
		row.createCell(4).setCellValue(UniqueData);
		row.createCell(5).setCellValue(String.valueOf(response.getStatusCode()));
		row.createCell(6).setCellValue(response.getStatusLine());
		row.createCell(7).setCellValue(ParseJSONResponse.getJSONResponse(response, "status"));
		row.createCell(8).setCellValue(ParseJSONResponse.getJSONResponse(response, "success"));
		row.createCell(9).setCellValue(ParseJSONResponse.getJSONResponse(response, "additional Info"));
		row.createCell(10).setCellValue(ParseJSONResponse.getJSONResponse(response, "reason code"));
		
		FileOutputStream out= new FileOutputStream(file);
		wb.write(out);
		
		
		FileWriter reqWriter= new FileWriter(folderPath + "\\" + TCScenarios + "_request.json");
		reqWriter.write(requestBody);
		reqWriter.close();
		
		FileWriter resWriter= new FileWriter(folderPath + "\\" + TCScenarios + "_response.json");
		resWriter.write(response.asString());
		resWriter.close();
	}

}
