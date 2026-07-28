package com.api.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.poiji.bind.Poiji;

public class ExcelReaderUtil2 {
	
	private ExcelReaderUtil2() {
		
	}

	public static <T> Iterator<T> loadTestData(String filepath, String sheetname, Class<T> clazz) {

		InputStream is = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream(filepath);
		XSSFWorkbook myWorkBook = null;
		try {
			myWorkBook = new XSSFWorkbook(is);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		XSSFSheet mysheet = myWorkBook.getSheet(sheetname);
		
		List<T> dataList = Poiji.fromExcel(mysheet, clazz);
		return dataList.iterator();
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		

		/* Read datae login test data -->store it in an arraylist <UserCredentials>
		 know the index of our instance variables

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
		
		*/
		
	}

}
