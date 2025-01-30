package com.aj.dao;

import java.util.List;
import com.aj.model.Companies;

public interface CompaniesDAO{
	public Integer addNewCompany(Companies companyobj); 
	public Companies getCompanyById(Long companyid);
	public void updateCompany(Companies companyobj);
	public void deleteCompany(Long companyid);
	public List<Companies> getAll(String query);
}