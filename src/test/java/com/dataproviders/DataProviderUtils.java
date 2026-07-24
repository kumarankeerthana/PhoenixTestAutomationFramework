package com.dataproviders;

import java.util.Iterator;

import org.testng.annotations.DataProvider;

import com.api.dataproviders.bean.UserBean;
import com.api.utils.CsvReaderUtil;

public class DataProviderUtils {
	
	/*
	 * Data Provider needs to return something 
	 * it can return a 1d or 2d array 
	 * or a Iterator<>  ===> Best choice because csvreader returns list of string array 
	 */
	
	@DataProvider(name ="LoginAPIDataProvider", parallel = true)
	public static Iterator<UserBean> loginAPIDataProvider() {
		return CsvReaderUtil.loadCsv("TestData/LoginCreds.csv");	
	}

}
