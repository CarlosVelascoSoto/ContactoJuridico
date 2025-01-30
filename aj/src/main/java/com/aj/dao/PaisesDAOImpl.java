package com.aj.dao;

import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import com.aj.model.Paises;

@Repository("paisesDAO")
public class PaisesDAOImpl extends AbstractDAO implements PaisesDAO{
	private static final Logger logger=Logger.getLogger(PaisesDAOImpl.class);

	@Override
	public Integer addNewPais(Paises paisObj){
		long retConfirm=0;
		try{retConfirm=(long) getSession().save(paisObj);
		}catch (HibernateException e){e.printStackTrace();}
		return (int)retConfirm;
	}

	@Override
	public Paises getPaisById(Long paisId){
		Paises getPaises=null;
		try{getPaises=(Paises) getSession().get(Paises.class,paisId);
		}catch (Exception e){logger.error("Exception in getPaises(): "+e.getMessage());}
		return getPaises;
	}

	@Override
	public void updatePais(Paises paisObj){
		try{getSession().update(paisObj);
		}catch (Exception e){logger.error("Exception in updatePaises(): "+e.getMessage());}
	}

	@Override
	public void deletePais(Long paisId){
		try{Object o=getSession().load(Paises.class,paisId);
			getSession().delete(o);
		}catch (Exception e){logger.error("Exception in deletePaises(): "+e.getMessage());}
	}

	@Override
	public List<Paises> getAll(String query){
		//Este proceso por default siempre hace un "Select", "query" debe contener el resto de la consulta o un "Select" completo  
		try{Query queryResult=getSession().createQuery(query);
			List<Paises> allRows=queryResult.list();
			return allRows;
		}catch (HibernateException e){e.printStackTrace();}
		return null;
	}
}