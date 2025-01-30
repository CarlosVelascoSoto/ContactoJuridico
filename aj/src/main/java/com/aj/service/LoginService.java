package com.aj.service;

import com.aj.utility.UserDTO;

public interface LoginService{
	public UserDTO checkLogin(String userName, String userPassword);
}