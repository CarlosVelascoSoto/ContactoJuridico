package com.aj.dao;

import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import com.aj.model.CustomColumns;

@Repository("customcolumnsDAO")
public class CustomColumnsDAOImpl extends AbstractDAO implements CustomColumnsDAO{
	private static final Logger logger=Logger.getLogger(CustomColumnsDAOImpl.class);

	@Override
	public List<CustomColumns> getAll(String query){
		//Este proceso por default siempre hace un "Select", "query" debe contener el resto de la consulta o un "Select" completo  
		try{Query queryResult=getSession().createQuery(query);
			List<CustomColumns> allRows=queryResult.list();
			return allRows;
		}catch (HibernateException e){e.printStackTrace();}
	return null;
	}

	@Override
	public Integer addNewCustomColumn(CustomColumns customcolumnobj){
		long retConfirm=0;
		try{retConfirm=(long) getSession().save(customcolumnobj);
		}catch (HibernateException e){e.printStackTrace();}
		return (int)retConfirm;
	}

	@Override
	public CustomColumns getCustomColumnById(Long customcolumnid){
		CustomColumns getCustomColumns=null;
		try{getCustomColumns=getSession().get(CustomColumns.class,customcolumnid);
		}catch (Exception e){logger.error("Exception in getCustomColumns(): "+e.getMessage());}
		return getCustomColumns;
	}

	@Override
	public void updateCustomColumn(CustomColumns customcolumnobj){
		try{getSession().update(customcolumnobj);
		}catch (Exception e){logger.error("Exception in updateCustomColumns(): "+e.getMessage());}
	}

	@Override
	public void deleteCustomColumn(Long customcolumnid){
		try{Object o=getSession().load(CustomColumns.class,customcolumnid);
			getSession().delete(o);
		}catch (Exception e){logger.error("Exception in deleteCustomColumn(): "+e.getMessage());}
	}
}