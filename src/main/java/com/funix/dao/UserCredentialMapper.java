package com.funix.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.funix.model.User;

public class UserCredentialMapper implements RowMapper<User> {

	@Override
	public User mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		String email = resultSet.getString("email");
		String password = resultSet.getString("password");
		return new User(email, password);
	}

}
