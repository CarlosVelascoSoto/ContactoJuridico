package com.aj.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface AccessDbJADao<T> extends GenericDao<T>{
	
	List getNative(String query, Class obj);
	List getFromJA(String query, Class obj);
	int directJAUpdate(String query, Map variables);
	Long getCountJA(String query);
	List<T> getFromJAHibernate(Class<T> clazz);
	T findById(Class clazz, Integer id);
	T findById(Class clazz, Short id);
	List sqlHQL(String strQuery, HashMap<String, Object> parameters);
	T sqlHQLEntity(String strQuery, HashMap<String, Object> parameters);
	int updateSqlNative(String query, Map variables);
}