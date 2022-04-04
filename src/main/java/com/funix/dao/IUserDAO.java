package com.funix.dao;

import java.util.List;

import com.funix.model.User;
import com.funix.model.UserFilter;

public interface IUserDAO {
	void create(User newUser);
	User getUser(int userID);
	void enableUserStatus(int userID);
	List<User> getManyUsers(UserFilter userFilter);
	void update(int userID, User newUser);
	void delete(String userIDs);
}
