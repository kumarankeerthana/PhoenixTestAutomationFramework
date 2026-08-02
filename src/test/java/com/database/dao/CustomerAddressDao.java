package com.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.database.DatabaseManager;
import com.database.model.CustomerAddressDBModel;

public class CustomerAddressDao {

	private CustomerAddressDao() {
	}

	private static final String CUSTOMER_ADDRESS_QUERY = """
			select * from tr_customer_address where id=?
			""";

	public static CustomerAddressDBModel getCustomerAddressInfo(int customerAddressId) {

		CustomerAddressDBModel customerAddressDBModel = null;
		Connection conn;
		PreparedStatement preparedStatement;
		ResultSet resultSet;
		try {
			conn = DatabaseManager.getConnection();
			preparedStatement = conn.prepareStatement(CUSTOMER_ADDRESS_QUERY);
			preparedStatement.setInt(1, customerAddressId);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {

				customerAddressDBModel = new CustomerAddressDBModel(resultSet.getInt("id"),resultSet.getString("flat_number"),
						resultSet.getString("apartment_name"), resultSet.getString("street_name"),
						resultSet.getString("landmark"), resultSet.getString("area"),
						resultSet.getString("pincode"), resultSet.getString("country"),
						resultSet.getString("state"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return customerAddressDBModel;
	}
}