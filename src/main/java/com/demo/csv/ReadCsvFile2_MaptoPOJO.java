package com.demo.csv;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.pojo.UserBean;

public class ReadCsvFile2_MaptoPOJO {

	public static void main(String[] args) throws IOException, Exception {

		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("TestData/LoginCreds.csv");
		InputStreamReader streamreader = new InputStreamReader(is);
		CSVReader reader = new CSVReader(streamreader); // needs a reader object

		CsvToBean<UserBean> csvToBean = new CsvToBeanBuilder(reader).withType(UserBean.class).withIgnoreEmptyLine(true)
				.build();

		List<UserBean> userList = csvToBean.parse();
		System.out.println(userList.get(0).getUsername());
	}
}

