package com.aj.dao;

import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import com.aj.model.JurisdictionalOrgans;

@Repository("jrdorganesDAO")
public class JurisdictionalOrgansDAOImpl extends AbstractDAO implements JurisdictionalOrgansDAO{
	private static final Logger logger=Logger.getLogger(JurisdictionalOrgansDAOImpl.class);

	@Override
	public List<JurisdictionalOrgans> getAll(String query){
		//Este proceso por default siempre hace un "Select", "query" debe contener el resto de la consulta o un "Select" completo  
		try{Query queryResult=getSession().createQuery(query);
			List<JurisdictionalOrgans> allRows=queryResult.list();
			return allRows;
		}catch (HibernateException e){e.printStackTrace();}
	return null;
	}

	@Override
	public Integer addNewJrdOrgan(JurisdictionalOrgans jrdorganobj){
		long retConfirm=0;
		try{retConfirm=(long) getSession().save(jrdorganobj);
		}catch (HibernateException e){e.printStackTrace();}
		return (int)retConfirm;
	}

	@Override
	public JurisdictionalOrgans getJrdOrganById(Long jrdorganid){
		JurisdictionalOrgans getJurisdictionalOrgans=null;
		try{getJurisdictionalOrgans=getSession().get(JurisdictionalOrgans.class,jrdorganid);
		}catch (Exception e){logger.error("Exception in getJurisdictionalOrgans(): "+e.getMessage());}
		return getJurisdictionalOrgans;
	}

	@Override
	public void updateJrdOrgan(JurisdictionalOrgans jrdorganobj){
		try{getSession().update(jrdorganobj);
		}catch (Exception e){logger.error("Exception in updateJurisdictionalOrgans(): "+e.getMessage());}
	}

	@Override
	public void deleteJrdOrgan(Long jrdorganid){
		try{Object o=getSession().load(JurisdictionalOrgans.class,jrdorganid);
			getSession().delete(o);
		}catch (Exception e){logger.error("Exception in deleteJrdOrgan(): "+e.getMessage());}
	}
}