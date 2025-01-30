package com.aj.dao;

import java.util.List;
import com.aj.model.CustomColumns;

public interface CustomColumnsDAO{
	public Integer addNewCustomColumn(CustomColumns customcolumnobj);
	public CustomColumns getCustomColumnById(Long customcolumnid);
	public void updateCustomColumn(CustomColumns customcolumnobj);
	public void deleteCustomColumn(Long customcolumnid);
	public List<CustomColumns> getAll(String query);
}