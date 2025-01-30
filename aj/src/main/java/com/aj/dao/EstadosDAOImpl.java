package com.aj.dao;

import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import com.aj.model.Estados;

@Repository("estadosDAO")
public class EstadosDAOImpl extends AbstractDAO implements EstadosDAO{
	private static final Logger logger=Logger.getLogger(EstadosDAOImpl.class);

	@Override
	public List<Estados> getAll(String query){
		//Este proceso por default siempre hace un "Select", "query" debe contener el resto de la consulta o un "Select" completo  
		try{Query queryResult=getSession().createQuery(query);
			List<Estados> allRows=queryResult.list();
			return allRows;
		}catch (HibernateException e){e.printStackTrace();}
	return null;
	}

	@Override
	public Integer addNewEstado(Estados estadoobj){
		long retConfirm=0;
		try{retConfirm=(long) getSession().save(estadoobj);
		}catch (HibernateException e){e.printStackTrace();}
		return (int)retConfirm;
	}

	@Override
	public Estados getEstadoById(Long estadoid){
		Estados getEstados=null;
		try{getEstados=getSession().get(Estados.class,estadoid);
		}catch (Exception e){logger.error("Exception in getEstados(): "+e.getMessage());}
		return getEstados;
	}

	@Override
	public void updateEstado(Estados estadoobj){
		try{getSession().update(estadoobj);
		}catch (Exception e){logger.error("Exception in updateEstados(): "+e.getMessage());}
	}

	@Override
	public void deleteEstado(Long estadoid){
		try{Object o=getSession().load(Estados.class,estadoid);
			getSession().delete(o);
		}catch (Exception e){logger.error("Exception in deleteEstado(): "+e.getMessage());}
	}
}