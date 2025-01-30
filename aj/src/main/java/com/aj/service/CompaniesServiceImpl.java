package com.aj.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.aj.dao.CompaniesDAO;
import com.aj.model.Companies;

@Service("companyService")
@Transactional
public class CompaniesServiceImpl implements CompaniesService{
	@Autowired
	private CompaniesDAO companiesDAO;
	
	@Override
	public Integer addNewCompany(Companies companyobj){
		return companiesDAO.addNewCompany(companyobj);
	}
	
	@Override
	public List<Companies> getAll(String query){
		return companiesDAO.getAll(query);
	}
	
	@Override
	public Companies getCompanyById(Long getcompanyid){
		return companiesDAO.getCompanyById(getcompanyid);
	}

	@Override
	public void updateCompany(Companies setcompanyobj){
		companiesDAO.updateCompany(setcompanyobj);
	}
	
	@Override
	public void deleteCompany(Long companyid){
		companiesDAO.deleteCompany(companyid);
	}
}