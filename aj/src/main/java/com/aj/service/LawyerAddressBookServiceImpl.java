package com.aj.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.aj.dao.LawyerAddressBookDAO;
import com.aj.model.LawyerAddressBook;

@Service("lawyeraddressService")
@Transactional
public class LawyerAddressBookServiceImpl implements LawyerAddressBookService{
	@Autowired
	private LawyerAddressBookDAO lawyeraddressbookDAO;
	
	@Override
	public Integer addNewLawyerAddress(LawyerAddressBook lawyeraddressobj){
		return lawyeraddressbookDAO.addNewLawyerAddress(lawyeraddressobj);
	}
	
	@Override
	public List<LawyerAddressBook> getAll(String query){
		return lawyeraddressbookDAO.getAll(query);
	}
	
	@Override
	public LawyerAddressBook getLawyerAddressById(Long getlawyeraddressid){
		return lawyeraddressbookDAO.getLawyerAddressById(getlawyeraddressid);
	}

	@Override
	public void updateLawyerAddress(LawyerAddressBook lawyeraddressobj){
		lawyeraddressbookDAO.updateLawyerAddress(lawyeraddressobj);
	}
	
	@Override
	public void deleteLawyerAddress(Long lawyeraddressid){
		lawyeraddressbookDAO.deleteLawyerAddress(lawyeraddressid);
	}
}