package com.aj.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aj.dao.AccessDbJADao;

@Service("accessDbJAService")
@Transactional
public class AccessDbJAServiceImpl implements AccessDbJAService {
	private static final Logger logger=Logger.getLogger(AccessDbJAServiceImpl.class);
	
	@Autowired
	private AccessDbJADao clientDAO;

	public void setClientDAO(AccessDbJADao clientDAO) {
		this.clientDAO = clientDAO;
	}

	@Override
	public List getFromJA(String query, Class obj) {
		// TODO Auto-generated method stub
		return clientDAO.getFromJA(query, obj);
	}

	@Override
	public int directJAUpdate(String query, Map variables) {
		// TODO Auto-generated method stub
		return clientDAO.directJAUpdate(query, variables);
	}

	@Override
	public Long getCountJA(String query) {
		// TODO Auto-generated method stub
		return clientDAO.getCountJA(query);
	}

	@Override
	public List getFromJAHibernate(Class clazz) {
		// TODO Auto-generated method stub
		return clientDAO.getFromJAHibernate(clazz);
	}

	@Override
	public Object findById(Class clazz, Integer id) {
		// TODO Auto-generated method stub
		return clientDAO.findById(clazz, id);
	}

	@Override
	public Object findById(Class clazz, Short id) {
		// TODO Auto-generated method stub
		return clientDAO.findById(clazz, id);
	}

	
	@Override
	public int save(Object entity) {
		// TODO Auto-generated method stub
		Serializable result = clientDAO.save(entity);
		if (result instanceof Integer)
			return (Integer) result;
		else
			return (Short) result;
	}

	@Override
	public List sqlHQL(String strQuery, HashMap parameters) {
		// TODO Auto-generated method stub
		return clientDAO.sqlHQL(strQuery, parameters);
	}

	@Override
	public Object sqlHQLEntity(String strQuery, HashMap parameters) {
		// TODO Auto-generated method stub
		return clientDAO.sqlHQLEntity(strQuery, parameters);
	}

	@Override
	public List getNative(String query, Class obj) {
		// TODO Auto-generated method stub
		return clientDAO.getNative(query, obj);
	}

	@Override
	public void saveOrUpdate(Object entity) {
		// TODO Auto-generated method stub
		clientDAO.saveOrUpdate(entity);
	}

	@Override
	public int updateSqlNative(String query, Map variables) {
		// TODO Auto-generated method stub
		return clientDAO.updateSqlNative(query, variables);
	}

}