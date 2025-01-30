package com.aj.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
/*import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;*/
import org.springframework.stereotype.Repository;

import com.aj.model.LeadProspect;

@Repository("accessDbJADao")
public class AccessDbJADaoImpl<T> extends AbstractDAO implements AccessDbJADao {
	private static final Logger logger = Logger.getLogger(AccessDbJADaoImpl.class);
	
	@Override
	public List getNative(String query, Class obj) {
		try {
			Query queryResult = getSession().createNativeQuery(query).setResultTransformer(Transformers.aliasToBean(obj));
			return queryResult.list();
		} catch (Exception ex) {
			logger.error("Exception in getFromJA()::\n" + query + "\n" + ex.getMessage());
			ex.printStackTrace();
			return null;
		}
	}
	
	@Override
	public List getFromJA(String query, Class obj) {
		try {
			Query queryResult = getSession().createSQLQuery(query).setResultTransformer(Transformers.aliasToBean(obj));
			return queryResult.list();
		} catch (Exception ex) {
			logger.error("Exception in getFromJA()::\n" + query + "\n" + ex.getMessage());
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public int directJAUpdate(String query, Map variables) {
		try {
			Query queryResult = getSession().createSQLQuery(query);
			setValoresQueryPS(variables, queryResult);
			return queryResult.executeUpdate();
		} catch (Exception ex) {
			logger.error("Exception in directJAUpdate()::\n" + query + "\n" + ex.getMessage());
			ex.printStackTrace();
			return 0;
		}
	}

	@Override
	public Long getCountJA(String query) {
		// TODO Auto-generated method stub
		try {
			return ((Number) getSession().createSQLQuery(query).uniqueResult()).longValue();
		} catch (Exception ex) {
			logger.error("Exception in getCountJA()::\n" + query + "\n" + ex.getMessage());
			ex.printStackTrace();
			return null;
		}
	}

	private void addParametersQuery(Query query, HashMap<String, Object> parameters) {
		for (String key : parameters.keySet()) {
			query.setParameter(key, parameters.get(key));
		}
	}

	@Override
	public List getFromJAHibernate(Class clazz) {
		// TODO Auto-generated method stub
		CriteriaBuilder cb = sessionFactory.getCriteriaBuilder();
		CriteriaQuery<T> cq = cb.createQuery(clazz);
		Root<T> root = cq.from(clazz);
		// cq.select(root).orderBy(cb.asc(root.get("type")));
		return sessionFactory.getCurrentSession().createQuery(cq).getResultList();
	}

	@Override
	public T findById(Class clazz, Integer id) {
		// TODO Auto-generated method stub
		return (T) sessionFactory.getCurrentSession().get(clazz, id);
	}

	@Override
	public Object findById(Class clazz, Short id) {
		// TODO Auto-generated method stub
		return (T) sessionFactory.getCurrentSession().get(clazz, id);
	}
	
	private void addParametersNameKey(Query query, HashMap<String, Object> parameters) {
		if (parameters != null)
			for (String key : parameters.keySet()) {
				query.setParameter(key, parameters.get(key));
			}
	}
	
	@Override
	public List sqlHQL(String strQuery, HashMap parameters) {
		// TODO Auto-generated method stub
		Session s = getSession();
		Query query = s.createQuery(strQuery);
		if(parameters!=null)
			addParametersNameKey(query, parameters);
		List resultado = query.list();
		return resultado;
	}
	
	@Override
	public Object sqlHQLEntity(String strQuery, HashMap parameters) {
		// TODO Auto-generated method stub
		List result = sqlHQL(strQuery, parameters);
		if (result.size() > 0)
			return result.get(0);
		else
			return null;
	}

	@Override
	public int updateSqlNative(String query, Map variables) {
		try {
			Query queryResult = getSession().createSQLQuery(query);
			if(variables!=null)
				setValoresQueryPS(variables, queryResult);
			return queryResult.executeUpdate();
		} catch (Exception ex) {
			logger.error("Exception in updateSqlNative()::\n" + query + "\n" + ex.getMessage());
			ex.printStackTrace();
			return 0;
		}
	}
}