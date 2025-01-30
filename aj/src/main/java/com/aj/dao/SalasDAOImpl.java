package com.aj.dao;

import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import com.aj.model.Salas;

@Repository("salasDAO")
public class SalasDAOImpl extends AbstractDAO implements SalasDAO{
	private static final Logger logger=Logger.getLogger(SalasDAOImpl.class);

	@Override
	public List<Salas> getAll(String query){
		//Este proceso por default siempre hace un "Select", "query" debe contener el resto de la consulta o un "Select" completo  
		try{Query queryResult=getSession().createQuery(query);
			List<Salas> allRows=queryResult.list();
			return allRows;
		}catch (HibernateException e){e.printStackTrace();}
	return null;
	}

	@Override
	public Integer addNewSala(Salas salaobj){
		long retConfirm=0;
		try{retConfirm=(long) getSession().save(salaobj);
		}catch (HibernateException e){e.printStackTrace();}
		return (int)retConfirm;
	}

	@Override
	public Salas getSalaById(Long salaid){
		Salas getSalas=null;
		try{getSalas=getSession().get(Salas.class,salaid);
		}catch (Exception e){logger.error("Exception in getSalas(): "+e.getMessage());}
		return getSalas;
	}

	@Override
	public void updateSala(Salas salaobj){
		try{getSession().update(salaobj);
		}catch (Exception e){logger.error("Exception in updateSalas(): "+e.getMessage());}
	}

	@Override
	public void deleteSala(Long salaid){
		try{Object o=getSession().load(Salas.class,salaid);
			getSession().delete(o);
		}catch (Exception e){logger.error("Exception in deleteSala(): "+e.getMessage());}
	}
}