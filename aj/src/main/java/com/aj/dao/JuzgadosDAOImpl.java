package com.aj.dao;

import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import com.aj.model.Juzgados;

@Repository("juzgadosDAO")
public class JuzgadosDAOImpl extends AbstractDAO implements JuzgadosDAO{
	private static final Logger logger=Logger.getLogger(JuzgadosDAOImpl.class);

	@Override
	public List<Juzgados> getAll(String query){
		//Este proceso por default siempre hace un "Select", "query" debe contener el resto de la consulta o un "Select" completo  
		try{Query queryResult=getSession().createQuery(query);
			List<Juzgados> allRows=queryResult.list();
			return allRows;
		}catch (HibernateException e){e.printStackTrace();}
	return null;
	}

	@Override
	public Integer addNewJuzgado(Juzgados juzgadoobj){
		long retConfirm=0;
		try{retConfirm=(long) getSession().save(juzgadoobj);
		}catch (HibernateException e){e.printStackTrace();}
		return (int)retConfirm;
	}

	@Override
	public Juzgados getJuzgadoById(Long juzgadoid){
		Juzgados getJuzgados=null;
		try{getJuzgados=getSession().get(Juzgados.class,juzgadoid);
		}catch (Exception e){logger.error("Exception in getJuzgados(): "+e.getMessage());}
		return getJuzgados;
	}

	@Override
	public void updateJuzgado(Juzgados juzgadoobj){
		try{getSession().update(juzgadoobj);
		}catch (Exception e){logger.error("Exception in updateJuzgados(): "+e.getMessage());}
	}

	@Override
	public void deleteJuzgado(Long juzgadoid){
		try{Object o=getSession().load(Juzgados.class,juzgadoid);
			getSession().delete(o);
		}catch (Exception e){logger.error("Exception in deleteJuzgado(): "+e.getMessage());}
	}
}