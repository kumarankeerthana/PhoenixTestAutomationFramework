package com.dataproviders;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.testng.annotations.DataProvider;

import com.api.dataproviders.bean.CreateJobBean;
import com.api.dataproviders.bean.UserBean;
import com.api.request.model.CreateJobPayload;
import com.api.utils.CreateJobBeanMapper;
import com.api.utils.CsvReaderUtil;

public class DataProviderUtils {

	/*
	 * Data Provider needs to return something it can return a 1d or 2d array or a
	 * Iterator<> ===> Best choice because csvreader returns list of string array
	 */

	@DataProvider(name = "LoginAPIDataProvider", parallel = true)
	public static Iterator<UserBean> loginAPIDataProvider() {
		return CsvReaderUtil.loadCsv("TestData/LoginCreds.csv", UserBean.class);
	}

	@DataProvider(name = "CreateJobDataProvider")
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

}
