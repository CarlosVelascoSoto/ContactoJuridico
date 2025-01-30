package com.aj.dao;

import com.aj.utility.UserDTO;

public interface LoginDAO{
	public UserDTO checkLogin(String userName, String userPassword);
}