package com.api.constants;

public enum Product {
	
	//IN ENUM CONSTRCTORS ARE PRIVATE 
	NEXUS_2 (1) , PIXEL(2) ;
	
	int code;
	private Product(int code) {
		this.code = code;
	}
	public int getcode() {
		return code;
	}

}
