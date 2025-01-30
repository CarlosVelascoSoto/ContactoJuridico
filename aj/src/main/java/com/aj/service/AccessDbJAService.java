package com.aj.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aj.dao.GenericDao;


public interface AccessDbJAService<T> {
	List getNative(String query, Class obj);
	List getFromJA(String query, Class obj);
	int directJAUpdate(String query, Map<String, Object> variables);
	Long getCountJA(String query);
	List<T> getFromJAHibernate(Class<T> clazz);
	T findById(Class clazz, Integer id);
	T findById(Class clazz, Short id);
	int save(T entity);
	List sqlHQL(String strQuery, HashMap<String, Object> parameters);
	T sqlHQLEntity(String strQuery, HashMap<String, Object> parameters);
	void saveOrUpdate(T entity);
	int updateSqlNative(String query, Map variables);
}