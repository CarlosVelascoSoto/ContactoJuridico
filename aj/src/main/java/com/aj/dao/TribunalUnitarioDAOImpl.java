package com.aj.dao;

import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import com.aj.model.TribunalUnitario;

@Repository("tribunalunitarioDAO")
public class TribunalUnitarioDAOImpl extends AbstractDAO implements TribunalUnitarioDAO{
	private static final Logger logger=Logger.getLogger(TribunalUnitarioDAOImpl.class);

	@Override
	public List<TribunalUnitario> getAll(String query){
		//Este proceso por default siempre hace un "Select", "query" debe contener el resto de la consulta o un "Select" completo  
		try{Query queryResult=getSession().createQuery(query);
			List<TribunalUnitario> allRows=queryResult.list();
			return allRows;
		}catch (HibernateException e){e.printStackTrace();}
		return null;
	}

	@Override
	public Integer addNewTribunalUnit(TribunalUnitario tribunalid){
		long retConfirm=0;
		try{retConfirm=(long) getSession().save(tribunalid);
		}catch (HibernateException e){e.printStackTrace();}
		return (int)retConfirm;
	}

	@Override
	public TribunalUnitario getTribunalUnitById(Long tribunalid){
		TribunalUnitario getTribunalUnitario=null;
		try{getTribunalUnitario=(TribunalUnitario) getSession().get(TribunalUnitario.class,tribunalid);
		}catch (Exception e){logger.error("Exception in getTribunalUnitario(): "+e.getMessage());}
		return getTribunalUnitario;
	}

	@Override
	public void updateTribunalUnit(TribunalUnitario tribunalid){
		try{getSession().update(tribunalid);
		}catch (Exception e){logger.error("Exception in updateTribunalUnitario(): "+e.getMessage());}
	}

	@Override
	public void deleteTribunalUnit(Long tribunalid){
		try{Object o=getSession().load(TribunalUnitario.class,tribunalid);
			getSession().delete(o);
		}catch (Exception e){logger.error("Exception in deleteTribunalUnitario(): "+e.getMessage());}
	}
}