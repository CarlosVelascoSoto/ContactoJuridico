package com.aj.dao;

import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import com.aj.model.Movimientos;

@Repository("movimientosDAO")
public class MovimientosDAOImpl extends AbstractDAO implements MovimientosDAO{
	private static final Logger logger=Logger.getLogger(MovimientosDAOImpl.class);

	@Override
	public Integer addNewMovto(Movimientos movObj){
		long retConfirm=0;
		try{retConfirm=(long) getSession().save(movObj);
		}catch (HibernateException e){e.printStackTrace();}
		return (int)retConfirm;
	}

	@Override
	public Movimientos getMovtoById(Long movId){
		Movimientos getMovimientos=null;
		try{getMovimientos=(Movimientos) getSession().get(Movimientos.class,movId);
		}catch (Exception e){logger.error("Exception in getMovimientos(): "+e.getMessage());}
		return getMovimientos;
	}

	@Override
	public void updateMovto(Movimientos movObj){
		try{getSession().update(movObj);
		}catch (Exception e){logger.error("Exception in updateMovimientos(): "+e.getMessage());}
	}

	@Override
	public void deleteMovto(Long movId){
		try{Object o=getSession().load(Movimientos.class,movId);
			getSession().delete(o);
		}catch (Exception e){logger.error("Exception in deleteMovimientos(): "+e.getMessage());}
	}

	@Override
	public List<Movimientos> getAll(String query){
		//Este proceso por default siempre hace un "Select", "query" debe contener el resto de la consulta o un "Select" completo  
		try{Query queryResult=getSession().createQuery(query);
			List<Movimientos> allMovs=queryResult.list();
			return allMovs;
		}catch (HibernateException e){e.printStackTrace();}
		return null;
	}
}