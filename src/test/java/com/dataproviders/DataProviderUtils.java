package com.dataproviders;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.testng.annotations.DataProvider;

import com.api.request.model.CreateJobPayload;
import com.api.request.model.UserCredentials;
import com.api.utils.CreateJobBeanMapper;
import com.api.utils.CsvReaderUtil;
import com.api.utils.ExcelReaderUtil2;
import com.api.utils.FakerDataGenerator;
import com.api.utils.JsonReaderUtil;
import com.dataproviders.bean.CreateJobBean;
import com.dataproviders.bean.UserBean;

public class DataProviderUtils {

	/*
	 * Data Provider needs to return something it can return a 1d or 2d array or a
	 * Iterator<> ===> Best choice because csvreader returns list of string array
	 */

	@DataProvider(name = "LoginAPIDataProvider", parallel = true)
	public static Iterator<UserBean> loginAPIDataProvider() {
		return CsvReaderUtil.loadCsv("TestData/LoginCreds.csv", UserBean.class);
	}
	
	
	@DataProvider(name = "LoginAPIJSONDataProvider", parallel = true)
	public static Iterator<UserCredentials> loginAPIJSONDataProvider() {
		return JsonReaderUtil.loadJSON("TestData/LoginCredsJSON.json.json", UserCredentials[].class);
	}
	
	@DataProvider(name = "LoginAPIExcelDataProvider", parallel = true)
	public static Iterator<UserBean> loginAPIExcelDataProvider() {
		return ExcelReaderUtil2.loadTestData("TestData/PhoenixTestData.xlsx", "LoginTestData", UserBean.class);
	}

	@DataProvider(name = "CreateJobDataProvider", parallel = true)
	public static Iterator<CreateJobPayload> createJobDataProvider() {
		Iterator<CreateJobBean> createjobiterator = CsvReaderUtil.loadCsv("TestData/CreateJobData.csv",
				CreateJobBean.class);

		List<CreateJobPayload> payloadlist = new ArrayList<CreateJobPayload>();
		while (createjobiterator.hasNext()) {

			CreateJobBean tempbean = createjobiterator.next();
			CreateJobPayload temppayload = CreateJobBeanMapper.mapper(tempbean);
			payloadlist.add(temppayload);

		}
		
		return payloadlist.iterator();

	}
	
	
	@DataProvider(name = "CreateJobAPIJSONDataProvider", parallel = true)
	public static Iterator<CreateJobPayload> createJobAPIJSONDataProvider() {
		return JsonReaderUtil.loadJSON("TestData/CreateJobAPIData.json", CreateJobPayload[].class);
	}
	
	@DataProvider(name = "CreateJobAPIFakerDataProvider", parallel = true)
	public static Iterator<CreateJobPayload> createJobFakeDataProvider(){ 
		String fakerCount = System.getProperty("fakerCount", "5");
		int fakerCountInt = Integer.parseInt(fakerCount);
		Iterator<CreateJobPayload> payloadIterator = FakerDataGenerator.generateFakeCreateJobData(fakerCountInt);
		return payloadIterator;
		
	}

}
