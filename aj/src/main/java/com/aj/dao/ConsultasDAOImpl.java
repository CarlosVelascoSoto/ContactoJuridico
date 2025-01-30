package com.aj.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.apache.log4j.Logger;
import com.aj.model.Consultas;

@Repository("consultasDAO")
public class ConsultasDAOImpl extends AbstractDAO implements ConsultasDAO {
	private static final Logger logger=Logger.getLogger(ConsultasDAOImpl.class);
	@Override
	public Integer addNewConsulta(Consultas consultaobj) {
		int retConfirm=0;
		try{retConfirm=(int) getSession().save(consultaobj);
		}catch (HibernateException e){e.printStackTrace();}
		return (int)retConfirm;
	}

	@Override
	public Consultas getConsultaById(int consultaid) {
		Consultas getConsultas=null;
		try{getConsultas=(Consultas) getSession().get(Consultas.class,consultaid);
		}catch (Exception e){logger.error("Exception in getConsultas(): "+e.getMessage());}
		return getConsultas;
	}

	@Override
	public void updateconsulta(Consultas consultaobj) {
		try{getSession().update(consultaobj);
		}catch (Exception e){logger.error("Exception in updateConsultas(): "+e.getMessage());}
		
	}

	@Override
	public void deleteConsulta(int consultaid) {
		try{Object o=getSession().load(Consultas.class,consultaid);
		getSession().delete(o);
	}catch (Exception e){logger.error("Exception in deleteConsulta(): "+e.getMessage());}
		
	}

	@Override
	public List<Consultas> getAll(String query) {
		//Este proceso por default siempre hace un "Select", "query" debe contener el resto de la consulta o un "Select" completo  
		try{Query queryResult=getSession().createQuery(query);
			List<Consultas> allRows=queryResult.list();
			return allRows;
		}catch (HibernateException e){e.printStackTrace();}
		return null;
		
	}
	
}