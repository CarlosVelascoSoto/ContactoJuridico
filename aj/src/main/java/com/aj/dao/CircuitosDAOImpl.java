package com.aj.dao;

import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import com.aj.model.Circuitos;

@Repository("circuitosDAO")
public class CircuitosDAOImpl extends AbstractDAO implements CircuitosDAO{
	private static final Logger logger=Logger.getLogger(CircuitosDAOImpl.class);

	@Override
	public List<Circuitos> getAll(String query){
		//Este proceso por default siempre hace un "Select", "query" debe contener el resto de la consulta o un "Select" completo  
		try{Query queryResult=getSession().createQuery(query);
			List<Circuitos> allRows=queryResult.list();
			return allRows;
		}catch (HibernateException e){e.printStackTrace();}
	return null;
	}

	@Override
	public Integer addNewCircuit(Circuitos circuitoobj){
		long retConfirm=0;
		try{retConfirm=(long) getSession().save(circuitoobj);
		}catch (HibernateException e){e.printStackTrace();}
		return (int)retConfirm;
	}

	@Override
	public Circuitos getCircuitById(Long circuitoid){
		Circuitos getCircuitos=null;
		try{getCircuitos=getSession().get(Circuitos.class,circuitoid);
		}catch (Exception e){logger.error("Exception in getCircuitos(): "+e.getMessage());}
		return getCircuitos;
	}

	@Override
	public void updateCircuit(Circuitos circuitoobj){
		try{getSession().update(circuitoobj);
		}catch (Exception e){logger.error("Exception in updateCircuitos(): "+e.getMessage());}
	}

	@Override
	public void deleteCircuit(Long circuitoid){
		try{Object o=getSession().load(Circuitos.class,circuitoid);
			getSession().delete(o);
		}catch (Exception e){logger.error("Exception in deleteCircuito(): "+e.getMessage());}
	}
}