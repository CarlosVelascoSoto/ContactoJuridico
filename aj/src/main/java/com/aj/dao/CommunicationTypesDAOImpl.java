package com.aj.dao;

import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import com.aj.model.CommunicationTypes;

@Repository("commtypesDAO")
public class CommunicationTypesDAOImpl extends AbstractDAO implements CommunicationTypesDAO{
	private static final Logger logger=Logger.getLogger(CommunicationTypesDAOImpl.class);

	@Override
	public List<CommunicationTypes> getAll(String query){
		//Este proceso por default siempre hace un "Select", "query" debe contener el resto de la consulta o un "Select" completo  
		try{Query queryResult=getSession().createQuery(query);
			List<CommunicationTypes> allRows=queryResult.list();
			return allRows;
		}catch (HibernateException e){e.printStackTrace();}
	return null;
	}

	@Override
	public Integer addNewCommType(CommunicationTypes commtypeobj){
		long retConfirm=0;
		try{retConfirm=(long) getSession().save(commtypeobj);
		}catch (HibernateException e){e.printStackTrace();}
		return (int)retConfirm;
	}

	@Override
	public CommunicationTypes getCommTypeById(Long commtypeid){
		CommunicationTypes getCommTypes=null;
		try{getCommTypes=getSession().get(CommunicationTypes.class,commtypeid);
		}catch (Exception e){logger.error("Exception in getCommTypeById(): "+e.getMessage());}
		return getCommTypes;
	}

	@Override
	public void updateCommType(CommunicationTypes commtypeobj){
		try{getSession().update(commtypeobj);
		}catch (Exception e){logger.error("Exception in updateCommType(): "+e.getMessage());}
	}

	@Override
	public void deleteCommType(Long commtypeid){
		try{Object o=getSession().load(CommunicationTypes.class,commtypeid);
			getSession().delete(o);
		}catch (Exception e){logger.error("Exception in deleteCommType(): "+e.getMessage());}
	}
}