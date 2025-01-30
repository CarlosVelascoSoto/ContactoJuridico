package com.aj.dao;

import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import com.aj.model.SharedDockets;


@Repository("sharedDocketsDAO")
public class SharedDocketsDAOImpl extends AbstractDAO implements SharedDocketsDAO{
	private static final Logger logger = Logger.getLogger(UserDAOImpl.class);

	@Override
	public int addNewSharedDocket(SharedDockets shareddocketsobj){
		int retConfirm=0;
		try{retConfirm=(int) getSession().save(shareddocketsobj);
		}catch (HibernateException e){e.printStackTrace();}
		return (int) retConfirm;
	}

	@Override
	public SharedDockets getSharedDocketById(Long shareddocketsid){
		SharedDockets data=null;
		try{data=(SharedDockets) getSession().get(SharedDockets.class, shareddocketsid);
		}catch (Exception e){logger.error("Exception in getSharedDocketsDetail()::"+e.getMessage());}
		return data;
	}

	@Override
	public void updateSharedDocket(SharedDockets shareddocketsid){
		try{getSession().update(shareddocketsid);
		}catch (Exception e){logger.error("Exception in updateSharedDocketsDetails() :: "+e.getMessage());}
	}

	@Override
	public void deleteSharedDocket(Long shareddocketsid){
		try{Object o=getSession().load(SharedDockets.class, shareddocketsid);
			getSession().delete(o);
		}catch (Exception e){logger.error("Exception in deleteSharedDockets(): "+e.getMessage());}
	}

	@Override
	public List<SharedDockets> getAll(String query){
		//Este proceso por omisión tiene un "Select", "query" puede contener el resto de la consulta o un "select" completo
		try{Query queryResult=getSession().createQuery(query);
			List<SharedDockets> allRows=queryResult.list();
			return allRows;
		}catch (HibernateException e){e.printStackTrace();}
		return null;
	}

	@Override
	public int updateDeleteByQuery(String query){
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