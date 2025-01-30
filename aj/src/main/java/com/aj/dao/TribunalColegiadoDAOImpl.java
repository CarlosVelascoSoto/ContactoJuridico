package com.aj.dao;

import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import com.aj.model.TribunalColegiado;

@Repository("tribunalcolegiadoDAO")
public class TribunalColegiadoDAOImpl extends AbstractDAO implements TribunalColegiadoDAO{
	private static final Logger logger=Logger.getLogger(TribunalColegiadoDAOImpl.class);

	@Override
	public List<TribunalColegiado> getAll(String query){
		//Este proceso por default siempre hace un "Select", "query" debe contener el resto de la consulta o un "Select" completo  
			try{Query queryResult=getSession().createQuery(query);
				List<TribunalColegiado> allRows=queryResult.list();
				return allRows;
			}catch (HibernateException e){e.printStackTrace();}
		return null;
	}

	@Override
	public Integer addNewTribunalC(TribunalColegiado tribunalid){
		long retConfirm=0;
		try{retConfirm=(long) getSession().save(tribunalid);
		}catch (HibernateException e){e.printStackTrace();}
		return (int)retConfirm;
	}

	@Override
	public TribunalColegiado getTribunalCById(Long tribunalid){
		TribunalColegiado getTribunalColegiado=null;
		try{getTribunalColegiado=(TribunalColegiado) getSession().get(TribunalColegiado.class,tribunalid);
		}catch (Exception e){logger.error("Exception in getTribunalColegiado(): "+e.getMessage());}
		return getTribunalColegiado;
	}

	@Override
	public void updateTribunalC(TribunalColegiado tribunalid){
		try{getSession().update(tribunalid);
		}catch (Exception e){logger.error("Exception in updateTribunalColegiado(): "+e.getMessage());}
	}

	@Override
	public void deleteTribunalC(Long tribunalid){
		try{Object o=getSession().load(TribunalColegiado.class,tribunalid);
			getSession().delete(o);
		}catch (Exception e){logger.error("Exception in deleteTribunalColegiado(): "+e.getMessage());}
	}
}