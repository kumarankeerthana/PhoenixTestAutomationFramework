package com.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.database.DatabaseManager;
import com.database.model.CustomerProductDBModel;

public class CustomerProductDao {
	
	private static final String CUSTOMER_PRODUCT_QUERY = """
			
			select * from tr_customer_product where id=?
			
			""" ; 
	
	
	public static CustomerProductDBModel getCustomerProductInfo(int customer_product_id) {
		CustomerProductDBModel customerProductDBModel = null;
		Connection conn;
		PreparedStatement preparedStatement;
		ResultSet resultSet;
		try {
			conn = DatabaseManager.getConnection();
			preparedStatement = conn.prepareStatement(CUSTOMER_PRODUCT_QUERY);
			preparedStatement.setInt(1, customer_product_id);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {

				customerProductDBModel = new CustomerProductDBModel(resultSet.getInt("id"),
						resultSet.getInt("tr_customer_id"), resultSet.getInt("mst_model_id"),
						resultSet.getString("dop"), resultSet.getString("popurl"),
						resultSet.getString("imei2"), resultSet.getString("imei1"),
						resultSet.getString("serial_number"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return customerProductDBModel;
	}

}
