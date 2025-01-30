package com.aj.dao;

import java.util.List;
import com.aj.model.CustomColumnsValues;

public interface CustomColumnsValuesDAO{
	public Integer addNewCustomColumnsValue(CustomColumnsValues customcolumnsvalueobj);
	public CustomColumnsValues getCustomColumnsValueById(Long customcolumnsvalueid);
	public void updateCustomColumnsValue(CustomColumnsValues customcolumnsvalueobj);
	public void deleteCustomColumnsValue(Long customcolumnsvalueid);
	public List<CustomColumnsValues> getAll(String query);
	public int deleteByQuery(String query);
}