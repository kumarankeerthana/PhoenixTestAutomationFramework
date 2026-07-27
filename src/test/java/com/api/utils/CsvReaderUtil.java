package com.api.utils;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

public class CsvReaderUtil {

	/*
	 * constructor is private static methods JOB: - HELPS TO READ CSV AND MAP IT TO
	 * A BEAN
	 */
	private CsvReaderUtil() {
		//prevent object creation

	}

	public static <T> Iterator<T> loadCsv(String pathOfCSVFile, Class <T> bean) {

		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(pathOfCSVFile);
		InputStreamReader streamreader = new InputStreamReader(is);
		CSVReader reader = new CSVReader(streamreader); // needs a reader object

		CsvToBean<T> csvToBean = new CsvToBeanBuilder(reader).
				withType(bean).
				withIgnoreEmptyLine(true)
				.build();

		List<T> list =  csvToBean.parse();
		return list.iterator();
	}

}
