package com.aj.dao;

import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import com.aj.model.Apelaciones;

@Repository("apelacionesDAO")
public class ApelacionesDAOImpl extends AbstractDAO implements ApelacionesDAO{
	private static final Logger logger=Logger.getLogger(ApelacionesDAOImpl.class);

	@Override
	public Integer addNewApelacion(Apelaciones apelacionobj){
		int retConfirm=0;
		try{retConfirm=(int) getSession().save(apelacionobj);
		}catch (HibernateException e){e.printStackTrace();}
		return (int)retConfirm;
	}

	@Override
	public Apelaciones getApelacionById(int apelacionid){
		Apelaciones getApelaciones=null;
		try{getApelaciones=(Apelaciones) getSession().get(Apelaciones.class,apelacionid);
		}catch (Exception e){logger.error("Exception in getApelaciones(): "+e.getMessage());}
		return getApelaciones;
	}

	@Override
	public void updateApelacion(Apelaciones apelacionobj){
		try{getSession().update(apelacionobj);
		}catch (Exception e){logger.error("Exception in updateApelaciones(): "+e.getMessage());}
	}

	@Override
	public void deleteApelacion(int apelacionid){
		try{Object o=getSession().load(Apelaciones.class,apelacionid);
			getSession().delete(o);
		}catch (Exception e){logger.error("Exception in deleteApelaciones(): "+e.getMessage());}
	}

	@Override
	public List<Apelaciones> getAll(String query){
		//Este proceso por default siempre hace un "Select", "query" debe contener el resto de la consulta o un "Select" completo  
		try{Query queryResult=getSession().createQuery(query);
			List<Apelaciones> allRows=queryResult.list();
			return allRows;
		}catch (HibernateException e){e.printStackTrace();}
		return null;
	}
}