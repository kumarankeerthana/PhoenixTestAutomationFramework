package com.api.utils;

import java.util.ArrayList;
import java.util.Iterator;

import com.api.request.model.CreateJobPayload;
import com.dataproviders.bean.CreateJobBean;

public class Demo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Iterator<CreateJobBean> iterator =  CsvReaderUtil.loadCsv("TestData/CreateJobData.csv", CreateJobBean.class);
		ArrayList <CreateJobPayload> payloadlist = new ArrayList<>();
		
		while(iterator.hasNext()) {
			
			CreateJobBean c = iterator.next();
			CreateJobPayload payload = CreateJobBeanMapper.mapper(c);
			System.out.println(payload);
			payloadlist.add(payload);
			
		}

	}

}
