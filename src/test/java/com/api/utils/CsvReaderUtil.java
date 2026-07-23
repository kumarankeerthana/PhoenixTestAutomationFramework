package com.api.utils;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.api.dataproviders.bean.*;

public class CsvReaderUtil {

	/*
	 * constructor is private static methods JOB: - HELPS TO READ CSV AND MAP IT TO
	 * A BEAN
	 */
	private CsvReaderUtil() {
		//prevent object creation

	}

	public static void loadCsv(String pathOfCSVFile) {

		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(pathOfCSVFile);
		InputStreamReader streamreader = new InputStreamReader(is);
		CSVReader reader = new CSVReader(streamreader); // needs a reader object

		CsvToBean<UserBean> csvToBean = new CsvToBeanBuilder(reader).withType(UserBean.class).withIgnoreEmptyLine(true)
				.build();

		List<UserBean> userList = csvToBean.parse();
		System.out.println(userList.get(0).getUsername());
	}

}
