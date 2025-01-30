package com.aj.dao;

import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import com.aj.model.Ciudades;

@Repository("ciudadesDAO")
public class CiudadesDAOImpl extends AbstractDAO implements CiudadesDAO{
	private static final Logger logger=Logger.getLogger(CiudadesDAOImpl.class);

	@Override
	public List<Ciudades> getAll(String query){
		//Este proceso por default siempre hace un "Select", "query" debe contener el resto de la consulta o un "Select" completo  
		try{Query queryResult=getSession().createQuery(query);
			List<Ciudades> allRows=queryResult.list();
			return allRows;
		}catch (HibernateException e){e.printStackTrace();}
	return null;
	}

	@Override
	public Integer addNewCiudad(Ciudades ciudadobj){
		long retConfirm=0;
		try{retConfirm=(long) getSession().save(ciudadobj);
		}catch (HibernateException e){e.printStackTrace();}
		return (int)retConfirm;
	}

	@Override
	public Ciudades getCiudadById(Long ciudadid){
		Ciudades getCiudades=null;
		try{getCiudades=getSession().get(Ciudades.class,ciudadid);
		}catch (Exception e){logger.error("Exception in getCiudades(): "+e.getMessage());}
		return getCiudades;
	}

	@Override
	public void updateCiudad(Ciudades ciudadobj){
		try{getSession().update(ciudadobj);
		}catch (Exception e){logger.error("Exception in updateCiudades(): "+e.getMessage());}
	}

	@Override
	public void deleteCiudad(Long ciudadid){
		try{Object o=getSession().load(Ciudades.class,ciudadid);
			getSession().delete(o);
		}catch (Exception e){logger.error("Exception in deleteCiudad(): "+e.getMessage());}
	}
}