package com.dataproviders.bean;

import com.opencsv.bean.CsvBindByName;
import com.poiji.annotation.ExcelCellName;

public class UserBean {
	
	@CsvBindByName(column ="username")
	@ExcelCellName("username")
	private String username;
	@CsvBindByName(column ="password")
	@ExcelCellName("password")
	private String password;
	
	public UserBean() {
		//default constructor 
	}
	@Override
	public String toString() {
		return "UserPojo [username=" + username + ", password=" + password + "]";
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	

}
