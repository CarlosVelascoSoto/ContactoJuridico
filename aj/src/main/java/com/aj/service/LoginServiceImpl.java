package com.aj.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aj.dao.LoginDAO;
import com.aj.utility.UserDTO;

@Service("loginService")
@Transactional
public class LoginServiceImpl implements LoginService{
	@Autowired
	private LoginDAO loginDAO;

	public UserDTO checkLogin(String userName, String userPassword){
		return loginDAO.checkLogin(userName, userPassword);
	}
}