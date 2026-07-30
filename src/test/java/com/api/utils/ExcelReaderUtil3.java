package com.api.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.api.request.model.CreateJobPayload;
import com.dataproviders.bean.CreateJobBean;

public class ExcelReaderUtil3 {
	
	
	public  void CreateJobDemo()  {
		Iterator<CreateJobBean> iterator = ExcelReaderUtil2.loadTestData("TestData/PhoenixTestData.xlsx",
				"CreateJobTestData", CreateJobBean.class);
		CreateJobBean bean;
		CreateJobPayload createjobpayload ;
		List<CreateJobPayload> list = null ;
		
		while (iterator.hasNext()) {
			
			 bean = iterator.next();
			 createjobpayload = CreateJobBeanMapper.mapper(bean);
			 System.out.println(createjobpayload);
		
			}
		
		
	}

}
