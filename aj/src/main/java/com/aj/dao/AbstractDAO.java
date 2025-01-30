package com.aj.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public abstract class AbstractDAO<T> implements GenericDao<T> {
	private final Class<T> persistentClass;

	@SuppressWarnings("unchecked")
	public AbstractDAO() {
		//this.persistentClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
		this.persistentClass = (Class<T>) this.getClass().getGenericSuperclass();
	}

	@Autowired
	@Qualifier("sessionFactory")
	protected SessionFactory sessionFactory;

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public T findById(final Serializable id) {
		return (T) getSession().get(this.persistentClass, id);
	}

	@Override
	public Serializable save(T entity) {
		return getSession().save(entity);
	}

	@Override
	public void saveOrUpdate(T entity) {
		getSession().saveOrUpdate(entity);
	}

	@Override
	public void delete(T entity) {
		getSession().delete(entity);
	}

	@Override
	public void deleteAll() {
		List<T> entities = findAll();
		for (T entity : entities) {
			getSession().delete(entity);
		}
	}

	@Override
	public List<T> findAll() {
		return getSession().createCriteria(this.persistentClass).list();
	}

	@Override
	public List<T> findAllByExample(T entity) {
		Example example = Example.create(entity).ignoreCase().enableLike().excludeZeroes();
		return getSession().createCriteria(this.persistentClass).add(example).list();
	}

	@Override
	public void clear() {
		getSession().clear();

	}

	@Override
	public void flush() {
		getSession().flush();

	}
	protected boolean setValoresQueryPS(Map<String, Object> variables, Query query) {

		int indiceValor = 0;

		for (String key : variables.keySet()) {
			// System.out.println(key + "=" + variables.get(key).toString() +
			// "<-");
			if (variables.get(key) instanceof Long) {
				query.setLong(key, new Long(variables.get(key).toString()));
				indiceValor++;
			} else if (variables.get(key) instanceof Float) {
				query.setFloat(key, Float.valueOf(variables.get(key).toString()));
				indiceValor++;
			} else if (variables.get(key) instanceof Integer) {
				query.setInteger(key, Integer.valueOf(variables.get(key).toString()));
				indiceValor++;
			} else if (variables.get(key) instanceof Timestamp) {
				query.setTimestamp(key, (Timestamp) variables.get(key));
				indiceValor++;
			} else if (variables.get(key) != null) {
				query.setString(key, variables.get(key).toString());
				indiceValor++;
			} else if (variables.get(key) == null) {
				query.setString(key, null);
				indiceValor++;
			}
		}

		/*
		 * if (countParameterQuery() != indiceValor) { return false; }
		 */

		return true;
	}

}