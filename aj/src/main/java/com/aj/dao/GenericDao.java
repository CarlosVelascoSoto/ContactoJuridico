package com.aj.dao;

import java.io.Serializable;
import java.util.List;

/**
 * Interface to provide common DAO methods
 * 
 */
public interface GenericDao<T>
{
	/**
	 * 
	 * @param entity:
	 *            entity to save
	 * @return Identifier of saved entity
	 */
	Serializable save(T entity);
	
	/**
	 * 
	 * @param entity:
	 *            entity to save or update
	 */
	public void saveOrUpdate(T entity);

	/**
	 * 
	 * @param entity: entity to delete
	 */
    void delete( T entity );
    
    /**
     * Delete all records
     */
    void deleteAll();
    
    /**
     * Find all records
     * @return
     */
    List<T> findAll();
    
    /**
     * Find all records matching provided entity
     * @param entity: entity object used for search
     * @return
     */
    List<T> findAllByExample( T entity );
    
    /**
     * Find by primary key
     * @param id
     * @return unique entity 
     */
    T findById( Serializable id );
    
  
	/**
	 * Clear session
	 */
    void clear();
    
    
	/**
	 * Flush session
	 */
    void flush();
}
