package com.aj.dao;

import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import com.aj.model.RelatedColumns;

@Repository("RelatedColumnsDAO")
public class RelatedColumnsDAOImpl extends AbstractDAO implements RelatedColumnsDAO{
	private static final Logger logger=Logger.getLogger(RelatedColumnsDAOImpl.class);

	@Override
	public List<RelatedColumns> getAll(String query){
		//Este proceso por default siempre hace un "Select", "query" debe contener el resto de la consulta o un "Select" completo  
		try{Query queryResult=getSession().createQuery(query);
			List<RelatedColumns> allRows=queryResult.list();
			return allRows;
		}catch (HibernateException e){e.printStackTrace();}
	return null;
	}

	@Override
	public Integer addNewRelatedCol(RelatedColumns relatedcolobj){
		long retConfirm=0;
		try{retConfirm=(long) getSession().save(relatedcolobj);
		}catch (HibernateException e){e.printStackTrace();}
		return (int)retConfirm;
	}

	@Override
	public RelatedColumns getRelatedColById(Long relatedcolid){
		RelatedColumns getRelatedColumns=null;
		try{getRelatedColumns=getSession().get(RelatedColumns.class,relatedcolid);
		}catch (Exception e){logger.error("Exception in getRelatedColumns(): "+e.getMessage());}
		return getRelatedColumns;
	}

	@Override
	public void updateRelatedCol(RelatedColumns relatedcolobj){
		try{getSession().update(relatedcolobj);
		}catch (Exception e){logger.error("Exception in updateRelatedColumns(): "+e.getMessage());}
	}

	@Override
	public void deleteRelatedCol(Long relatedcolid){
		try{Object o=getSession().load(RelatedColumns.class,relatedcolid);
			getSession().delete(o);
		}catch (Exception e){logger.error("Exception in deleteRelatedCol(): "+e.getMessage());}
	}
}