package com.aj.dao;

import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import com.aj.model.Socialnetworks;

@Repository("socialnetworkDAO")
public class SocialnetworkDAOImpl extends AbstractDAO implements SocialnetworkDAO{
	private static final Logger logger=Logger.getLogger(SocialnetworkDAOImpl.class);

	@Override
	public Integer addNewSocNetWork(Socialnetworks SocNetWorkobj){
		int retConfirm=0;
		try{retConfirm=(int) getSession().save(SocNetWorkobj);
		}catch (HibernateException e){e.printStackTrace();}
		return (int)retConfirm;
	}

	@Override
	public Socialnetworks getSocNetWorkById(int SocNetWorkid){
		Socialnetworks getSocialnetwork=null;
		try{getSocialnetwork=(Socialnetworks) getSession().get(Socialnetworks.class,SocNetWorkid);
		}catch (Exception e){logger.error("Exception in getSocialnetwork(): "+e.getMessage());}
		return getSocialnetwork;
	}

	@Override
	public void updateSocNetWork(Socialnetworks SocNetWorkobj){
		try{getSession().update(SocNetWorkobj);
		}catch (Exception e){logger.error("Exception in updateSocialnetwork(): "+e.getMessage());}
	}

	@Override
	public void deleteSocNetWork(int SocNetWorkid){
		try{Object o=getSession().load(Socialnetworks.class,SocNetWorkid);
			getSession().delete(o);
		}catch (Exception e){logger.error("Exception in deleteSocialnetwork(): "+e.getMessage());}
	}

	@Override
	public List<Socialnetworks> getAll(String query){
		//Este proceso por default siempre hace un "Select", "query" debe contener el resto de la consulta o un "Select" completo  
		try{Query queryResult=getSession().createQuery(query);
			List<Socialnetworks> allRows=queryResult.list();
			return allRows;
		}catch (HibernateException e){e.printStackTrace();}
		return null;
	}
}