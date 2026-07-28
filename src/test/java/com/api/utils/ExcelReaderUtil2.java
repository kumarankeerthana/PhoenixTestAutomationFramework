package com.api.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.api.request.model.UserCredentials;

public class ExcelReaderUtil2 {

	public static Iterator<UserCredentials> loadTestData(String filepath) {

		InputStream is = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream(filepath);
		XSSFWorkbook myWorkBook = null;
		try {
			myWorkBook = new XSSFWorkbook(is);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		XSSFSheet mysheet = myWorkBook.getSheet("LoginTestData");

		// Read datae login test data -->store it in an arraylist <UserCredentials>
		// know the index of our instance variables

		XSSFRow headerRow = mysheet.getRow(0);

		int userNameIndex = -1;
		int passwordIndex = -1;

		for (Cell cell : headerRow) {

			if (cell.getStringCellValue().trim().equalsIgnoreCase("username")) {
				userNameIndex = cell.getColumnIndex();
			}
			if (cell.getStringCellValue().trim().equalsIgnoreCase("password")) {
				passwordIndex = cell.getColumnIndex();
			}

		}

		int lastRowIndex = mysheet.getLastRowNum();
		XSSFRow rowdata;
		UserCredentials credentials = null;
		List<UserCredentials> userList = new ArrayList<UserCredentials>();

		for (int rowIndex = 1; rowIndex <= lastRowIndex; rowIndex++) {

			rowdata = mysheet.getRow(rowIndex);
			credentials = new UserCredentials(rowdata.getCell(userNameIndex).toString(),
					rowdata.getCell(passwordIndex).toString());
			System.out.println(credentials);
			userList.add(credentials);

		}
		return userList.iterator();
		
		
		
	}

}
