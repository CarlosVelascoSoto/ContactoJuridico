package com.aj.dao;

import java.util.List;
import com.aj.model.Company;
import com.aj.model.Users;

public interface UserDAO{
	public List<Users> getAllUserList();
	public long addNewUser(Users user);
	public Users getUserById(long userId);
	public void updateUserDetails(Users user);
	public void deleteUser(long userId);
	public Users getProfile(String email);
	public List<Users> getAll(String query);
	public List<Company> getAllCompanyList();
	public Integer addNewCompany(Company company);
}