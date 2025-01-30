package com.aj.dao;

import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import com.aj.model.TipoJuiciosAccion;

@Repository("tipojuiciosaccionDAO")
public class TipoJuiciosAccionDAOImpl extends AbstractDAO implements TipoJuiciosAccionDAO{
	private static final Logger logger=Logger.getLogger(TipoJuiciosAccionDAOImpl.class);

	@Override
	public List<TipoJuiciosAccion> getAll(String query){
		//Este proceso por default siempre hace un "Select", "query" debe contener el resto de la consulta o un "Select" completo  
		try{Query queryResult=getSession().createQuery(query);
			List<TipoJuiciosAccion> allRows=queryResult.list();
			return allRows;
		}catch (HibernateException e){e.printStackTrace();}
	return null;
	}

	@Override
	public Integer addNewAccion(TipoJuiciosAccion accionobj){
		long retConfirm=0;
		try{retConfirm=(long) getSession().save(accionobj);
		}catch (HibernateException e){e.printStackTrace();}
		return (int)retConfirm;
	}

	@Override
	public TipoJuiciosAccion getAccionById(Long accionid){
		TipoJuiciosAccion getTipoJuiciosAccion=null;
		try{getTipoJuiciosAccion=getSession().get(TipoJuiciosAccion.class,accionid);
		}catch (Exception e){logger.error("Exception in getTipoJuiciosAccion(): "+e.getMessage());}
		return getTipoJuiciosAccion;
	}

	@Override
	public void updateAccion(TipoJuiciosAccion accionobj){
		try{getSession().update(accionobj);
		}catch (Exception e){logger.error("Exception in updateTipoJuiciosAccion(): "+e.getMessage());}
	}

	@Override
	public void deleteAccion(Long accionid){
		try{Object o=getSession().load(TipoJuiciosAccion.class,accionid);
			getSession().delete(o);
		}catch (Exception e){logger.error("Exception in deleteTipoJuiciosAccion(): "+e.getMessage());}
	}
}