package com.aj.dao;

import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import com.aj.model.CustomColumnsValues;

@Repository("customcolumnsvalueDAO")
public class CustomColumnsValuesDAOImpl extends AbstractDAO implements CustomColumnsValuesDAO{
	private static final Logger logger=Logger.getLogger(CustomColumnsValuesDAOImpl.class);

	@Override
	public List<CustomColumnsValues> getAll(String query){
		//Este proceso por default siempre hace un "Select", "query" debe contener el resto de la consulta o un "Select" completo  
		try{Query queryResult=getSession().createQuery(query);
			List<CustomColumnsValues> allRows=queryResult.list();
			return allRows;
		}catch (HibernateException e){e.printStackTrace();}
		return null;
	}

	@Override
	public Integer addNewCustomColumnsValue(CustomColumnsValues customcolumnsvalueobj){
		long retConfirm=0;
		try{retConfirm=(long) getSession().save(customcolumnsvalueobj);
		}catch (HibernateException e){e.printStackTrace();}
		return (int)retConfirm;
	}

	@Override
	public CustomColumnsValues getCustomColumnsValueById(Long customcolumnsvalueid){
		CustomColumnsValues getCustomColumnsValues=null;
		try{getCustomColumnsValues=getSession().get(CustomColumnsValues.class,customcolumnsvalueid);
		}catch (Exception e){logger.error("Exception in getCustomColumnsValues(): "+e.getMessage());}
		return getCustomColumnsValues;
	}

	@Override
	public void updateCustomColumnsValue(CustomColumnsValues customcolumnsvalueobj){
		try{getSession().update(customcolumnsvalueobj);
		}catch (Exception e){logger.error("Exception in updateCustomColumnsValues(): "+e.getMessage());}
	}

	@Override
	public void deleteCustomColumnsValue(Long customcolumnsvalueid){
		try{Object o=getSession().load(CustomColumnsValues.class,customcolumnsvalueid);
			getSession().delete(o);
		}catch (Exception e){logger.error("Exception in deleteCustomColumnsValue(): "+e.getMessage());}
	}

	@Override
	public int deleteByQuery(String query){
		int recordsDeleted = 0;
        try {
        	Session session = sessionFactory.openSession();
        	Transaction tr = session.beginTransaction();
        	Query q = session.createQuery(query);
    		recordsDeleted = q.executeUpdate();
	        tr.commit();
	        session.close();
        	return recordsDeleted;
        } catch (Exception e) {logger.error("Exception in deleteByQuery(): "+e.getMessage());}
		return recordsDeleted;
	}
}