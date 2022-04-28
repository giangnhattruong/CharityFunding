package com.funix.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import org.springframework.jdbc.core.RowMapper;

import com.funix.model.User;
import com.funix.service.NullConvert;

public class UserSimpleMapper implements RowMapper<User> {

	@Override
	public User mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		int userID = resultSet.getInt("userID");
		String email = resultSet.getString("email");
		String password = resultSet.getString("password");
		String fullname = resultSet.getString("fullname");
		String address = resultSet.getString("address");
		String phone = resultSet.getString("phone");
		int userRole = resultSet.getInt("userRole");
		int userStatus = resultSet.getInt("userStatus");
		LocalDate dateCreated = NullConvert
				.toLocalDate(resultSet.getDate("dateCreated"));
		
		return new User(userID, email, password, fullname, 
				address, phone, userRole, userStatus, dateCreated);
	}

}
