package com.aj.dao;

import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import com.aj.model.Jueces;

@Repository("juecesDAO")
public class JuecesDAOImpl extends AbstractDAO implements JuecesDAO{
	private static final Logger logger=Logger.getLogger(JuecesDAOImpl.class);

	@Override
	public List<Jueces> getAll(String query){
		//Este proceso por default siempre hace un "Select", "query" debe contener el resto de la consulta o un "Select" completo  
		try{Query queryResult=getSession().createQuery(query);
			List<Jueces> allRows=queryResult.list();
			return allRows;
		}catch (HibernateException e){e.printStackTrace();}
	return null;
	}

	@Override
	public Integer addNewJuez(Jueces juezobj){
		long retConfirm=0;
		try{retConfirm=(long) getSession().save(juezobj);
		}catch (HibernateException e){e.printStackTrace();}
		return (int)retConfirm;
	}

	@Override
	public Jueces getJuezById(Long juezid){
		Jueces getJueces=null;
		try{getJueces=getSession().get(Jueces.class,juezid);
		}catch (Exception e){logger.error("Exception in getJueces(): "+e.getMessage());}
		return getJueces;
	}

	@Override
	public void updateJuez(Jueces juezobj){
		try{getSession().update(juezobj);
		}catch (Exception e){logger.error("Exception in updateJueces(): "+e.getMessage());}
	}

	@Override
	public void deleteJuez(Long juezid){
		try{Object o=getSession().load(Jueces.class,juezid);
			getSession().delete(o);
		}catch (Exception e){logger.error("Exception in deleteJuez(): "+e.getMessage());}
	}
}