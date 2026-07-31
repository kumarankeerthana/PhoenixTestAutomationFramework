package com.database.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.api.request.model.CreateJobPayload;
import com.api.utils.CreateJobBeanMapper;
import com.dataproviders.bean.CreateJobBean;

public class DaoDemoRunner {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		List<CreateJobBean> beanList = CreateJobPayloadDataDao.getCreateJobPayLoadData();
		List<CreateJobPayload> payloadList = new ArrayList<CreateJobPayload>();
		
		for(CreateJobBean b : beanList) {
			CreateJobPayload payload = CreateJobBeanMapper.mapper(b);
			payloadList.add(payload);
		}
		
		for(CreateJobPayload p : payloadList) {
			System.out.println(p);
		}
		
	

	}

}
