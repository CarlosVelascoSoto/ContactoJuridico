package com.aj.dao;

import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import com.aj.model.LawyerDirectory;

@Repository("lawyerdirectoryDAO")
public class LawyerDirectoryDAOImpl extends AbstractDAO implements LawyerDirectoryDAO{
	private static final Logger logger=Logger.getLogger(LawyerDirectoryDAOImpl.class);

	@Override
	public List<LawyerDirectory> getAll(String query){
		//Este proceso por default siempre hace un "Select", "query" debe contener el resto de la consulta o un "Select" completo  
		try{Query queryResult=getSession().createQuery(query);
			List<LawyerDirectory> allRows=queryResult.list();
			return allRows;
		}catch (HibernateException e){e.printStackTrace();}
	return null;
	}

	@Override
	public Integer addNewLawyerDir(LawyerDirectory lawyerdirobj){
		long retConfirm=0;
		try{retConfirm=(long) getSession().save(lawyerdirobj);
		}catch (HibernateException e){e.printStackTrace();}
		return (int)retConfirm;
	}

	@Override
	public LawyerDirectory getLawyerDirById(Long lawyerdirid){
		LawyerDirectory getLawyerDirectory=null;
		try{getLawyerDirectory=getSession().get(LawyerDirectory.class,lawyerdirid);
		}catch (Exception e){logger.error("Exception in getLawyerDirectory(): "+e.getMessage());}
		return getLawyerDirectory;
	}

	@Override
	public void updateLawyerDir(LawyerDirectory lawyerdirobj){
		try{getSession().update(lawyerdirobj);
		}catch (Exception e){logger.error("Exception in updateLawyerDirectory(): "+e.getMessage());}
	}

	@Override
	public void deleteLawyerDir(Long lawyerdirid){
		try{Object o=getSession().load(LawyerDirectory.class,lawyerdirid);
			getSession().delete(o);
		}catch (Exception e){logger.error("Exception in deleteLawyerDir(): "+e.getMessage());}
	}
}