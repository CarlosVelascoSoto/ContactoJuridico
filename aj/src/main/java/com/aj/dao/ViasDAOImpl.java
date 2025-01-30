package com.aj.dao;

import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import com.aj.model.Vias;

@Repository("viasDAO")
public class ViasDAOImpl extends AbstractDAO implements ViasDAO{
	private static final Logger logger=Logger.getLogger(ViasDAOImpl.class);

	@Override
	public List<Vias> getAll(String query){
		//Este proceso por default siempre hace un "Select", "query" debe contener el resto de la consulta o un "Select" completo  
		try{Query queryResult=getSession().createQuery(query);
			List<Vias> allRows=queryResult.list();
			return allRows;
		}catch (HibernateException e){e.printStackTrace();}
	return null;
	}

	@Override
	public Integer addNewVia(Vias viaobj){
		long retConfirm=0;
		try{retConfirm=(long) getSession().save(viaobj);
		}catch (HibernateException e){e.printStackTrace();}
		return (int)retConfirm;
	}

	@Override
	public Vias getViaById(Long viaid){
		Vias getVias=null;
		try{getVias=getSession().get(Vias.class,viaid);
		}catch (Exception e){logger.error("Exception in getVias(): "+e.getMessage());}
		return getVias;
	}

	@Override
	public void updateVia(Vias viaobj){
		try{getSession().update(viaobj);
		}catch (Exception e){logger.error("Exception in updateVias(): "+e.getMessage());}
	}

	@Override
	public void deleteVia(Long viaid){
		try{Object o=getSession().load(Vias.class,viaid);
			getSession().delete(o);
		}catch (Exception e){logger.error("Exception in deleteVia(): "+e.getMessage());}
	}
}