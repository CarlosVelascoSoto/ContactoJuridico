package com.aj.dao;

import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import com.aj.model.TipoJuicios;

@Repository("tipojuiciosDAO")
public class TipoJuiciosDAOImpl extends AbstractDAO implements TipoJuiciosDAO{
	private static final Logger logger=Logger.getLogger(TipoJuiciosDAOImpl.class);

	@Override
	public List<TipoJuicios> getAll(String query){
		//Este proceso por default siempre hace un "Select", "query" debe contener el resto de la consulta o un "Select" completo  
		try{Query queryResult=getSession().createQuery(query);
			List<TipoJuicios> allRows=queryResult.list();
			return allRows;
		}catch (HibernateException e){e.printStackTrace();}
	return null;
	}

	@Override
	public Integer addNewTipoJuicio(TipoJuicios tipojuicioobj){
		long retConfirm=0;
		try{retConfirm=(long) getSession().save(tipojuicioobj);
		}catch (HibernateException e){e.printStackTrace();}
		return (int)retConfirm;
	}

	@Override
	public TipoJuicios getTipoJuicioById(Long tipojuicioid){
		TipoJuicios getTipoJuicios=null;
		try{getTipoJuicios=getSession().get(TipoJuicios.class,tipojuicioid);
		}catch (Exception e){logger.error("Exception in getTipoJuicios(): "+e.getMessage());}
		return getTipoJuicios;
	}

	@Override
	public void updateTipoJuicio(TipoJuicios tipojuicioobj){
		try{getSession().update(tipojuicioobj);
		}catch (Exception e){logger.error("Exception in updateTipoJuicios(): "+e.getMessage());}
	}

	@Override
	public void deleteTipoJuicio(Long tipojuicioid){
		try{Object o=getSession().load(TipoJuicios.class,tipojuicioid);
			getSession().delete(o);
		}catch (Exception e){logger.error("Exception in deleteTipoJuicio(): "+e.getMessage());}
	}
}