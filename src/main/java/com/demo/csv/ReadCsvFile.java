package com.demo.csv;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import com.opencsv.CSVReader;

public class ReadCsvFile {

	public static void main(String[] args) throws IOException, Exception {
		
		
		//File file = new File("C:\\Users\\keert\\eclipse-workspace\\PhoenixTestAutomationFramework\\src\\main\\resources\\TestData\\LoginCreds.csv");
		// FileReader filereader = new FileReader(file);
		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("TestData/LoginCreds.csv");
		InputStreamReader streamreader = new InputStreamReader(is);
		
		CSVReader reader = new CSVReader(streamreader); //needs a reader object 
		
		List<String[]> datalist = reader.readAll();
		for (String[] dataArray :datalist) {
			for(String data: dataArray) {
				System.out.println(data+" ");
			
			}
			System.out.println(" ");
		}
		
		reader.close();
		

	}

}
