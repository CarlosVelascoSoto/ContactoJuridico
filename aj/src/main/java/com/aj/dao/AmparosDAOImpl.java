package com.aj.dao;

import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import com.aj.model.Amparos;

@Repository("amparosDAO")
public class AmparosDAOImpl extends AbstractDAO implements AmparosDAO{
	private static final Logger logger=Logger.getLogger(AmparosDAOImpl.class);

	@Override
	public Integer addNewAmparo(Amparos amparoobj){
		int retConfirm=0;
		try{retConfirm=(int) getSession().save(amparoobj);
		}catch (HibernateException e){e.printStackTrace();}
		return (int)retConfirm;
	}

	@Override
	public Amparos getAmparoById(int amparoid){
		Amparos getAmparos=null;
		try{getAmparos=(Amparos) getSession().get(Amparos.class,amparoid);
		}catch (Exception e){logger.error("Exception in getAmparos(): "+e.getMessage());}
		return getAmparos;
	}

	@Override
	public void updateAmparo(Amparos amparoobj){
		try{getSession().update(amparoobj);
		}catch (Exception e){logger.error("Exception in updateAmparos(): "+e.getMessage());}
	}

	@Override
	public void deleteAmparo(int amparoid){
		try{Object o=getSession().load(Amparos.class,amparoid);
			getSession().delete(o);
		}catch (Exception e){logger.error("Exception in deleteAmparo(): "+e.getMessage());}
	}

	@Override
	public List<Amparos> getAll(String query){
		//Este proceso por default siempre hace un "Select", "query" debe contener el resto de la consulta o un "Select" completo  
		try{Query queryResult=getSession().createQuery(query);
			List<Amparos> allRows=queryResult.list();
			return allRows;
		}catch (HibernateException e){e.printStackTrace();}
		return null;
	}
}