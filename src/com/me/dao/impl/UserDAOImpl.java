package com.me.dao.impl;

import com.me.bean.User;
import com.me.dao.UserDAO;

public class UserDAOImpl extends BaseDAO<User> implements UserDAO {

	@Override
	public User getUser(String username) {
		String sql = "SELECT userId, username,password, accountId " +
				"FROM userinfo WHERE username = ?";
		return query(sql, username); 
	}

}
