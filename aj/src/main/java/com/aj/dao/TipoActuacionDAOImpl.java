package com.aj.dao;

import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import com.aj.model.TipoActuacion;

@Repository("tipoactsDAO")
public class TipoActuacionDAOImpl extends AbstractDAO implements TipoActuacionDAO{
	private static final Logger logger=Logger.getLogger(TipoActuacionDAOImpl.class);

	@Override
	public Integer addNewTipoActuacion(TipoActuacion tipoactobj){
		int retConfirm=0;
		try{retConfirm=(int) getSession().save(tipoactobj);
		}catch (HibernateException e){e.printStackTrace();}
		return (int)retConfirm;
	}

	@Override
	public TipoActuacion getTipoActuacionById(int tipoactid){
		TipoActuacion getTipoActuacion=null;
		try{getTipoActuacion=(TipoActuacion) getSession().get(TipoActuacion.class,tipoactid);
		}catch (Exception e){logger.error("Exception in getTipoActuacion(): "+e.getMessage());}
		return getTipoActuacion;
	}

	@Override
	public void updateTipoActuacion(TipoActuacion tipoactobj){
		try{getSession().update(tipoactobj);
		}catch (Exception e){logger.error("Exception in updateTipoActuacion(): "+e.getMessage());}
	}

	@Override
	public void deleteTipoActuacion(int tipoactid){
		try{Object o=getSession().load(TipoActuacion.class,tipoactid);
			getSession().delete(o);
		}catch (Exception e){logger.error("Exception in deleteAmparo(): "+e.getMessage());}
	}

	@Override
	public List<TipoActuacion> getAll(String query){
		//Este proceso por default siempre hace un "Select", "query" debe contener el resto de la consulta o un "Select" completo  
		try{Query queryResult=getSession().createQuery(query);
			List<TipoActuacion> allRows=queryResult.list();
			return allRows;
		}catch (HibernateException e){e.printStackTrace();}
		return null;
	}
}