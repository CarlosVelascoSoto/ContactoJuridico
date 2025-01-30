package com.aj.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.aj.dao.CustomColumnsDAO;
import com.aj.model.CustomColumns;

@Service("customcolumnService")
@Transactional
public class CustomColumnsServiceImpl implements CustomColumnsService{
	@Autowired
	private CustomColumnsDAO customcolumnsDAO;
	
	@Override
	public Integer addNewCustomColumn(CustomColumns customcolumnobj){
		return customcolumnsDAO.addNewCustomColumn(customcolumnobj);
	}
	
	@Override
	public List<CustomColumns> getAll(String query){
		return customcolumnsDAO.getAll(query);
	}
	
	@Override
	public CustomColumns getCustomColumnById(Long getcustomcolumnid){
		return customcolumnsDAO.getCustomColumnById(getcustomcolumnid);
	}

	@Override
	public void updateCustomColumn(CustomColumns customcolumnobj){
		customcolumnsDAO.updateCustomColumn(customcolumnobj);
	}
	
	@Override
	public void deleteCustomColumn(Long customcolumnid){
		customcolumnsDAO.deleteCustomColumn(customcolumnid);
	}
}