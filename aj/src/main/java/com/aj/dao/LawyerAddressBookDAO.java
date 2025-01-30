package com.aj.dao;

import java.util.List;
import com.aj.model.LawyerAddressBook;

public interface LawyerAddressBookDAO{
	public Integer addNewLawyerAddress(LawyerAddressBook lawyeraddressobj);
	public LawyerAddressBook getLawyerAddressById(Long lawyeraddressid);
	public void updateLawyerAddress(LawyerAddressBook lawyeraddressobj);
	public void deleteLawyerAddress(Long lawyeraddressid);
	public List<LawyerAddressBook> getAll(String query);
}