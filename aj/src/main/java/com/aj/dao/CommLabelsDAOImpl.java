package com.aj.dao;

import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import com.aj.model.CommunicationLabels;

@Repository("commlabelsDAO")
public class CommLabelsDAOImpl extends AbstractDAO implements CommLabelsDAO{
	private static final Logger logger=Logger.getLogger(CommLabelsDAOImpl.class);

	@Override
	public List<CommunicationLabels> getAll(String query){
		//Este proceso por default siempre hace un "Select", "query" debe contener el resto de la consulta o un "Select" completo  
		try{Query queryResult=getSession().createQuery(query);
			List<CommunicationLabels> allRows=queryResult.list();
			return allRows;
		}catch (HibernateException e){e.printStackTrace();}
	return null;
	}

	@Override
	public Integer addNewCommLabel(CommunicationLabels commlabelobj){
		long retConfirm=0;
		try{retConfirm=(long) getSession().save(commlabelobj);
		}catch (HibernateException e){e.printStackTrace();}
		return (int)retConfirm;
	}

	@Override
	public CommunicationLabels getCommLabelById(Long commlabelid){
		CommunicationLabels getCommunicationLabels=null;
		try{getCommunicationLabels=getSession().get(CommunicationLabels.class,commlabelid);
		}catch (Exception e){logger.error("Exception in getCommunicationLabels(): "+e.getMessage());}
		return getCommunicationLabels;
	}

	@Override
	public void updateCommLabel(CommunicationLabels commlabelobj){
		try{getSession().update(commlabelobj);
		}catch (Exception e){logger.error("Exception in updateCommunicationLabels(): "+e.getMessage());}
	}

	@Override
	public void deleteCommLabel(Long commlabelid){
		try{Object o=getSession().load(CommunicationLabels.class,commlabelid);
			getSession().delete(o);
		}catch (Exception e){logger.error("Exception in deleteCommLabel(): "+e.getMessage());}
	}
}