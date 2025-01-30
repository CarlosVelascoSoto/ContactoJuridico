package com.aj.dao;

import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import com.aj.model.Companyclients;

@Repository("companyclientsDAO")
public class CompanyclientsDAOImpl extends AbstractDAO implements CompanyclientsDAO{
	private static final Logger logger=Logger.getLogger(CompanyclientsDAOImpl.class);

	@Override
	public List<Companyclients> getAll(String query){
		//Este proceso por default siempre hace un "Select", "query" debe contener el resto de la consulta o un "Select" completo  
		try{Query queryResult=getSession().createQuery(query);
			List<Companyclients> cclients=queryResult.list();
			return cclients;
		}catch (HibernateException e){e.printStackTrace();}
		return null;
	}

	@Override
	public Integer addNewCClient(Companyclients cclientobj){
		int retConfirm=0;
		try{retConfirm=(int) getSession().save(cclientobj);
		}catch (HibernateException e){e.printStackTrace();}
		return (int)retConfirm;
	}

	@Override
	public Companyclients getCClientById(int cclientid){
		Companyclients getCompanyclients=null;
		try{getCompanyclients=(Companyclients) getSession().get(Companyclients.class,cclientid);
		}catch (Exception e){logger.error("Exception in getCompanyclients(): "+e.getMessage());}
		return getCompanyclients;
	}

	@Override
	public void updateCClient(Companyclients cclientobj){
		try{getSession().update(cclientobj);
		}catch (Exception e){logger.error("Exception in updateCompanyclients(): "+e.getMessage());}
	}

	@Override
	public void deleteCClient(int cclientid){
		try{Object o=getSession().load(Companyclients.class,cclientid);
			getSession().delete(o);
		}catch (Exception e){logger.error("Exception in deleteCClient(): "+e.getMessage());}
	}
}