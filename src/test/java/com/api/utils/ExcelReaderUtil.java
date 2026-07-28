package com.api.utils;

import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReaderUtil {

	public static void main(String[] args) throws IOException {

		InputStream is = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("TestData/PhoenixTestData.xlsx");
		XSSFWorkbook myWorkBook = new XSSFWorkbook(is);
		XSSFSheet mysheet = myWorkBook.getSheet("LoginTestData");
		XSSFRow myRow;
		XSSFCell myCell;
		
		XSSFRow myrow = mysheet.getRow(1);
		XSSFCell mycell = myrow.getCell(0);
		
		System.out.println(mycell);
		
		int lastRowIndex = mysheet.getLastRowNum();
		XSSFRow rowheader = mysheet.getRow(0);
		int lastColumnIndex = (rowheader.getLastCellNum()-1); // this one returns cell number 
		// we need index hence -1.
		
		for (int rowindex =0; rowindex <=lastRowIndex;rowindex++) {
			for (int colindex =0; colindex<=lastColumnIndex;colindex++) {
				myRow = mysheet.getRow(rowindex);
				myCell = myRow.getCell(colindex);
				System.out.print(myCell +" ");
			}
			System.out.println(" ");
		}
		
		
		
		
		

	}

}
