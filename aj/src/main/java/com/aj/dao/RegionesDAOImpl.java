package com.aj.dao;

import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import com.aj.model.Regiones;

@Repository("regionesDAO")
public class RegionesDAOImpl extends AbstractDAO implements RegionesDAO{
	private static final Logger logger=Logger.getLogger(RegionesDAOImpl.class);

	@Override
	public List<Regiones> getAll(String query){
		//Este proceso por default siempre hace un "Select", "query" debe contener el resto de la consulta o un "Select" completo  
		try{Query queryResult=getSession().createQuery(query);
			List<Regiones> allRows=queryResult.list();
			return allRows;
		}catch (HibernateException e){e.printStackTrace();}
	return null;
	}

	@Override
	public Integer addNewRegion(Regiones regionobj){
		long retConfirm=0;
		try{retConfirm=(long) getSession().save(regionobj);
		}catch (HibernateException e){e.printStackTrace();}
		return (int)retConfirm;
	}

	@Override
	public Regiones getRegionById(Long regionid){
		Regiones getRegiones=null;
		try{getRegiones=getSession().get(Regiones.class,regionid);
		}catch (Exception e){logger.error("Exception in getRegiones(): "+e.getMessage());}
		return getRegiones;
	}

	@Override
	public void updateRegion(Regiones regionobj){
		try{getSession().update(regionobj);
		}catch (Exception e){logger.error("Exception in updateRegiones(): "+e.getMessage());}
	}

	@Override
	public void deleteRegion(Long regionid){
		try{Object o=getSession().load(Regiones.class,regionid);
			getSession().delete(o);
		}catch (Exception e){logger.error("Exception in deleteRegion(): "+e.getMessage());}
	}
}