package com.aj.dao;

import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import com.aj.model.TipoOrganos;

@Repository("tipoorganosDAO")
public class TipoOrganosDAOImpl extends AbstractDAO implements TipoOrganosDAO{
	private static final Logger logger=Logger.getLogger(TipoOrganosDAOImpl.class);

	@Override
	public List<TipoOrganos> getAll(String query){
		//Este proceso por default siempre hace un "Select", "query" debe contener el resto de la consulta o un "Select" completo  
		try{Query queryResult=getSession().createQuery(query);
			List<TipoOrganos> allRows=queryResult.list();
			return allRows;
		}catch (HibernateException e){e.printStackTrace();}
	return null;
	}

	@Override
	public Integer addNewTipoOrgano(TipoOrganos tipoorganoobj){
		long retConfirm=0;
		try{retConfirm=(long) getSession().save(tipoorganoobj);
		}catch (HibernateException e){e.printStackTrace();}
		return (int)retConfirm;
	}

	@Override
	public TipoOrganos getTipoOrganoById(Long tipoorganoid){
		TipoOrganos getTipoOrganos=null;
		try{getTipoOrganos=getSession().get(TipoOrganos.class,tipoorganoid);
		}catch (Exception e){logger.error("Exception in getTipoOrganos(): "+e.getMessage());}
		return getTipoOrganos;
	}

	@Override
	public void updateTipoOrgano(TipoOrganos tipoorganoobj){
		try{getSession().update(tipoorganoobj);
		}catch (Exception e){logger.error("Exception in updateTipoOrganos(): "+e.getMessage());}
	}

	@Override
	public void deleteTipoOrgano(Long tipoorganoid){
		try{Object o=getSession().load(TipoOrganos.class,tipoorganoid);
			getSession().delete(o);
		}catch (Exception e){logger.error("Exception in deleteTipoOrgano(): "+e.getMessage());}
	}
}