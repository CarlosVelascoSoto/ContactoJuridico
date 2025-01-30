package com.aj.dao;

import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import com.aj.model.Socialnetworkclient;

@Repository("socialnetworkclientDAO")
public class SocialnetworkclientDAOImpl extends AbstractDAO implements SocialnetworkclientDAO{
	private static final Logger logger=Logger.getLogger(SocialnetworkclientDAOImpl.class);

	@Override
	public List<Socialnetworkclient> getAll(String query){
		//Este proceso por omisión tiene un "Select", "query" puede contener el resto de la consulta o un "select" completo
		try{Query queryResult=getSession().createQuery(query);
			List<Socialnetworkclient> allRows=queryResult.list();
			return allRows;
		}catch (HibernateException e){e.printStackTrace();}
		return null;
	}

	@Override
	public Integer addNewSNCWork(Socialnetworkclient SNCobj){
		long retConfirm=0;
		try{retConfirm=(long) getSession().save(SNCobj);
		}catch (HibernateException e){e.printStackTrace();}
		return (int) retConfirm;
	}

	@Override
	public Socialnetworkclient getSNCById(int sncid){
		Socialnetworkclient getsnw=null;
		try{getsnw=(Socialnetworkclient) getSession().get(Socialnetworkclient.class, sncid);
		}catch (Exception e){logger.error("Exception in getSocialNetworkClientDetail()::"+e.getMessage());}
		return getsnw;
	}

	@Override
	public void updateSNC(Socialnetworkclient sncid){
		try{getSession().update(sncid);
		}catch (Exception e){logger.error("Exception in updateSocialNetworkClientDetails() :: "+e.getMessage());}
	}

	@Override
	public void deleteSNC(int sncid){
		try{Object o=getSession().load(Socialnetworkclient.class, sncid);
			getSession().delete(o);
		}catch (Exception e){logger.error("Exception in deleteSocialNetworkClient(): "+e.getMessage());}
	}

	@Override
	public void deleteNotIn(String ccid, String validSN){
		try{//Elimina los registros que no esten incluidos en el query, ejemplo query="10,12,14".
			Query query = getSession().createQuery("DELETE FROM Socialnetworkclient WHERE companyclientid="+ccid+" AND snid NOT IN("+validSN+")");
			int result = query.executeUpdate();
		}catch (Exception e){logger.error("Exception in deleteNotIn-SocialNetworkClient(): "+e.getMessage());}
	}
}