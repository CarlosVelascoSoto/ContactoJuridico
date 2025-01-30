package com.aj.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aj.dao.UserDAO;
import com.aj.model.Company;
import com.aj.model.Users;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService{
	@Autowired
	private UserDAO userDAO;

	public void setUserDAO(UserDAO userDAO){
		this.userDAO=userDAO;
	}

	@Override
	public long addNewUser(Users user){
		return userDAO.addNewUser(user);
	}

	@Override
	public List<Users> getAllUserList(){
		return userDAO.getAllUserList();
	}

	@Override
	public Users getUserById(long userId){
		return userDAO.getUserById(userId);
	}

	@Override
	public void updateUserDetails(Users user){
		userDAO.updateUserDetails(user);
	}

	@Override
	public void deleteUser(long userId){
		userDAO.deleteUser(userId);
	}

	@Override
	public Users getProfile(String email){
		return userDAO.getProfile(email);
	}

	public List<Users> getAll(String query){
		return userDAO.getAll(query);
	}

	@Override
	public List<Company> getAllCompanyList(){
		return userDAO.getAllCompanyList();
	}

	@Override
	public Integer addNewCompany(Company company){
		return userDAO.addNewCompany(company);
	}
}