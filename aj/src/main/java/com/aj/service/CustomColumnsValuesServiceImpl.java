package com.aj.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.aj.dao.CustomColumnsValuesDAO;
import com.aj.model.CustomColumnsValues;

@Service("customcolumnsvalueService")
@Transactional
public class CustomColumnsValuesServiceImpl implements CustomColumnsValuesService{
	@Autowired
	private CustomColumnsValuesDAO customcolumnsvalueDAO;
	
	@Override
	public Integer addNewCustomColumnsValue(CustomColumnsValues customcolumnsvalueobj){
		return customcolumnsvalueDAO.addNewCustomColumnsValue(customcolumnsvalueobj);
	}
	
	@Override
	public List<CustomColumnsValues> getAll(String query){
		return customcolumnsvalueDAO.getAll(query);
	}
	
	@Override
	public CustomColumnsValues getCustomColumnsValueById(Long getcustomcolumnsvalueid){
		return customcolumnsvalueDAO.getCustomColumnsValueById(getcustomcolumnsvalueid);
	}

	@Override
	public void updateCustomColumnsValue(CustomColumnsValues customcolumnsvalueobj){
		customcolumnsvalueDAO.updateCustomColumnsValue(customcolumnsvalueobj);
	}
	
	@Override
	public void deleteCustomColumnsValue(Long customcolumnsvalueid){
		customcolumnsvalueDAO.deleteCustomColumnsValue(customcolumnsvalueid);
	}

	@Override
	public int deleteByQuery(String query){
		return customcolumnsvalueDAO.deleteByQuery(query);
	}
}